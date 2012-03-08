package stegen.client.presenter;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.gui.*;
import stegen.client.presenter.NonregisteredUserPresenter.Display;
import stegen.shared.*;
@RunWith(MockitoJUnitRunner.class)
public class NonregisteredUserPresenterTest {

	private NonregisteredUserPresenter presenter;
	@Mock
	private Display view;
	@Mock
	private Shell shell;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	private void setupPresenter() {
		presenter = new NonregisteredUserPresenter(view, loginData,shell);
	}

	private void setupInitializationExpects() {
		verify(view).setUserName(loginData.player.nickname);
		verify(view).setShell(shell);
	}
}
