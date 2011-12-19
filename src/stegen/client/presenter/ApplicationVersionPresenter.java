package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;

public class ApplicationVersionPresenter implements Presenter {

	private final Display view;
	private final EventBus eventBus;
	final UpdateApplicationVersionCallback eventUpdateApplicationVersion = createApplicationVersionCallback();

	public interface Display {
		void setApplicationVersion(String pplicationVersion);
	}

	public ApplicationVersionPresenter(Display view, EventBus eventBus) {
		this.view = view;
		this.eventBus = eventBus;
	}

	private UpdateApplicationVersionCallback createApplicationVersionCallback() {
		return new UpdateApplicationVersionCallback() {

			@Override
			public void onSuccessImpl(String result) {
				view.setApplicationVersion("v. " + result);
			}
		};
	}

	@Override
	public void go() {
		eventBus.addHandler(eventUpdateApplicationVersion);
		eventBus.getApplicationVersion();
	}
}
