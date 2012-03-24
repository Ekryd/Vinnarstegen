package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class UndoPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	final ClickHandler clickUndoInputHandler = createClickUndoHandler();
	final RefreshEventHandler refreshEventHandler = refreshEventHandler();
	final UndoEventHandler undoEventHandler = createUndoEventHandler();
	final GamePlayedEventHandler gamePlayedEventHandler = createGamePlayedEventHandler();
	final ClearScoresEventHandler clearScoresEventHandler = createClearScoresEventHandler();
	final AbstractAsyncCallback<PlayerCommandDto> getUndoCallback = createGetUndoCallback();
	final AbstractAsyncCallback<UndoPlayerCommandResult> undoCallback = createUndoCallback();
	private final PlayerCommandServiceAsync playerCommandService;
	private final com.google.gwt.event.shared.EventBus gwtEventBus;

	public interface Display {

		void setUndoButtonText(String buttonText);

		void addClickUndoHandler(ClickHandler clickHandler);

		void showUndoFailAlert();

		void hideUndoButton();

		void showUndoButton();

	}

	public UndoPresenter(Display messagesView, LoginDataDto loginData,PlayerCommandServiceAsync playerCommandService,com.google.gwt.event.shared.EventBus gwtEventBus) {
		this.view = messagesView;
		this.loginData = loginData;
		this.playerCommandService = playerCommandService;
		this.gwtEventBus = gwtEventBus;
	}

	@Override
	public void go() {
		initView();
		initEvents();
		loadUndoButton();
	}

	private void initView() {
		view.addClickUndoHandler(clickUndoInputHandler);
	}

	private void initEvents() {
		gwtEventBus.addHandler(GamePlayedEvent.TYPE, gamePlayedEventHandler);
		gwtEventBus.addHandler(ClearScoresEvent.TYPE,clearScoresEventHandler);
		gwtEventBus.addHandler(RefreshEvent.TYPE,refreshEventHandler);
		gwtEventBus.addHandler(UndoEvent.TYPE, undoEventHandler);
	}

	private void loadUndoButton() {
		playerCommandService.getUndoCommand(getUndoCallback);
	}
	
	private AbstractAsyncCallback<PlayerCommandDto> createGetUndoCallback(){
		return new AbstractAsyncCallback<PlayerCommandDto>() {
			@Override
			public void onSuccess(PlayerCommandDto result) {
				if (ownsLastUndoCommand(result)) {
					view.setUndoButtonText("Ångra " + result.description);
					view.showUndoButton();
				} else {
					view.hideUndoButton();
				}
			}
			private boolean ownsLastUndoCommand(PlayerCommandDto result) {
				return result != null && result.player.email.address.equals(loginData.player.email.address);
			}
		};
	}
	
	private AbstractAsyncCallback<UndoPlayerCommandResult> createUndoCallback(){
		return new AbstractAsyncCallback<UndoPlayerCommandResult>() {
			@Override
			public void onSuccess(UndoPlayerCommandResult result) {
				gwtEventBus.fireEvent(new UndoEvent(result));
			}
		};
	}
	
	private UndoEventHandler createUndoEventHandler(){
		return new UndoEventHandler() {
			@Override
			public void handleEvent(UndoEvent event) {
				if (event.getResult() == UndoPlayerCommandResult.FAILURE) {
					view.showUndoFailAlert();
				}
				loadUndoButton();
			}
		};
	}

	private ClickHandler createClickUndoHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				playerCommandService.undoPlayerCommand(loginData.player,undoCallback );
			}
		};
	}

	private ClearScoresEventHandler createClearScoresEventHandler() {
		return new ClearScoresEventHandler() {
			@Override
			public void handleEvent(ClearScoresEvent event) {
				loadUndoButton();
			}
		};
	}

	private GamePlayedEventHandler createGamePlayedEventHandler(){
		return new GamePlayedEventHandler() {
			@Override
			public void handleEvent(GamePlayedEvent event) {
				loadUndoButton();
			}
		};
	}

	private RefreshEventHandler refreshEventHandler(){
		return new RefreshEventHandler() {
			@Override
			public void handleEvent(RefreshEvent event) {
				if (event.getRefreshType() == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadUndoButton();
				}
			}
		};
	}
}
