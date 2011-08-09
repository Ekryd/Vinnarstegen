package stegen.shared;

import java.io.*;

public class PlayerScoreDto implements Serializable {
	private static final long serialVersionUID = -4143759459619965619L;

	public PlayerDto player;
	public int score;
	public int ranking;
	public PlayerDto changedBy;
	public String changedDateTime;

	/** For Serialization */
	protected PlayerScoreDto() {}

	public PlayerScoreDto(PlayerDto player, int score, PlayerDto changedBy, String changedDateTime) {
		this.player = player;
		this.score = score;
		this.changedBy = changedBy;
		this.changedDateTime = changedDateTime;
	}

}
