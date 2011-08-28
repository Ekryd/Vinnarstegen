package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class WinGameInputPresenter implements Presenter {
	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	private ButtonType clickedWonOrLostButton;
	private PlayerDto otherPlayer;

	WinGameFieldUpdater openWinGameInputhandler = createOpenWinGameInputhandler();
	ClickHandler clickWinGameHandler = createClickWinGameHandler();

	public interface Display {
		void addClickOpenWinGameInputHandler(WinGameFieldUpdater fieldUpdater);

		void addClickWinGameHandler(ClickHandler clickHandler);

		void setupWinGameInputDialog(String winnerName, String loserName);

		void openWinGameInputDialog();

		GameResultDto getGameResult();

	}

	public WinGameInputPresenter(Display scoreView, LoginDataDto result, EventBus eventBus) {
		this.view = scoreView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
	}

	private void initView() {
		view.addClickOpenWinGameInputHandler(openWinGameInputhandler);
		view.addClickWinGameHandler(clickWinGameHandler);
	}

	private WinGameFieldUpdater createOpenWinGameInputhandler() {
		return new WinGameFieldUpdater() {

			@Override
			public void onButtonClick(ScoreTableRow row, ButtonType buttonType) {
				clickedWonOrLostButton = buttonType;
				otherPlayer = row.player;
				if (clickedWonOrLostButton == ButtonType.WIN) {
					view.setupWinGameInputDialog(result.player.nickname, otherPlayer.nickname);
				} else {
					view.setupWinGameInputDialog(otherPlayer.nickname, result.player.nickname);
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
					eventBus.playerWonOverPlayer(result.player, otherPlayer, view.getGameResult(), result.player);
				} else {
					eventBus.playerWonOverPlayer(otherPlayer, result.player, view.getGameResult(), result.player);
				}
			}
		};
	}
}
