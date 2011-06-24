package stegen.server;

import stegen.client.dto.*;
import stegen.server.database.*;
import stegen.shared.*;

public class ScoreRule {
	private final int oldWinnerScore;
	private final int oldLooserScore;
	private final GameResultCalculator calculator;

	public ScoreRule(int oldWinnerScore, int oldLooserScore, GameResultDto result) {
		this.oldWinnerScore = oldWinnerScore;
		this.oldLooserScore = oldLooserScore;
		this.calculator = new GameResultCalculator(result);
	}

	public int getWinningScore() {
		int nrOfSets = (calculator.getWonSets() * 2) - 1;
		return Math.max(oldWinnerScore, oldLooserScore) + (nrOfSets - calculator.getLostSets());
	}

	public int getLosingScore(int currentScore) {
		return currentScore + calculator.getLostSets();
	}

	public static ScoreRule playerWonOverPlayer(Player winner, Player loser, GameResultDto result) {
		return new ScoreRule(winner.getScore(), loser.getScore(), result);
	}

}
