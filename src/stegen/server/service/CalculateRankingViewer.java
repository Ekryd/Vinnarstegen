package stegen.server.service;

import java.util.*;

import stegen.shared.*;

public class CalculateRankingViewer implements CalculateRankingIF {

	private final List<PlayerScoreDto> sortedPlayerScores;

	public CalculateRankingViewer(List<PlayerScoreDto> sortedPlayerScores) {
		this.sortedPlayerScores = Collections.unmodifiableList(sortedPlayerScores);
	}

	@Override
	public List<PlayerScoreDto> getList() {
		calculateRanking();
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
		}
	}
}
