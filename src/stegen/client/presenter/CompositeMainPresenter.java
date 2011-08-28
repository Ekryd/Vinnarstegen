package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.service.*;
import stegen.shared.*;

public class CompositeMainPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	private final InsultFactory insultFactory;

	public interface Display {

		stegen.client.presenter.ScorePresenter.Display getScoreView();

		stegen.client.presenter.ChallengePresenter.Display getChallengeView();

	}

	public CompositeMainPresenter(Display compositeMainView, LoginDataDto result, EventBus eventBus,
			InsultFactory insultFactory) {
		this.view = compositeMainView;
		this.result = result;
		this.eventBus = eventBus;
		this.insultFactory = insultFactory;
	}

	@Override
	public void go() {
		initPresenterParts();
	}

	private void initPresenterParts() {
		new ScorePresenter(view.getScoreView(), result, eventBus).go();
		new ChallengePresenter(view.getChallengeView(), result, eventBus, insultFactory).go();
		// new GameResultPresenter(view.getGameResultView(), result, eventBus);
		// new LoginStatusesPresenter(view.getLoginStatusesView(), result,
		// eventBus);
		// new MiscPlayerCommandPresenter(view.getMiscPlayerCommandView(),
		// result, eventBus);
	}

}
