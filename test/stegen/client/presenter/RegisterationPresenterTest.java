package stegen.client.presenter;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.RegistrationPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class RegisterationPresenterTest {

	private RegistrationPresenter presenter;
	@Mock
	private Display view;
	@Mock
	private Shell shell;
	@Mock
	private PlayerServiceAsync playerService;
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;
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

		presenter.isNewUserPasswordOk.onSuccess(true);
		verify(playerService).registerPlayer(eq(loginData.player.email),any(AsyncCallback.class));
		verify(playerService).getUserLoginStatus(eq("hostPageBaseURL"),any(AsyncCallback.class));
	}
	

	@Test
	public void testUserLoginStatusCallback() {
		setupPresenter();

		presenter.userLoginStatusCallback.onSuccess(loginData);
		verify(gwtEventBus).fireEvent(any(LoginEvent.class));
	}

	@Test
	public void testNoSuccessPlayerCallback() {
		setupPresenter();

		presenter.isNewUserPasswordOk.onSuccess(false);
		verify(view).showRegistrationFail();
	}


	private void setupInitializationExpects() {
		verify(view).addRegistrationEventHandler(presenter.checkNewUserPasswordHandler);
		verify(view).setShell(shell);
	}

	private void setupPresenter() {
		presenter = new RegistrationPresenter(view, loginData, playerService,gwtEventBus, "hostPageBaseURL",shell);
	}
	private void setupRegistrationExpectations() {
		verify(playerService).isNewUserPasswordOk(eq(passcode), any(AsyncCallback.class));
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
