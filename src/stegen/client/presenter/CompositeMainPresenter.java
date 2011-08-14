package stegen.client.presenter;

import stegen.client.event.*;
import stegen.shared.*;

public class CompositeMainPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;

	public interface Display {

		stegen.client.presenter.ScorePresenter.Display getScoreView();

	}

	public CompositeMainPresenter(Display compositeMainView, LoginDataDto result, EventBus eventBus) {
		this.view = compositeMainView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initPresenterParts();
	}

	private void initPresenterParts() {
		new ScorePresenter(view.getScoreView(), result, eventBus);
		// new GameResultPresenter(view.getGameResultView(), result, eventBus);
		// new LoginStatusesPresenter(view.getLoginStatusesView(), result,
		// eventBus);
		// new MiscPlayerCommandPresenter(view.getMiscPlayerCommandView(),
		// result, eventBus);
	}

}
