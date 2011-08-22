package stegen.client.gui.score;

public class ScoreTableRow {
	public final String name;
	public final String score;
	public final String ranking;
	public final String changedDateTime;
	public final String changedBy;
	public final boolean currentUser;

	public ScoreTableRow(String name, String score, String ranking, String changedDateTime, String changedBy,
			boolean currentUser) {
		this.name = name;
		this.score = score;
		this.ranking = ranking;
		this.changedDateTime = changedDateTime;
		this.changedBy = changedBy;
		this.currentUser = currentUser;
	}

}