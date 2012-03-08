package stegen.client.gui.desktop.register;
import stegen.client.gui.*;
import stegen.client.presenter.NonregisteredUserPresenter.Display;

import com.google.gwt.core.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class UserView extends Composite implements Display {

	private static NonregisteredUserUiBinder uiBinder = GWT.create(NonregisteredUserUiBinder.class);

	interface NonregisteredUserUiBinder extends UiBinder<Widget, UserView> {}

	@UiField
	Label userLabel;
	@UiField
	protected HorizontalPanel panel;
	
	public UserView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setUserName(String name) {
		userLabel.setText("Välkommen " + name + " ");
	}
	
	@Override
	public void setShell(Shell shell) {
		shell.showInUserArea(this);
	}

}
