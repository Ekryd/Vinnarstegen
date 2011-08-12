package stegen.client.gui.player;

import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class ChangeNicknameButton extends Button {
	private final ChangeNicknameDialog dialog;

	public ChangeNicknameButton(MessageCentral messageCentral, final LoginDataDto loginData) {
		super("Ändra alias");
		dialog = new ChangeNicknameDialog(messageCentral, loginData);
		init();
		addHandler();
	}

	private void init() {
		setStylePrimaryName("thinButton");
	}

	private void addHandler() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int left = getAbsoluteLeft() + 10;
				int top = getAbsoluteTop() + 10;
				dialog.setPopupPosition(left, top);

				dialog.show();
			}
		});
	}

}
