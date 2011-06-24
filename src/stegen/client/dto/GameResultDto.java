package stegen.client.dto;

import java.io.*;

public class GameResultDto implements Serializable {

	private static final long serialVersionUID = 3274119697058718282L;

	public SetScoreDto[] setScores;

	/** For Serialization */
	protected GameResultDto() {}

	public static GameResultDto createEmptyGameResult() {
		GameResultDto gameResult = new GameResultDto();
		gameResult.setScores = new SetScoreDto[5];
		for (int i = 0; i < gameResult.setScores.length; i++) {
			gameResult.setScores[i] = new SetScoreDto();
		}
		return gameResult;
	}

}
