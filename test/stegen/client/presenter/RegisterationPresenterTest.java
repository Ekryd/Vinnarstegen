package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.RegistrationPresenter.Display;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

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
	public void testRegistrationKeyPress() {
		setupPresenter();
		presenter.go();

		passcode = "WrongPasscode";
		setupEnterPressExpectations();

		simulateEnterPress();
	}


	@Test
	public void testRegisterPlayerCallback() {
		setupPresenter();
		eventBus.registerPlayer(loginData.player.email);
		eventBus.getUserLoginStatus("hostPageBaseURL");
		replay(view, eventBus);

		presenter.eventNewUserPassword.onSuccess(true);
	}
	
	@Test
	public void testNoSuccessPlayerCallback() {
		setupPresenter();
		view.showRegistrationFail();
		replay(view, eventBus);

		presenter.eventNewUserPassword.onSuccess(false);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		presenter = new RegistrationPresenter(view, loginData, eventBus, "hostPageBaseURL");
	}

	private void setupInitializationExpects() {
		view.addClickRegistrationHandler((ClickHandler)presenter.checkNewUserPasswordHandler);
		view.addKeyPressHandler((KeyPressHandler)presenter.checkNewUserPasswordHandler);
		
		eventBus.addHandler(presenter.eventNewUserPassword);
		replay(view, eventBus);
	}

	private void setupRegistrationFailExpectations() {
		reset(view, eventBus);
		expect(view.getRegistrationCode()).andReturn(passcode);
		eventBus.isNewUserPasswordOk(passcode);		
		replay(view, eventBus);
	}
	
	private void setupEnterPressExpectations() {
		reset(view, eventBus);		
		expect(view.getRegistrationCode()).andReturn(passcode);
		eventBus.isNewUserPasswordOk(passcode);		
		replay(view, eventBus);
	}

	

	private void simulateRegistrationClick() {
		((ClickHandler)presenter.checkNewUserPasswordHandler).onClick(null);
	}
	
	private void simulateEnterPress() {
		
		((KeyPressHandler)presenter.checkNewUserPasswordHandler).onKeyPress(new KeyPressEvent() {
			@Override
			public char getCharCode() {
			return KeyCodes.KEY_ENTER;
			}
			});
	}
	
	

	


}
