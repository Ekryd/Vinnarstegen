package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class WinGameInputPresenter implements Presenter {
	private final Display view;
	private final LoginDataDto loginData;
	private ButtonType clickedWonOrLostButton;
	private PlayerDto otherPlayer;
	private String nickname;

	final WinGameFieldUpdater openWinGameInputhandler = createOpenWinGameInputhandler();
	final ClickHandler clickWinGameHandler = createClickWinGameHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler =  createChangeNicknameEventHandler();
	final AbstractAsyncCallback<Void> playerWonOverPlayerCallback = createPlayerWonOverPlayerCallback();
	private final com.google.gwt.event.shared.EventBus gwtEventBus;
	private final ScoreServiceAsync scoreService;

	public interface Display {
		void addClickOpenWinGameInputHandler(WinGameFieldUpdater fieldUpdater);

		void addClickWinGameHandler(ClickHandler clickHandler);

		void setupWinGameInputDialog(String winnerName, String loserName);

		void openWinGameInputDialog();

		SetResult getGameResult();
	}

	public WinGameInputPresenter(Display scoreView, LoginDataDto loginData, com.google.gwt.event.shared.EventBus gwtEventBus,ScoreServiceAsync scoreService) {
		this.view = scoreView;
		this.loginData = loginData;
		this.gwtEventBus = gwtEventBus;
		this.scoreService = scoreService;
		this.nickname = loginData.player.nickname;
	}

	@Override
	public void go() {
		initView();
		initEvents();
	}

	private void initView() {
		view.addClickOpenWinGameInputHandler(openWinGameInputhandler);
		view.addClickWinGameHandler(clickWinGameHandler);
	}

	private void initEvents() {
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
	}

	private WinGameFieldUpdater createOpenWinGameInputhandler() {
		return new WinGameFieldUpdater() {
			@Override
			public void onButtonClick(ScoreTableRow row, ButtonType buttonType) {
				clickedWonOrLostButton = buttonType;
				otherPlayer = row.player;
				if (clickedWonOrLostButton == ButtonType.WIN) {
					view.setupWinGameInputDialog(nickname, otherPlayer.nickname);
				} else {
					view.setupWinGameInputDialog(otherPlayer.nickname, nickname);
				}
				view.openWinGameInputDialog();
			}
		};
	}

	private ClickHandler createClickWinGameHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (clickedWonOrLostButton == ButtonType.WIN) {
					scoreService.playerWonOverPlayer(loginData.player, otherPlayer, convert(view.getGameResult()),loginData.player,playerWonOverPlayerCallback);
				} else {
					scoreService.playerWonOverPlayer(otherPlayer, loginData.player, convert(view.getGameResult()),loginData.player,playerWonOverPlayerCallback);
				}
			}
		};
	}

	static GameResultDto convert(SetResult setResult) {
		GameResultDto returnValue = GameResultDto.createEmptyGameResult();
		switch (setResult) {
		case TRE_NOLL:
			returnValue.setScores[0] = new SetScoreDto(11, 1);
			returnValue.setScores[1] = new SetScoreDto(11, 1);
			returnValue.setScores[2] = new SetScoreDto(11, 1);
			return returnValue;
		case TRE_ETT:
			returnValue.setScores[0] = new SetScoreDto(1, 11);
			returnValue.setScores[1] = new SetScoreDto(11, 1);
			returnValue.setScores[2] = new SetScoreDto(11, 1);
			returnValue.setScores[3] = new SetScoreDto(11, 1);
			return returnValue;
		case TRE_TVA:
			returnValue.setScores[0] = new SetScoreDto(1, 11);
			returnValue.setScores[1] = new SetScoreDto(1, 11);
			returnValue.setScores[2] = new SetScoreDto(11, 1);
			returnValue.setScores[3] = new SetScoreDto(11, 1);
			returnValue.setScores[4] = new SetScoreDto(11, 1);
			return returnValue;
		}
		return returnValue;
	}
	
	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				nickname = event.getNewNickname();
			}
		};
	}
	
	private AbstractAsyncCallback<Void> createPlayerWonOverPlayerCallback(){
		return new AbstractAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				gwtEventBus.fireEvent(new GamePlayedEvent());
			}
		};
	}
}