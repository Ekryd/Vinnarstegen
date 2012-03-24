package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.gui.playeraction.*;
import stegen.client.service.*;
import stegen.shared.*;

public class LoginStatusesPresenter implements Presenter {
	private final Display view;
	final AbstractAsyncCallback<List<PlayerCommandDto>> loginStatusStackCallback = createLoginStatusStackCallback();
	final RefreshEventHandler refreshEventHandler = refreshEventHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler =  createChangeNicknameEventHandler();
	private final PlayerCommandServiceAsync playerCommandService;
	private final com.google.gwt.event.shared.EventBus gwtEventBus;

	public interface Display {
		void changeLoginStatusList(List<LoginStatusRow> content);
	}

	public LoginStatusesPresenter(Display scoreView,PlayerCommandServiceAsync playerCommandService,com.google.gwt.event.shared.EventBus gwtEventBus) {
		this.view = scoreView;
		this.playerCommandService = playerCommandService;
		this.gwtEventBus = gwtEventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadLoginStatuses();
	}

	private void initEvents() {
		gwtEventBus.addHandler(RefreshEvent.TYPE,refreshEventHandler);
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
	}

	private void loadLoginStatuses() {
		playerCommandService.getLoginStatusCommandStack(10, loginStatusStackCallback);
	}
	
	private AbstractAsyncCallback<List<PlayerCommandDto>> createLoginStatusStackCallback(){
		return new AbstractAsyncCallback<List<PlayerCommandDto>>() {
			@Override
			public void onSuccess(List<PlayerCommandDto> gameResults) {
				List<LoginStatusRow> content = new ArrayList<LoginStatusRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new LoginStatusRow(playerCommandDto.player.nickname,playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeLoginStatusList(content);			
			}
		};	
	}
	
	private RefreshEventHandler refreshEventHandler(){
		return new RefreshEventHandler() {
			@Override
			public void handleEvent(RefreshEvent result) {
				if (result.getRefreshType() == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadLoginStatuses();
				}
			}
		};
	}

	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				loadLoginStatuses();
			}
		};
	}
}
