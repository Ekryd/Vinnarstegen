package stegen.client.gui.score;

import stegen.client.gui.common.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class CleanScoresButton2 implements IsWidget {

	private final Button baseWidget = new Button("Rensa alla poäng");
	private OpenDialogHandler openDialogHandler = new OpenDialogHandler();

	public CleanScoresButton2() {
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

	private static class OpenDialogHandler implements ClickHandler {
		private final DialogBox dialogBox = new DialogBox();
		private final Button okButton = new Button("Ja, skryk är skönt!");
		private final Button cancelButton = new Button("Nej, jag bara skojade!");

		public OpenDialogHandler() {
			initLayout();
		}

		private void initLayout() {
			VerticalPanel panel = new VerticalPanel();
			dialogBox.add(panel);
			HTML dialogLabel = new HTML(
					"Är du verkligen säker på att du vill rensa poängen?<br/> Risk för gruppstryk annars!<br/>"
							+ "Skriv ned tidigare poäng i alla fall.");
			panel.add(dialogLabel);

			HideDialogHandler hideDialogHandler = new HideDialogHandler(dialogBox);
			okButton.addClickHandler(hideDialogHandler);
			okButton.setStylePrimaryName("button");
			panel.add(okButton);

			cancelButton.addClickHandler(hideDialogHandler);
			cancelButton.setStylePrimaryName("button");
			panel.add(cancelButton);
		}

		@Override
		public void onClick(ClickEvent event) {
			dialogBox.center();
		}

		public void addClickCleanScoresHandler(ClickHandler clickHandler) {
			okButton.addClickHandler(clickHandler);
		}
	}
}
