package stegen.client.gui.playeraction;

import java.util.*;

public class PlayerMiscCommandRow {
	public final String performedBy;
	public final Date performedDate;
	public final String description;

	public PlayerMiscCommandRow(String performedBy, Date performedDate, String description) {
		this.performedBy = performedBy;
		this.performedDate = performedDate;
		this.description = description;
	}
}
