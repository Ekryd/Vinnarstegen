package stegen.client.presenter;

import stegen.client.event.*;
import stegen.shared.*;

public class LoggedInPresenter implements Presenter {

	private final Display loggedInView;
	private final LoginDataDto result;
	private final EventBus eventBus;

	public interface Display {

	}

	public LoggedInPresenter(Display loggedInView, LoginDataDto result, EventBus eventBus) {
		this.loggedInView = loggedInView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		// TODO Auto-generated method stub

	}

}
