package stegen.client.gui.refresh;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.RefreshPresenter.Display;

import com.google.gwt.event.dom.client.*;

public class RefreshView implements Display {
	private final RefreshButton2 refreshButton = new RefreshButton2();

	public RefreshView() {
		REFRESH_AREA.clearPanel();
		REFRESH_AREA.addToPanel(refreshButton);
	}

	@Override
	public void addClickRefreshHandler(ClickHandler clickHandler) {
		refreshButton.addClickHandler(clickHandler);
	}

}
