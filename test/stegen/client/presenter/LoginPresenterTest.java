package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.presenter.LoginPresenter.Display;
import stegen.shared.*;

public class LoginPresenterTest {

	@Test
	public void test() {
		Display loginView = EasyMock.createStrictMock(Display.class);
		LoginDataDto loginData = LoginDataDto.userIsNotLoggedIn("signInUrl");
		LoginPresenter presenter = new LoginPresenter(loginView, loginData);

		loginView.setSignInUrl("signInUrl");
		EasyMock.replay(loginView);

		presenter.go();
	}

}
