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
	private LoginDataDto loginData;

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
	public void testRegisterPlayerCallback() {
		setupPresenter();
		eventBus.registerPlayer(loginData.player.email);
		eventBus.getUserLoginStatus("hostPageBaseURL");
		replay(view, eventBus);

		presenter.eventNUP.onSuccess(true);
	}
	
	@Test
	public void testNoSuccessPlayerCallback() {
		setupPresenter();
		view.showRegistrationFail();
		replay(view, eventBus);

		presenter.eventNUP.onSuccess(false);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		presenter = new RegistrationPresenter(view, loginData, eventBus, "hostPageBaseURL");
	}

	private void setupInitializationExpects() {
		view.addClickRegistrationHandler(presenter.checkNUPHandler);
		view.addKeyPressHandler(presenter.nupKeyhandler);
		
		eventBus.addHandler(presenter.eventNUP);
		replay(view, eventBus);
	}

	private void setupRegistrationFailExpectations() {
		reset(view, eventBus);
		expect(view.getRegistrationCode()).andReturn(passcode);
		eventBus.isNUP(passcode);		
		replay(view, eventBus);
	}

	

	private void simulateRegistrationClick() {
		presenter.checkNUPHandler.onClick(null);
	}

}
