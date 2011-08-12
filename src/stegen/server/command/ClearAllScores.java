package stegen.server.command;

import java.util.*;

import stegen.server.database.*;
import stegen.server.database.PlayerRepository.Func;
import stegen.shared.*;

public class ClearAllScores implements PlayerCommand {
	private static final long serialVersionUID = 2188373900934655611L;
	private final OldScore[] oldScores;
	private final EmailAddressDto changedBy;

	/** Only for serialization */
	protected ClearAllScores() {
		this.changedBy = null;
		this.oldScores = null;
	}

	public ClearAllScores(EmailAddressDto changedBy) {
		this.changedBy = changedBy;
		this.oldScores = createOldScores();
	}

	private OldScore[] createOldScores() {
		final List<OldScore> oldScoreList = new ArrayList<ClearAllScores.OldScore>();
		PlayerRepository.get().processAndPersist(new Func() {

			@Override
			public void exec(Player player) {
				oldScoreList.add(new OldScore(player.getEmail(), player.getScore()));
			}
		});
		return oldScoreList.toArray(new OldScore[oldScoreList.size()]);
	}

	@Override
	public void execute() {
		PlayerRepository.get().processAndPersist(new Func() {

			@Override
			public void exec(Player player) {
				player.changeScore(0, changedBy);
			}
		});
	}

	@Override
	public void undo() {
		PlayerRepository playerRepository = PlayerRepository.get();
		playerRepository.processAndPersist(new Func() {

			@Override
			public void exec(Player player) {
				player.changeScore(0, changedBy);
			}
		});
		for (OldScore oldScore : oldScores) {
			playerRepository.changeScore(oldScore.playerEmail, oldScore.score, changedBy);
		}
	}

	@Override
	public String getDescription() {
		return "Rensade alla poäng";
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

	protected static class OldScore {
		public final EmailAddressDto playerEmail;
		public final int score;

		/** Only for serialization */
		protected OldScore() {
			this.playerEmail = null;
			this.score = 0;
		}

		public OldScore(EmailAddressDto playerEmail, int score) {
			this.playerEmail = playerEmail;
			this.score = score;
		}
	}

}
