package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.presenter.LogoutPresenter.Display;
import stegen.shared.*;

public class LogoutPresenterTest {

	private LogoutPresenter presenter;
	private Display view;
	private LoginDataDto result;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	private void setupPresenter() {
		view = EasyMock.createStrictMock(Display.class);
		result = LoginDataDtoFactory.createLoginData();
		presenter = new LogoutPresenter(view, result);
	}

	private void setupInitializationExpects() {
		view.setLogoutUrl("logoutUrl");
		EasyMock.replay(view);
	}

}
