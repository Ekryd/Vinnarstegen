package stegen.client.gui.playeraction;

import java.util.*;

public class LoginStatusRow {
	public final String performedBy;
	public final Date loginStatusDate;
	public final String description;

	public LoginStatusRow(String performedBy, Date loginStatusDate, String description) {
		this.performedBy = performedBy;
		this.loginStatusDate = loginStatusDate;
		this.description = description;
	}
}
