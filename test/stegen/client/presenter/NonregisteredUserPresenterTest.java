package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.presenter.NonregisteredUserPresenter.Display;
import stegen.shared.*;

public class NonregisteredUserPresenterTest {

	private NonregisteredUserPresenter presenter;
	private Display view;
	private LoginDataDto loginData;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	private void setupPresenter() {
		view = EasyMock.createStrictMock(Display.class);
		loginData = LoginDataDtoFactory.createLoginData();
		presenter = new NonregisteredUserPresenter(view, loginData);
	}

	private void setupInitializationExpects() {
		view.setUserName("nickname");
		EasyMock.replay(view);
	}
}
