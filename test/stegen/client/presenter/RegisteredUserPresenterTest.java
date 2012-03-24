package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.RegisteredUserPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisteredUserPresenterTest {

	private RegisteredUserPresenter presenter;
	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	private String nickname;
	@Mock
	private Shell shell;
	@Mock
	private PlayerServiceAsync playerService;
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	@Test
	public void testEnterEmptyNickname() {
		setupPresenter();
		presenter.go();

		nickname = " ";
		when(view.getNewNickname()).thenReturn(nickname);
		simulateChangeNicknameClick();
	}

	@Test
	public void testEnterOkNickname() {
		setupPresenter();
		presenter.go();

		nickname = "Nicknick";
		when(view.getNewNickname()).thenReturn(nickname);
		simulateChangeNicknameClick();
		setupOkNicknameExpects();
	}

	@Test
	public void testChangeNicknameCallback() {
		setupPresenter();

		presenter.changeNicknameCallback.onSuccess("nickname");
		
		verify(gwtEventBus).fireEvent(any(ChangeNicknameEvent.class));
		
	}

	private void setupPresenter() {
		presenter = new RegisteredUserPresenter(view, loginData, playerService,gwtEventBus, shell);
	}

	private void setupInitializationExpects() {
		verify(view).setUserName("nickname");
		verify(view).addClickChangeUserNameHandler(presenter.changeUserNameClickHandler);
		verify(view).setShell(shell);
		
	}

	private void setupOkNicknameExpects() {
		playerService.changeNickname(loginData.player, nickname,presenter.changeNicknameCallback);
	}

	private void simulateChangeNicknameClick() {
		presenter.changeUserNameClickHandler.onClick(null);
	}
}
