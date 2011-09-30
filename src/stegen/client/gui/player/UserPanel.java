package stegen.client.gui.player;

import com.google.gwt.user.client.ui.*;

public class UserPanel implements IsWidget {

	protected final HorizontalPanel baseWidget = new HorizontalPanel();
	private Label userLabel = new Label("Välkommen");

	public UserPanel() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.add(userLabel);
		baseWidget.setCellVerticalAlignment(userLabel, HasVerticalAlignment.ALIGN_MIDDLE);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setPlayerName(String name) {
		userLabel.setText("Välkommen " + name + " ");
	}
}
