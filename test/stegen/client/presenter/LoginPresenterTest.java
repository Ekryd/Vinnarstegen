package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.*;

import stegen.client.gui.*;
import stegen.client.presenter.LoginPresenter.Display;
import stegen.shared.*;

public class LoginPresenterTest {

	@Test
	public void test() {
		Display view = Mockito.mock(Display.class);
		Shell shell = Mockito.mock(Shell.class);
		LoginDataDto loginData = LoginDataDto.userIsNotLoggedIn("signInUrl");
		new LoginPresenter(view, loginData,shell).go();

		
		verify(view).setSignInUrl("signInUrl");
		verify(view).setShell(shell);

	}

}
