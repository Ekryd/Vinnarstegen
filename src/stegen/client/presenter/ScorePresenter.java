package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.gui.score.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class ScorePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto loginData;
	final ClearScoresEventHandler clearScoresEventHandler = createClearScoresEventHandler();
	final AbstractAsyncCallback<Void> clearScoresCallback = createClearScoresCallback(); 
	final AbstractAsyncCallback<List<PlayerScoreDto>> eventChangedScoresCallback = createUpdatePlayerScoreListCallback();
	final ClickHandler clickCleanScoresHandler = createClickCleanScoresHandler();
	final RefreshEventHandler refreshEventHandler = refreshEventHandler();
	final UndoEventHandler undoEventHandler = createUndoEventHandler();
	final GamePlayedEventHandler gamePlayedEventHandler = createGamePlayedEventHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler = createChangeNicknameEventHandler();
	private final com.google.gwt.event.shared.EventBus gwtEventBus;
	private final ScoreServiceAsync scoreService;

	public interface Display {
		void addCleanScoresHandler(ClickHandler clickHandler);

		void changeScoreList(List<ScoreTableRow> content);
	}

	public ScorePresenter(Display scoreView, LoginDataDto loginData, com.google.gwt.event.shared.EventBus gwtEventBus, ScoreServiceAsync scoreService) {
		this.view = scoreView;
		this.loginData = loginData;
		this.gwtEventBus = gwtEventBus;
		this.scoreService = scoreService;
	}

	
	@Override
	public void go() {
		initView();
		initEvents();
		loadScores();
	}

	private void initEvents() {
		gwtEventBus.addHandler(RefreshEvent.TYPE,refreshEventHandler);
		gwtEventBus.addHandler(ClearScoresEvent.TYPE, clearScoresEventHandler);
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
		gwtEventBus.addHandler(UndoEvent.TYPE, undoEventHandler);
		gwtEventBus.addHandler(GamePlayedEvent.TYPE, gamePlayedEventHandler);
	}

	private void initView() {
		view.addCleanScoresHandler(clickCleanScoresHandler);

	}

	private void loadScores() {
		scoreService.getPlayerScoreList(loginData.player.email,eventChangedScoresCallback);
	}

	private ClearScoresEventHandler createClearScoresEventHandler() {
		return new ClearScoresEventHandler() {
			
			@Override
			public void handleEvent(ClearScoresEvent event) {
				loadScores();
			}
		};
	}
	
	private ClickHandler createClickCleanScoresHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				scoreService.clearAllScores(loginData.player,clearScoresCallback);
			}
		};
	}
	
	private AbstractAsyncCallback<Void> createClearScoresCallback() {
		return new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void newNickname) {
				gwtEventBus.fireEvent(new ClearScoresEvent());
			}
		};
	}

	private AbstractAsyncCallback<List<PlayerScoreDto>> createUpdatePlayerScoreListCallback() {
		return new AbstractAsyncCallback<List<PlayerScoreDto>>() {
			@Override
			public void onSuccess(List<PlayerScoreDto> scores) {
				List<ScoreTableRow> content = new ArrayList<ScoreTableRow>();
				for (PlayerScoreDto playerScoreDto : scores) {
					boolean currentUser = playerScoreDto.player.email.address.equals(loginData.player.email.address);
					content.add(new ScoreTableRow(playerScoreDto.player, "" + playerScoreDto.score, ""
							+ playerScoreDto.ranking, playerScoreDto.changedDateTime,
							playerScoreDto.changedBy.nickname, currentUser, playerScoreDto.showChallenge));
				}
				view.changeScoreList(content);
			}
		};
	}
	
	private RefreshEventHandler refreshEventHandler(){
		return new RefreshEventHandler() {
			@Override
			public void handleEvent(RefreshEvent event) {
				if (event.getRefreshType() == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadScores();
				}
			}
		};
	}

	private UndoEventHandler createUndoEventHandler(){
		return new UndoEventHandler() {
			@Override
			public void handleEvent(UndoEvent event) {
				loadScores();
			}
		};
	}

	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				loadScores();
			}
		};
	}
	private GamePlayedEventHandler createGamePlayedEventHandler(){
		return new GamePlayedEventHandler() {
			@Override
			public void handleEvent(GamePlayedEvent event) {
				loadScores();
			}
		};
	}
}
