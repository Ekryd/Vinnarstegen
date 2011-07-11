package stegen.client.gui.common;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public abstract class CancelOrOkButtonPanel extends HorizontalPanel {
	private final Button closeButton = new Button("Avbryt");
	private final Button okButton = new Button("Ok");

	public CancelOrOkButtonPanel() {
		init();
		setupButtonHandler();
	}

	private void init() {
		closeButton.setStylePrimaryName("button");
		okButton.setStylePrimaryName("button");

		setWidth("100%");
		add(okButton);
		add(closeButton);
		setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_LEFT);
		setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
	}

	public void setOkButtonEnabled(boolean enabled) {
		okButton.setEnabled(enabled);
	}

	private void setupButtonHandler() {
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onCloseButtonClick();
			}
		});
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onOkButtonClick();
			}

		});
	}

	protected abstract void onOkButtonClick();

	protected abstract void onCloseButtonClick();

}
