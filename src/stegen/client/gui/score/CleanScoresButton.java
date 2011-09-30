package stegen.client.gui.score;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class CleanScoresButton implements IsWidget {

	private final Button baseWidget = new Button("Rensa alla poäng");
	private CleanScoresOpenDialogHandler openDialogHandler = new CleanScoresOpenDialogHandler();

	public CleanScoresButton() {
		initLayout();
		initHandler();
	}

	private void initLayout() {
		baseWidget.setStylePrimaryName("button");
	}

	private void initHandler() {
		baseWidget.addClickHandler(openDialogHandler);
	}

	public void addClickCleanScoresHandler(ClickHandler clickHandler) {
		openDialogHandler.addClickCleanScoresHandler(clickHandler);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}
}
