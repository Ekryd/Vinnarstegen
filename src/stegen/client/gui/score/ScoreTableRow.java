package stegen.client.gui.score;

import stegen.shared.*;

// Går det att få bort PlayerDTO?
public class ScoreTableRow {
	public final PlayerDto player;
	public final String score;
	public final String ranking;
	public final String changedDateTime;
	public final String changedBy;
	public final boolean currentUser;
	public final boolean showChallengeButton;

	public ScoreTableRow(PlayerDto player, String score, String ranking, String changedDateTime, String changedBy,
			boolean currentUser, boolean showChallengeButton) {
		this.player = player;
		this.score = score;
		this.ranking = ranking;
		this.changedDateTime = changedDateTime;
		this.changedBy = changedBy;
		this.currentUser = currentUser;
		this.showChallengeButton = showChallengeButton;
	}

}
