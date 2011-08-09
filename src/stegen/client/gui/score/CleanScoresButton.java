package stegen.client.gui.score;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class CleanScoresButton extends Button {
	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;

	public CleanScoresButton(MessageCentral messageCentral, LoginDataDto loginData) {
		super("Rensa alla poäng");
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
		initHandler();
	}

	private void init() {
		setStylePrimaryName("button");
	}

	private void initHandler() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final DialogBox dialogBox = new DialogBox();
				VerticalPanel panel = new VerticalPanel();
				dialogBox.add(panel);
				panel.add(new HTML(
						"Är du verkligen säker på att du vill rensa poängen?<br/> Risk för gruppstryk annars!<br/>"
								+ "Skriv ned tidigare poäng i alla fall."));
				Button okButton = new Button("Ja, skryk är skönt!");
				panel.add(okButton);
				okButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						messageCentral.clearAllScores(loginData.player);
						dialogBox.hide();
					}
				});
				Button cancelButton = new Button("Nej, jag bara skojade!");
				panel.add(cancelButton);
				cancelButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						dialogBox.hide();
					}
				});
				cancelButton.setStylePrimaryName("button");
				okButton.setStylePrimaryName("button");
				dialogBox.center();
			}
		});
	}
}
