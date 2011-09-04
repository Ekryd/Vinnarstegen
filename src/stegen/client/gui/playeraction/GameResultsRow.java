package stegen.client.gui.playeraction;

import java.util.*;

public class GameResultsRow {
	public final String playerName;
	public final Date gameDateTime;
	public final String result;

	public GameResultsRow(String playerName, Date gameDateTime, String result) {
		this.playerName = playerName;
		this.gameDateTime = gameDateTime;
		this.result = result;
	}
}
