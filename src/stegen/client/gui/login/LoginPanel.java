package stegen.client.gui.login;

import com.google.gwt.user.client.ui.*;

public class LoginPanel extends VerticalPanel {

	private final String signInUrl;

	public LoginPanel(String signInUrl) {
		this.signInUrl = signInUrl;
		init();
	}

	private void init() {
		Anchor signInLink = new Anchor("Sign In", signInUrl);
		Label loginLabel = new Label("Please sign in to your Google Account to access the Stegen application.");
		add(loginLabel);
		add(signInLink);
	}

}
