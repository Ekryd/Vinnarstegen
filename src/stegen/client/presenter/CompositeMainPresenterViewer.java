package stegen.client.presenter;

import stegen.client.event.*;

public class CompositeMainPresenterViewer implements Presenter {

	private final Display view;
	private final EventBus eventBus;

	public interface Display {

		stegen.client.presenter.ScorePresenterViewer.Display getScoreView();

		stegen.client.presenter.GameResultsPresenterViewer.Display getGameResultsView();

		stegen.client.presenter.MessagesPresenterViewer.Display getMessageView();

	}

	public CompositeMainPresenterViewer(Display compositeMainView, EventBus eventBus) {
		this.view = compositeMainView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initPresenterParts();
	}

	private void initPresenterParts() {
		new ScorePresenterViewer(view.getScoreView(), eventBus).go();
		new MessagesPresenterViewer(view.getMessageView(),eventBus).go();
		new GameResultsPresenterViewer(view.getGameResultsView(), eventBus).go();
	}

}
