package stegen.client.gui.gameresult;

public enum SetResult {
	NOLL_NOLL(0, 0, "0 - 0"),
	TRE_NOLL(3, 0, "3 - 0"),
	TRE_ETT(3, 1, "3 - 1"),
	TRE_TVA(3, 2, "3 - 2");

	public final int winnerSets;
	public final int loserSets;
	public final String description;

	private SetResult(int winnerScore, int loserScore, String description) {
		this.winnerSets = winnerScore;
		this.loserSets = loserScore;
		this.description = description;
	}
}
