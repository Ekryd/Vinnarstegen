package stegen.shared;

import java.io.*;

public class SetScoreDto implements Serializable {
	private static final long serialVersionUID = -5741403060155392359L;
	public Integer gameWinnerScore;
	public Integer gameLoserScore;

	/** For Serialization */
	protected SetScoreDto() {}

	public SetScoreDto(Integer gameWinnerScore, Integer gameLoserScore) {
		this.gameWinnerScore = gameWinnerScore;
		this.gameLoserScore = gameLoserScore;
	}

}
