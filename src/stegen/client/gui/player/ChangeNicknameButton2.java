package stegen.client.gui.player;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class ChangeNicknameButton2 implements IsWidget {
	private final Button baseWidget = new Button("Ändra alias");
	private final ChangeNicknameDialog2 dialog = new ChangeNicknameDialog2();

	public ChangeNicknameButton2() {
		initLayout();
		addHandler();
	}

	private void initLayout() {
		baseWidget.setStylePrimaryName("thinButton");
	}

	private void addHandler() {
		baseWidget.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int left = baseWidget.getAbsoluteLeft() + 10;
				int top = baseWidget.getAbsoluteTop() + 10;
				dialog.setPopupPosition(left, top);

				dialog.showDialog();
			}
		});
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public String getNewNickname() {
		return dialog.getInputText();
	}

	public void addClickChangeUserNicknameHandler(ClickHandler clickHandler) {
		dialog.addClickOkHandler(clickHandler);
	}

	public void setPlayerName(String name) {
		dialog.setPlayerName(name);
	}

}
