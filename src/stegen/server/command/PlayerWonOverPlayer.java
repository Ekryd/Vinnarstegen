package stegen.server.command;

import stegen.server.*;
import stegen.server.database.*;
import stegen.shared.*;

public class PlayerWonOverPlayer implements PlayerCommand {

	private static final long serialVersionUID = -8029412431570876817L;
	private final Scores scores;
	private final EmailAddressDto winnerEmail;
	private final EmailAddressDto loserEmail;
	private final EmailAddressDto changedBy;

	/** Only for serialization */
	protected PlayerWonOverPlayer() {
		this.winnerEmail = null;
		this.loserEmail = null;
		this.changedBy = null;
		this.scores = null;
	}

	public PlayerWonOverPlayer(EmailAddressDto winnerEmail, EmailAddressDto loserEmail, GameResultDto result,
			EmailAddressDto changedBy) {
		this.winnerEmail = winnerEmail;
		this.loserEmail = loserEmail;
		this.changedBy = changedBy;
		this.scores = createScores(result);
	}

	protected static class Scores {
		private final int oldWinnerScore;
		private final int oldLoserScore;
		private final int newWinnerScore;
		private final int newLoserScore;

		/** Only for serialization */
		protected Scores() {
			this.oldWinnerScore = 0;
			this.oldLoserScore = 0;
			this.newWinnerScore = 0;
			this.newLoserScore = 0;
		}

		public Scores(int oldWinnerScore, int oldLoserScore, int newWinnerScore, int newLoserScore) {
			this.oldWinnerScore = oldWinnerScore;
			this.oldLoserScore = oldLoserScore;
			this.newWinnerScore = newWinnerScore;
			this.newLoserScore = newLoserScore;
		}

	}

	private Scores createScores(GameResultDto result) {
		Player winner = PlayerRepository.get().getPlayer(winnerEmail);
		Player loser = PlayerRepository.get().getPlayer(loserEmail);
		ScoreRule rule = ScoreRule.playerWonOverPlayer(winner, loser, result);
		return new Scores(winner.getScore(), loser.getScore(), rule.getWinningScore(), rule.getLosingScore(loser
				.getScore()));

	}

	@Override
	public void execute() {
		changeAndPersistScores(scores.newWinnerScore, scores.newLoserScore);
	}

	@Override
	public void undo() {
		changeAndPersistScores(scores.oldWinnerScore, scores.oldLoserScore);
	}

	private void changeAndPersistScores(int winnerScore, int loserScore) {
		PlayerRepository.get().changeScore(winnerEmail, winnerScore, changedBy);
		PlayerRepository.get().changeScore(loserEmail, loserScore, changedBy);
	}

	@Override
	public String getDescription() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		return String.format("%s vann över %s och ökade sina poäng från %s till %s. Förloraren fick %s poäng",
				stegenUserRepository.getOrCreateNickname(winnerEmail),
				stegenUserRepository.getOrCreateNickname(loserEmail), scores.oldWinnerScore, scores.newWinnerScore,
				scores.newLoserScore - scores.oldLoserScore);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

}
