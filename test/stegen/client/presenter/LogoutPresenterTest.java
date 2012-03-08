package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.gui.*;
import stegen.client.presenter.LogoutPresenter.Display;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class LogoutPresenterTest {

	private LogoutPresenter presenter;
	@Mock
	private Display view;
	@Mock
	private Shell shell;
	
	private LoginDataDto loginData;

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		presenter = new LogoutPresenter(view, loginData,shell);
	}

	private void setupInitializationExpects() {
		verify(view).setLogoutUrl(loginData.logoutUrl);
		verify(view).setShell(shell);
	}

}
