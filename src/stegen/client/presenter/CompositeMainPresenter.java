package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.service.*;
import stegen.shared.*;

public class CompositeMainPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	private final InsultFactory insultFactory;
	private final DateTimeFormats dateTimeFormats;

	public interface Display {

		stegen.client.presenter.ScorePresenter.Display getScoreView();

		stegen.client.presenter.ChallengePresenter.Display getChallengeInputView();

		stegen.client.presenter.WinGameInputPresenter.Display getWinGameInputView();

		stegen.client.presenter.GameResultsPresenter.Display getGameResultsView();

		stegen.client.presenter.UndoPresenter.Display getUndoView();

		stegen.client.presenter.LoginStatusesPresenter.Display getLoginStatusesView();

		stegen.client.presenter.PlayerMiscCommandsPresenter.Display getPlayerMiscCommandView();

	}

	public CompositeMainPresenter(Display compositeMainView, LoginDataDto loginData, EventBus eventBus,
			InsultFactory insultFactory, DateTimeFormats dateTimeFormats) {
		this.view = compositeMainView;
		this.loginData = loginData;
		this.eventBus = eventBus;
		this.insultFactory = insultFactory;
		this.dateTimeFormats = dateTimeFormats;
	}

	@Override
	public void go() {
		initPresenterParts();
	}

	private void initPresenterParts() {
		new ScorePresenter(view.getScoreView(), loginData, eventBus).go();
		new ChallengePresenter(view.getChallengeInputView(), loginData, eventBus, insultFactory, dateTimeFormats).go();
		new WinGameInputPresenter(view.getWinGameInputView(), loginData, eventBus).go();
		new GameResultsPresenter(view.getGameResultsView(), eventBus).go();
		new UndoPresenter(view.getUndoView(), loginData, eventBus).go();
		new LoginStatusesPresenter(view.getLoginStatusesView(), eventBus).go();
		new PlayerMiscCommandsPresenter(view.getPlayerMiscCommandView(), eventBus).go();
	}

}
