package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.*;

import com.google.gwt.event.dom.client.*;

public class RefreshPresenter implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	private final Shell shell;

	final ClickHandler clickRefreshHandler = createClickRefreshHandler();
	final Runnable timerCommand = createTimerCommand();

	public interface Display {
		void addClickRefreshHandler(ClickHandler clickHandler);
		void setShell(Shell shell);
		void startTimer(Runnable commandToRun);
	}

	public RefreshPresenter(Display scoreView, EventBus eventBus,Shell shell) {
		this.view = scoreView;
		this.eventBus = eventBus;
		this.shell = shell;
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

	private ClickHandler createClickRefreshHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.refresh();
			}
		};
	}

	private Runnable createTimerCommand() {
		return new Runnable() {

			@Override
			public void run() {
				eventBus.refresh();
			}
		};
	}

}
