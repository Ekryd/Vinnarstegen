package stegen.server.service;

import java.util.*;

import stegen.shared.*;

public class CalculateRanking implements CalculateRankingIF{

	private static final int CHALLENGE_DIFFERENCE_RANKING = 3;
	private final String currentPlayerAddress;
	private final List<PlayerScoreDto> sortedPlayerScores;
	private PlayerScoreDto currentPlayerScore;

	public CalculateRanking(EmailAddressDto currentPlayerEmail, List<PlayerScoreDto> sortedPlayerScores) {
		this.currentPlayerAddress = currentPlayerEmail.address;
		this.sortedPlayerScores = Collections.unmodifiableList(sortedPlayerScores);
	}

	@Override
	public List<PlayerScoreDto> getList() {
		calculateRanking();
		calculateShowChallenge();
		return new ArrayList<PlayerScoreDto>(sortedPlayerScores);
	}

	private void calculateRanking() {
		int currentScore = 0;
		int ranking = 0;
		for (PlayerScoreDto playerScore : sortedPlayerScores) {
			if (playerScore.score != currentScore) {
				currentScore = playerScore.score;
				ranking++;
			}
			playerScore.ranking = ranking;
			if (currentPlayerAddress.equals(playerScore.player.email.address)) {
				currentPlayerScore = playerScore;
			}
		}
	}

	private void calculateShowChallenge() {
		int currentPlayerRanking = currentPlayerScore.ranking;
		for (PlayerScoreDto playerScore : sortedPlayerScores) {
			boolean isCurrentPlayer = playerScore == currentPlayerScore;
			boolean isWithinChallengeRange = Math.abs(currentPlayerRanking - playerScore.ranking) <= CHALLENGE_DIFFERENCE_RANKING;
			playerScore.showChallenge = !isCurrentPlayer && isWithinChallengeRange;
		}
	}

}
