package stegen.client.gui.desktop.player;

import stegen.client.gui.desktop.register.*;
import stegen.client.gui.dialog.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class ChangeNicknameView extends UserView implements stegen.client.presenter.RegisteredUserPresenter.Display {
	
	
	Button changeNicknameButton = new Button("Ändra alias");
	
	private final InputDialogBox dialog = new InputDialogBox();

	public ChangeNicknameView() {
		initLayout();
		addHandler();
	}

	private void initLayout() {
		changeNicknameButton.setStylePrimaryName("thinButton");
		panel.add(changeNicknameButton);
	}

	private void addHandler() {
		changeNicknameButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int left = changeNicknameButton.getAbsoluteLeft() + 10;
				int top = changeNicknameButton.getAbsoluteTop() + 10;
				dialog.resetInputText();
				dialog.setPopupPosition(left, top);
				dialog.showDialog();
			}
		});
	}

	@Override
	public String getNewNickname() {
		return dialog.getInputText();
	}

	@Override
	public void addClickChangeUserNameHandler(ClickHandler clickHandler) {
		dialog.addClickOkHandler(clickHandler);
	}
	
	@Override
	public void setUserName(String name) {
		super.setUserName(name);
		dialog.setLabelText("Ändra från " + name + " till");
	}
}
