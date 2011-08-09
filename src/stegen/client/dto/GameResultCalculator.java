package stegen.client.dto;

import stegen.client.dto.*;
import stegen.shared.*;

public class GameResultCalculator {
	private final GameResultDto gameResult;
	private int wonSets;
	private int lostSets;

	public GameResultCalculator(GameResultDto gameResult) {
		this.gameResult = gameResult;
		calculateSets();
	}

	public int getWonSets() {
		return wonSets;
	}

	public int getLostSets() {
		return lostSets;
	}

	private void calculateSets() {
		wonSets = 0;
		lostSets = 0;
		for (SetScoreDto setScore : gameResult.setScores) {
			boolean notNull = setScore.gameLoserScore != null && setScore.gameWinnerScore != null;
			if (notNull && (setScore.gameLoserScore != setScore.gameWinnerScore)) {
				if (setScore.gameLoserScore < setScore.gameWinnerScore) {
					wonSets++;
				} else {
					lostSets++;
				}
			}
		}
	}

}
