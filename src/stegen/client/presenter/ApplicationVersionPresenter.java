package stegen.client.presenter;

import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.client.rpc.*;

public class ApplicationVersionPresenter implements Presenter {

	private final Display view;
	private final PlayerServiceAsync playerService;
	private final Shell shell;
	final AsyncCallback<String> eventUpdateApplicationVersion = createApplicationVersionCallback();

	public interface Display {
		void setApplicationVersion(String pplicationVersion);
		void setShell(Shell shell);
	}

	public ApplicationVersionPresenter(Display view, PlayerServiceAsync playerService,Shell shell) {
		this.view = view;
		this.shell = shell;
		this.playerService = playerService;
	}

	private AsyncCallback<String> createApplicationVersionCallback() {
		return new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				view.setApplicationVersion("v. " + result);
			}
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
	}

	@Override
	public void go() {
		playerService.getApplicationVersion(eventUpdateApplicationVersion);
		view.setShell(shell);
	}
}
