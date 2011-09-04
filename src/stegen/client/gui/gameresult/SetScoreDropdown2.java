package stegen.client.gui.gameresult;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class SetScoreDropdown2 implements IsWidget {
	private final VerticalPanel baseWidget = new VerticalPanel();
	private final ListBox listBox = new ListBox(false);
	private Label playersLabel;

	public SetScoreDropdown2() {
		initLayout();
	}

	private void initLayout() {
		playersLabel = new Label();
		playersLabel.setStylePrimaryName("players_label");
		baseWidget.add(playersLabel);

		baseWidget.setWidth("100%");
		for (SetResult setResult : SetResult.values()) {
			listBox.addItem(setResult.description, setResult.name());
		}
		listBox.setStylePrimaryName("center");
		baseWidget.add(listBox);
		baseWidget.setCellHorizontalAlignment(listBox, HasHorizontalAlignment.ALIGN_CENTER);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setPlayers(String winnerName, String loserName) {
		playersLabel.setText(winnerName + " - " + loserName);
	}

	public SetResult getSetResult() {
		return SetResult.values()[listBox.getSelectedIndex()];
	}

	public void addOnScoreChangeEventHandler(ChangeHandler onScoreChangeEventHandler) {
		listBox.addChangeHandler(onScoreChangeEventHandler);
	}

	public void resetScoreToZeroZero() {
		listBox.setItemSelected(0, true);
	}

}
