package stegen.client.gui.refresh;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.RefreshPresenter.Display;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.*;

public class RefreshView implements Display {
	private static final int ONE_MINUTE = 60 * 1000;
	private final RefreshButton2 refreshButton = new RefreshButton2();
	private Timer timer;

	public RefreshView() {
		REFRESH_AREA.clearPanel();
		REFRESH_AREA.addToPanel(refreshButton);
	}

	@Override
	public void addClickRefreshHandler(ClickHandler clickHandler) {
		refreshButton.addClickHandler(clickHandler);
	}

	@Override
	public void startTimer(final Runnable commandToRun) {
		timer = new Timer() {

			@Override
			public void run() {
				commandToRun.run();
			}
		};
		timer.scheduleRepeating(ONE_MINUTE);
	}

}
