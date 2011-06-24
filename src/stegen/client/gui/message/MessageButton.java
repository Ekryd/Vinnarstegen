package stegen.client.gui.message;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class MessageButton extends Button implements PlayerCommandListener {
	private final Random random = new Random();
	private final static ButtonText[] buttonTexts = new ButtonText[] { new ButtonText("Ropa", "ropar att"),
			new ButtonText("Viska", "viskar att"), new ButtonText("Väsa", "väser att"),
			new ButtonText("Berätta", "berättar att"), new ButtonText("Fråga", "frågar om"),
			new ButtonText("Tokvråla", "tokvrålar att"), new ButtonText("Lämna en lapp", "lämnar en lapp om"),
			new ButtonText("Gissa", "gissar att"), new ButtonText("Skriva", "skriver att"),
			new ButtonText("Meddela", "meddelar att"), new ButtonText("Skrika", "skriker att"),
			new ButtonText("Påtala", "påtalar att"), new ButtonText("Ge feedback", "ger feedback på"),
			new ButtonText("Peka", "pekar på"), new ButtonText("Gorma", "gormar att"),
			new ButtonText("Rappa", "rappar om"), new ButtonText("Skria", "skriar att"),
			new ButtonText("Skräna", "skränar att"), new ButtonText("Ryta", "ryter att"),
			new ButtonText("Hojta", "hojtar att"), new ButtonText("Säga", "säger att"),
			new ButtonText("Tala", "talar om"), new ButtonText("Yttra", "yttrar"),
			new ButtonText("Framhålla", "framhåller att"), new ButtonText("Erbjuda", "erbjuder"),
			new ButtonText("Utmana", "utmanar"), new ButtonText("Håna", "hånar") };
	private final MessageCentral messageCentral;
	private ButtonText buttonText;
	private final LoginDataDto loginData;

	public MessageButton(MessageCentral messageCentral, final LoginDataDto loginData) {
		super("");
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
		addHandler();
		messageCentral.listeners.addListener(this);
	}

	private void init() {
		setStylePrimaryName("button");
	}

	private void addHandler() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MessageDialog ropaDialog = new MessageDialog(messageCentral, loginData, buttonText);
				int left = getAbsoluteLeft() + 10;
				int top = getAbsoluteTop() + 10;
				ropaDialog.setPopupPosition(left, top);

				ropaDialog.show();
			}
		});
	}

	private void changeButtonText() {
		buttonText = buttonTexts[random.nextInt(buttonTexts.length)];
		setText(buttonText.buttonText);
	}

	@Override
	public void onPlayerCommandListUpdate(List<PlayerCommandDto> result) {
		changeButtonText();
	}

	@Override
	public void onUndoCommand(UndoPlayerCommandResult result) {}

	@Override
	public void onUndoCommandUpdate(PlayerCommandDto result) {}

}
