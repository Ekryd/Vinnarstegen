package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.presenter.NonregisteredUserPresenter.Display;
import stegen.shared.*;

public class NonregisteredUserPresenterTest {

	private NonregisteredUserPresenter presenter;
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
		presenter = new NonregisteredUserPresenter(view, result);
	}

	private void setupInitializationExpects() {
		view.setUserName("nickname");
		EasyMock.replay(view);
	}
}
