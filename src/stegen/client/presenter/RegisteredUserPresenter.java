package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class RegisteredUserPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	final ClickHandler clickChangeUserNameHandler = createClickChangeUserNameHandler();
	final ChangeNicknameCallback eventChangeNicknameHandler = createEventChangeNicknameHandler();

	public interface Display {
		void setUserName(String name);

		String getNewNickname();

		void addClickChangeUserNameHandler(ClickHandler clickHandler);
	}

	public RegisteredUserPresenter(Display loginButNotRegisteredView, LoginDataDto result, EventBus eventBus) {
		this.view = loginButNotRegisteredView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		view.setUserName(result.player.nickname);
		view.addClickChangeUserNameHandler(clickChangeUserNameHandler);

		eventBus.addHandler(eventChangeNicknameHandler);
	}

	private ClickHandler createClickChangeUserNameHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String newNickname = view.getNewNickname();
				boolean emptyNickname = newNickname.trim().isEmpty();
				if (!emptyNickname) {
					eventBus.changeNickname(result.player, newNickname);
				}
			}
		};
	}

	private ChangeNicknameCallback createEventChangeNicknameHandler() {
		return new ChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(PlayerDto result) {
				view.setUserName(result.nickname);
			}
		};
	}

}
