package stegen.client.presenter;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.RegistrationPresenter.Display;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
@RunWith(MockitoJUnitRunner.class)
public class RegisterationPresenterTest {

	private RegistrationPresenter presenter;
	@Mock
	private EventBus eventBus;
	@Mock
	private Display view;
	@Mock
	private Shell shell;
	private String passcode;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}
	
	@Test
	public void shouldRegisterSuccessfull() {
		setupPresenter();
		presenter.go();

		passcode = "Waldner";
		
		when(view.getRegistrationCode()).thenReturn(passcode);
		simulateRegistrationClick();
		setupRegistrationExpectations();
	}

	@Test
	public void testRegistrationFail() {
		setupPresenter();
		
		presenter.go();

		passcode = "WrongPasscode";
		
		when(view.getRegistrationCode()).thenReturn(passcode);
		simulateRegistrationClick();
		setupRegistrationExpectations();
	}

	@Test
	public void shouldShowWrongPasswordAfterEnterPress() {
		setupPresenter();
		presenter.go();

		passcode = "WrongPasscode";
		
		when(view.getRegistrationCode()).thenReturn(passcode);
		simulateEnterPress();
		setupRegistrationExpectations();
	}
	
	@Test
	public void shouldDoNothingBecauseNoEnterPress() {
		setupPresenter();
		presenter.go();

		passcode = "WrongPasscode";		

		simulateNoEnterPress();
	}


	@Test
	public void testRegisterPlayerCallback() {
		setupPresenter();

		presenter.eventNewUserPassword.onSuccess(true);
		verify(eventBus).registerPlayer(loginData.player.email);
		verify(eventBus).getUserLoginStatus("hostPageBaseURL");
	}
	
	@Test
	public void testNoSuccessPlayerCallback() {
		setupPresenter();

		presenter.eventNewUserPassword.onSuccess(false);
		verify(view).showRegistrationFail();
	}

	private void setupPresenter() {
		presenter = new RegistrationPresenter(view, loginData, eventBus, "hostPageBaseURL",shell);
	}

	private void setupInitializationExpects() {
		verify(view).addRegistrationEventHandler(presenter.checkNewUserPasswordHandler);
		verify(eventBus).addHandler(presenter.eventNewUserPassword);
		verify(view).setShell(shell);
	}

	private void setupRegistrationExpectations() {
		verify(eventBus).isNewUserPasswordOk(passcode);		
	}
	

	private void simulateRegistrationClick() {
		presenter.checkNewUserPasswordHandler.onClick(null);
	}
	
	private void simulateEnterPress() {
		presenter.checkNewUserPasswordHandler.onKeyPress(new KeyPressEvent() {
			@Override
			public char getCharCode() {
				return KeyCodes.KEY_ENTER;
				}
			});
	}
	
	private void simulateNoEnterPress() {
		presenter.checkNewUserPasswordHandler.onKeyPress(new KeyPressEvent() {
			@Override
			public char getCharCode() {
				return KeyCodes.KEY_TAB;
				}
			});
	}

}
