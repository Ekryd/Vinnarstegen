package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.RegistrationPresenter.Display;
import stegen.shared.*;

public class RegisterationPresenterTest {

	private RegistrationPresenter presenter;
	private EventBus eventBus;
	private Display view;
	private String passcode;
	private LoginDataDto result;

	@Before
	public void before() {
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
	}

	@After
	public void after() {
		verify(view, eventBus);
	}

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
		result = createLoginData();
		presenter = new RegistrationPresenter(view, result, eventBus);
	}

	private LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return result;
	}

	private void setupInitializationExpects() {
		view.addClickRegistrationHandler(presenter.checkRegistrationOkHandler);
		replay(view, eventBus);
	}

	private void setupRegistrationFailExpectations() {
		reset(view, eventBus);
		expect(view.getRegistrationCode()).andReturn(passcode);
		view.showRegistrationFail();
		replay(view, eventBus);
	}

	private void setupRegistrationSucceedExpectations() {
		reset(view, eventBus);
		expect(view.getRegistrationCode()).andReturn(passcode);
		eventBus.registerPlayer(result.player.email);
		replay(view, eventBus);
	}

	private void simulateRegistrationClick() {
		presenter.checkRegistrationOkHandler.onClick(null);
	}

}
