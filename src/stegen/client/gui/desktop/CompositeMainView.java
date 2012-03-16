package stegen.client.gui.desktop;
import stegen.client.gui.*;
import stegen.client.gui.challenge.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.message.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.rules.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.CompositeMainPresenter.Display;

import com.google.gwt.core.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class CompositeMainView extends Composite implements Display {
	
	private static CompositeMainUiBinder uiBinder = GWT.create(CompositeMainUiBinder.class);

	interface CompositeMainUiBinder extends UiBinder<Widget, CompositeMainView> {}

	@UiField TabLayoutPanel mainContentTable;
	@UiField ListScorePanel scorePanel;
	@UiField GameResultPanel gameResultPanel;
	@UiField LoginStatusTable loginStatusTable;
	@UiField PlayerMiscCommandTable playerMiscCommandTable;
	@UiField MessagePanel messagePanel;
	@UiField RulesPanel rulesPanel;
	
	private final ScoreView scoreView;
	private final MessagesView messagesView;
	private final ChallengeInputView challengeView;
	private final WinGameInputView gameInputView;
	private final GameResultsView gameResultsView;
	private final UndoView undoView;
	private final LoginStatusesView loginStatusesView;
	private final PlayerMiscCommandsView playerMiscCommandView;

	public CompositeMainView() {
		initWidget(uiBinder.createAndBindUi(this));
		scoreView = new ScoreView(scorePanel);
		messagesView = new MessagesView(messagePanel);
		challengeView = new ChallengeInputView(scorePanel.getChallengeButtonColumn(), scorePanel.getChallengeDialog());
		gameInputView = new WinGameInputView(scorePanel.getWinnerButtonColumn(), scorePanel.getLoserButtonColumn(),scorePanel.getWinGameDialog());
		gameResultsView = new GameResultsView(gameResultPanel);
		undoView = new UndoView(gameResultPanel.getUndoButton());
		loginStatusesView = new LoginStatusesView(loginStatusTable);
		playerMiscCommandView = new PlayerMiscCommandsView(playerMiscCommandTable);
	}

	@Override
	public stegen.client.presenter.ScorePresenter.Display getScoreView() {
		return scoreView;
	}

	@Override
	public stegen.client.presenter.ChallengePresenter.Display getChallengeInputView() {
		return challengeView;
	}

	@Override
	public stegen.client.presenter.WinGameInputPresenter.Display getWinGameInputView() {
		return gameInputView;
	}

	@Override
	public stegen.client.presenter.GameResultsPresenter.Display getGameResultsView() {
		return gameResultsView;
	}

	@Override
	public stegen.client.presenter.UndoPresenter.Display getUndoView() {
		return undoView;
	}

	@Override
	public stegen.client.presenter.LoginStatusesPresenter.Display getLoginStatusesView() {
		return loginStatusesView;
	}

	@Override
	public stegen.client.presenter.PlayerMiscCommandsPresenter.Display getPlayerMiscCommandView() {
		return playerMiscCommandView;
	}

	@Override
	public stegen.client.presenter.MessagesPresenter.Display getMessageView() {
		return messagesView;
	}

	@Override
	public void setShell(Shell shell) {
		shell.showInMainArea(this);
	}
}
