package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.EventBus;

public class RegisteredUserPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final PlayerServiceAsync playerService;
	final ClickHandler changeUserNameClickHandler = createClickChangeUserNameHandler();
	final AbstractAsyncCallback<String> changeNicknameCallback = createCommandChangeNicknameCallback();
	private final ChangeNicknameEventHandler changeNicknameEventHandler = createChangeNicknameEventHandler();
	private final Shell shell;
	private final EventBus gwtEventBus;

	public interface Display {
		void setUserName(String name);
		String getNewNickname();
		void addClickChangeUserNameHandler(ClickHandler clickHandler);
		void setShell(Shell shell);
	}

	public RegisteredUserPresenter(Display loginButNotRegisteredView, LoginDataDto loginData, PlayerServiceAsync playerService, EventBus gwtEventBus, Shell shell) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
		this.playerService = playerService;
		this.gwtEventBus = gwtEventBus;
		this.shell = shell;
	}

	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				view.setUserName(event.getNewNickname());
			}
		};
	}

	@Override
	public void go() {
		view.setUserName(loginData.player.nickname);
		view.addClickChangeUserNameHandler(changeUserNameClickHandler);
		view.setShell(shell);
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
	}

	private ClickHandler createClickChangeUserNameHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String newNickname = view.getNewNickname();
				boolean emptyNickname = newNickname.trim().isEmpty();
				if (!emptyNickname) {
					playerService.changeNickname(loginData.player, newNickname,changeNicknameCallback);
				}
			}
		};
	}

	private AbstractAsyncCallback<String> createCommandChangeNicknameCallback() {
		return new AbstractAsyncCallback<String>() {
			@Override
			public void onSuccess(String newNickname) {
				gwtEventBus.fireEvent(new ChangeNicknameEvent(newNickname));
			}
		};
	}
}
