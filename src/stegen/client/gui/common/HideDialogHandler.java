package stegen.client.gui.common;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class HideDialogHandler implements ClickHandler {

	private final DialogBox dialog;

	public HideDialogHandler(DialogBox dialog) {
		this.dialog = dialog;
	}

	@Override
	public void onClick(ClickEvent event) {
		dialog.hide();
	}

}
