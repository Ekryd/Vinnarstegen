package stegen.client.gui.refresh;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class RefreshButton implements IsWidget {
	private final Button baseWidget = new Button("Refresh!");

	public RefreshButton() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setStylePrimaryName("thinButton");
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void addClickHandler(ClickHandler clickHandler) {
		baseWidget.addClickHandler(clickHandler);
	}

}
