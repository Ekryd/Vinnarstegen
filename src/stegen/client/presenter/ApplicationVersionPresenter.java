package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.*;

public class ApplicationVersionPresenter implements Presenter {

	private final Display view;
	private final EventBus eventBus;
	private final Shell shell;
	final UpdateApplicationVersionCallback eventUpdateApplicationVersion = createApplicationVersionCallback();

	public interface Display {
		void setApplicationVersion(String pplicationVersion);
		void setShell(Shell shell);
	}

	public ApplicationVersionPresenter(Display view, EventBus eventBus,Shell shell) {
		this.view = view;
		this.eventBus = eventBus;
		this.shell = shell;
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
		view.setShell(shell);
	}
}
