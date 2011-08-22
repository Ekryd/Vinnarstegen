package stegen.client.gui.message;

import java.util.*;

public class MessageTableRow {
	public final String playerName;
	public final Date messageDate;
	public final String message;

	public MessageTableRow(String playerName, Date messageDate, String message) {
		this.playerName = playerName;
		this.messageDate = messageDate;
		this.message = message;
	}
}