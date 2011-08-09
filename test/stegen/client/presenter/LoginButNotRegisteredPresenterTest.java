package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.LoginButNotRegisteredPresenter.Display;
import stegen.shared.*;

public class LoginButNotRegisteredPresenterTest {

	private LoginButNotRegisteredPresenter presenter;
	private EventBus eventBus;
	private Display loginButNotRegisteredView;
	private String passcode;
	private LoginDataDto result;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testRegistrationFail() {
		setupPresenter();
		presenter.go();

		passcode = "WrongPasscode";
		setupRegistrationFailExpectations();

		simulateRegistrationClick();
	}

	@Test
	public void testRegistrationSucceed() {
		setupPresenter();
		presenter.go();

		passcode = "SuckoPust";
		setupRegistrationSucceedExpectations();

		simulateRegistrationClick();
	}

	private void setupPresenter() {
		loginButNotRegisteredView = EasyMock.createStrictMock(Display.class);
		result = createLoginData();
		eventBus = EasyMock.createStrictMock(EventBus.class);
		presenter = new LoginButNotRegisteredPresenter(loginButNotRegisteredView, result, eventBus);
	}

	private LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return result;
	}

	private void setupInitializationExpects() {
		loginButNotRegisteredView.addClickRegistrationHandler(presenter.checkRegistrationOkHandler);
		loginButNotRegisteredView.setLogoutUrl("logoutUrl");
		loginButNotRegisteredView.setUserName("nickname");
		EasyMock.replay(loginButNotRegisteredView, eventBus);
	}

	private void setupRegistrationFailExpectations() {
		EasyMock.reset(loginButNotRegisteredView, eventBus);
		EasyMock.expect(loginButNotRegisteredView.getRegistrationCode()).andReturn(passcode);
		loginButNotRegisteredView.showRegistrationFail();
		EasyMock.replay(loginButNotRegisteredView, eventBus);
	}

	private void setupRegistrationSucceedExpectations() {
		EasyMock.reset(loginButNotRegisteredView, eventBus);
		EasyMock.expect(loginButNotRegisteredView.getRegistrationCode()).andReturn(passcode);
		eventBus.registerPlayer(result.player.email);
		EasyMock.replay(loginButNotRegisteredView, eventBus);
	}

	private void simulateRegistrationClick() {
		presenter.checkRegistrationOkHandler.onClick(null);
	}

}
