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
		result = createLoginData();
		presenter = new LogoutPresenter(view, result);
	}

	private LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return result;
	}

	private void setupInitializationExpects() {
		view.setLogoutUrl("logoutUrl");
		EasyMock.replay(view);
	}

}
