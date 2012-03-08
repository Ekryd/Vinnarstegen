package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.RegisteredUserPresenter.Display;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisteredUserPresenterTest {

	private RegisteredUserPresenter presenter;
	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	private String nickname;
	@Mock
	private EventBus eventBus;
	@Mock
	private Shell shell;

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

		presenter.eventCommandChangeNicknameHandler.onSuccess("nickname");
		
		verify(view).setUserName("nickname");
	}

	private void setupPresenter() {
		presenter = new RegisteredUserPresenter(view, loginData, eventBus,shell);
	}

	private void setupInitializationExpects() {
		verify(view).setUserName("nickname");
		verify(view).addClickChangeUserNameHandler(presenter.clickChangeUserNameHandler);
		verify(eventBus).addHandler(presenter.eventCommandChangeNicknameHandler);
		verify(view).setShell(shell);
		
	}

	private void setupOkNicknameExpects() {
		eventBus.changeNickname(loginData.player, nickname);
	}

	private void simulateChangeNicknameClick() {
		presenter.clickChangeUserNameHandler.onClick(null);
	}
}
