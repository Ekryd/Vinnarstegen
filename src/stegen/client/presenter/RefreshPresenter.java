package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;

public class RefreshPresenter implements Presenter {
	private final Display view;
	RefreshService refreshService;
	private final Shell shell;
	final AsyncCallback<RefreshType> refreshCallback = new RefreshCallback();
	final com.google.gwt.event.shared.EventBus gwtEventBus;
	final ClickHandler clickRefreshHandler = createClickRefreshHandler();
	final Runnable timerCommand = createTimerCommand();

	public interface Display {
		void addClickRefreshHandler(ClickHandler clickHandler);
		void setShell(Shell shell);
		void startTimer(Runnable commandToRun);
	}

	public RefreshPresenter(Display scoreView, PlayerCommandServiceAsync playerCommandService, com.google.gwt.event.shared.EventBus gwtEventBus,Shell shell) {
		this.view = scoreView;
		this.refreshService = new RefreshService(playerCommandService);
		this.shell = shell;
		this.gwtEventBus = gwtEventBus;
	}

	@Override
	public void go() {
		initView();
		view.setShell(shell);
	}

	private void initView() {
		view.addClickRefreshHandler(clickRefreshHandler);
		view.startTimer(timerCommand);
	}

	private class RefreshCallback extends AbstractAsyncCallback<RefreshType> {
		@Override
		public void onSuccess(RefreshType result) {
			gwtEventBus.fireEvent(new RefreshEvent(result));						
		}
	}
	
	private ClickHandler createClickRefreshHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				refreshService.refresh(refreshCallback);
			}
		};
	}

	private Runnable createTimerCommand() {
		return new Runnable() {

			@Override
			public void run() {
				refreshService.refresh(refreshCallback);
			}
		};
	}

}
