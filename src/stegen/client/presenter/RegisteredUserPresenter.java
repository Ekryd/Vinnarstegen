package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class RegisteredUserPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final ClickHandler clickChangeUserNameHandler = createClickChangeUserNameHandler();
	final CommandChangeNicknameCallback eventCommandChangeNicknameHandler = createCommandChangeNicknameCallback();

	public interface Display {
		void setUserName(String name);

		String getNewNickname();

		void addClickChangeUserNameHandler(ClickHandler clickHandler);
	}

	public RegisteredUserPresenter(Display loginButNotRegisteredView, LoginDataDto loginData, EventBus eventBus) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		view.setUserName(loginData.player.nickname);
		view.addClickChangeUserNameHandler(clickChangeUserNameHandler);

		eventBus.addHandler(eventCommandChangeNicknameHandler);
	}

	private ClickHandler createClickChangeUserNameHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String newNickname = view.getNewNickname();
				boolean emptyNickname = newNickname.trim().isEmpty();
				if (!emptyNickname) {
					eventBus.changeNickname(loginData.player, newNickname);
				}
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				view.setUserName(newNickname);
			}
		};
	}

}
