package stegen.client.gui.player;

import com.google.gwt.user.client.ui.*;

public class LoginPanel implements IsWidget {

	private final VerticalPanel baseWidget = new VerticalPanel();
	private Anchor signInLink = new Anchor("Sign In");

	public LoginPanel() {
		initLayout();
	}

	private void initLayout() {
		Label loginLabel = new Label("Logga in med ditt google-konto för att komma in.");
		baseWidget.add(loginLabel);
		baseWidget.add(signInLink = new Anchor("Logga in"));
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setSignInUrl(String signInUrl) {
		signInLink.setHref(signInUrl);
	}

}
