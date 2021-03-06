package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class WinGameInputPresenter implements Presenter {
	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	private ButtonType clickedWonOrLostButton;
	private PlayerDto otherPlayer;
	private String nickname;

	final WinGameFieldUpdater openWinGameInputhandler = createOpenWinGameInputhandler();
	final ClickHandler clickWinGameHandler = createClickWinGameHandler();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

	public interface Display {
		void addClickOpenWinGameInputHandler(WinGameFieldUpdater fieldUpdater);

		void addClickWinGameHandler(ClickHandler clickHandler);

		void setupWinGameInputDialog(String winnerName, String loserName);

		void openWinGameInputDialog();

		SetResult getGameResult();

	}

	public WinGameInputPresenter(Display scoreView, LoginDataDto loginData, EventBus eventBus) {
		this.view = scoreView;
		this.loginData = loginData;
		this.eventBus = eventBus;
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
		eventBus.addHandler(eventCommandChangeNicknameCallback);
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
					eventBus.playerWonOverPlayer(loginData.player, otherPlayer, convert(view.getGameResult()),
							loginData.player);
				} else {
					eventBus.playerWonOverPlayer(otherPlayer, loginData.player, convert(view.getGameResult()),
							loginData.player);
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

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				nickname = newNickname;
			}

		};
	}
}
