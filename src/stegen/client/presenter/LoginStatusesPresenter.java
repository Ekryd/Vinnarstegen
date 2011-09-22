package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.playeraction.*;
import stegen.shared.*;

public class LoginStatusesPresenter implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	final UpdateLoginStatusListCallback eventUpdateLoginStatusListCallback = creatUpdateLoginStatusListCallback();
	final CommandRefreshCallback eventCommandRefreshCallback = createCommandRefreshCallback();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

	public interface Display {
		void changeLoginStatusList(List<LoginStatusRow> content);
	}

	public LoginStatusesPresenter(Display scoreView, EventBus eventBus) {
		this.view = scoreView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadLoginStatuses();
	}

	private void initEvents() {
		eventBus.addHandler(eventUpdateLoginStatusListCallback);
		eventBus.addHandler(eventCommandRefreshCallback);
		eventBus.addHandler(eventCommandChangeNicknameCallback);
	}

	private void loadLoginStatuses() {
		eventBus.updateLoginStatusList();
	}

	private UpdateLoginStatusListCallback creatUpdateLoginStatusListCallback() {
		return new UpdateLoginStatusListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerCommandDto> gameResults) {
				List<LoginStatusRow> content = new ArrayList<LoginStatusRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new LoginStatusRow(playerCommandDto.player.nickname, playerCommandDto.description));
				}
				view.changeLoginStatusList(content);
			}
		};
	}

	private CommandRefreshCallback createCommandRefreshCallback() {
		return new CommandRefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadLoginStatuses();
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				loadLoginStatuses();
			}

		};
	}

}
