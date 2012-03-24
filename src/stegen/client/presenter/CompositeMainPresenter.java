package stegen.client.presenter;

import stegen.client.gui.*;
import stegen.client.service.*;
import stegen.shared.*;

public class CompositeMainPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	final com.google.gwt.event.shared.EventBus gwtEventBus;
	private final DateTimeFormats dateTimeFormats;
	private final Shell shell;
	private final PlayerCommandServiceAsync playerCommandService;
	private final ScoreServiceAsync scoreService;
	private final PlayerServiceAsync playerService;

	public interface Display {
		stegen.client.presenter.ScorePresenter.Display getScoreView();
		stegen.client.presenter.ChallengePresenter.Display getChallengeInputView();
		stegen.client.presenter.WinGameInputPresenter.Display getWinGameInputView();
		stegen.client.presenter.GameResultsPresenter.Display getGameResultsView();
		stegen.client.presenter.UndoPresenter.Display getUndoView();
		stegen.client.presenter.LoginStatusesPresenter.Display getLoginStatusesView();
		stegen.client.presenter.PlayerMiscCommandsPresenter.Display getPlayerMiscCommandView();
		stegen.client.presenter.MessagesPresenter.Display getMessageView();
		void setShell(Shell shell);
	}

	public CompositeMainPresenter(Display compositeMainView, LoginDataDto loginData,com.google.gwt.event.shared.EventBus gwtEventBus,PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,PlayerServiceAsync playerService,
		 DateTimeFormats dateTimeFormats,Shell shell) {
		this.view = compositeMainView;
		this.loginData = loginData;
		this.scoreService = scoreService;
		this.playerService = playerService;
		this.dateTimeFormats = dateTimeFormats;
		this.shell = shell;
		this.gwtEventBus = gwtEventBus;
		this.playerCommandService = playerCommandService;
		
	}

	@Override
	public void go() {
		initPresenterParts();
		view.setShell(shell);
	}

	private void initPresenterParts() {
		new ScorePresenter(view.getScoreView(), loginData, gwtEventBus,scoreService).go();
		new MessagesPresenter(view.getMessageView(), loginData,  playerCommandService,playerService,gwtEventBus).go();
		new ChallengePresenter(view.getChallengeInputView(), loginData, dateTimeFormats,scoreService,gwtEventBus).go();
		new WinGameInputPresenter(view.getWinGameInputView(), loginData, gwtEventBus,scoreService).go();
		new GameResultsPresenter(view.getGameResultsView(),gwtEventBus, playerCommandService).go();
		new UndoPresenter(view.getUndoView(), loginData, playerCommandService,gwtEventBus).go();
		new LoginStatusesPresenter(view.getLoginStatusesView(),playerCommandService,gwtEventBus).go();
		new PlayerMiscCommandsPresenter(view.getPlayerMiscCommandView(),gwtEventBus,playerCommandService).go();
	}

}
