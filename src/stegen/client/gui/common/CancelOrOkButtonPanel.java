package stegen.client.gui.common;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class CancelOrOkButtonPanel implements IsWidget {
	private final HorizontalPanel baseWidget = new HorizontalPanel();
	private final Button closeButton = new Button("Avbryt");
	private final Button okButton = new Button("Ok");

	public CancelOrOkButtonPanel() {
		initLayout();
	}

	private void initLayout() {
		closeButton.setStylePrimaryName("button");
		okButton.setStylePrimaryName("button");

		baseWidget.setWidth("100%");
		baseWidget.add(okButton);
		baseWidget.add(closeButton);
		baseWidget.setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_LEFT);
		baseWidget.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setOkButtonEnabled(boolean enabled) {
		okButton.setEnabled(enabled);
	}

	public void addClickCloseHandler(ClickHandler clickHandler) {
		closeButton.addClickHandler(clickHandler);
	}

	public void addClickOkHandler(ClickHandler clickHandler) {
		okButton.addClickHandler(clickHandler);
	}

}
