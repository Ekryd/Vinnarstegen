package stegen.client.gui.mobile.login;

import stegen.client.gui.*;
import stegen.client.presenter.LoginPresenter.Display;

import com.google.gwt.core.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class LoginView extends Composite implements Display {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, LoginView> {}

	@UiField
	Anchor signInLink = new Anchor();
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setSignInUrl(String signInUrl) {
		signInLink.setHref(signInUrl);
	}
	@Override
	public void setShell(Shell shell) {
		shell.showInMainArea(this);		
	}
}
