package stegen.client.gui.player;

import com.google.gwt.user.client.ui.*;

public class UserPanel2 implements IsWidget {

	protected final HorizontalPanel baseWidget = new HorizontalPanel();
	private Label userLabel = new Label("Välkommen");

	public UserPanel2() {
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
