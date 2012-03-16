package stegen.client.gui.desktop.refresh;

import stegen.client.gui.*;
import stegen.client.presenter.RefreshPresenter.Display;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

public class RefreshView extends Composite implements Display{
	
	private static RefreshUiBinder uiBinder = GWT.create(RefreshUiBinder.class);
	interface RefreshUiBinder extends UiBinder<Widget, RefreshView> {}
	
	private static final int ONE_MINUTE = 60 * 1000;
	
	@UiField
	Button refreshButton = new Button("Refresh!");
	
	private Timer timer;

	public RefreshView() {
		initWidget(uiBinder.createAndBindUi(this));
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
	
	@Override
	public void setShell(Shell shell) {
		shell.showInRefreshArea(this);
	}
}
