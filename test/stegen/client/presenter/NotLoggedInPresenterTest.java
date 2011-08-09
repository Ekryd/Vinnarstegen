package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.presenter.NotLoggedInPresenter.Display;
import stegen.shared.*;

public class NotLoggedInPresenterTest {

	@Test
	public void test() {
		Display loginView = EasyMock.createStrictMock(Display.class);
		LoginDataDto result = LoginDataDto.userIsNotLoggedIn("signInUrl");
		NotLoggedInPresenter presenter = new NotLoggedInPresenter(loginView, result);

		loginView.setSignInUrl("signInUrl");
		EasyMock.replay(loginView);

		presenter.go();
	}

}
