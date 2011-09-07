package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.RegisteredUserPresenter.Display;
import stegen.shared.*;

public class RegisteredUserPresenterTest {

	private RegisteredUserPresenter presenter;
	private Display view;
	private LoginDataDto loginData;
	private String nickname;
	private EventBus eventBus;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testEnterEmptyNickname() {
		setupPresenter();
		presenter.go();

		nickname = " ";
		setupEmptyNicknameExpects();

		simulateChangeNicknameClick();
	}

	@Test
	public void testEnterOkNickname() {
		setupPresenter();
		presenter.go();

		nickname = "Nicknick";
		setupOkNicknameExpects();

		simulateChangeNicknameClick();
	}

	@Test
	public void testChangeNicknameCallback() {
		setupPresenter();

		view.setUserName("nickname");
		replay(view);

		presenter.eventCommandChangeNicknameHandler.onSuccess(new PlayerDto(null, "nickname"));

		verify(view);
	}

	private void setupPresenter() {
		view = createStrictMock(Display.class);
		loginData = LoginDataDtoFactory.createLoginData();
		eventBus = createStrictMock(EventBus.class);
		presenter = new RegisteredUserPresenter(view, loginData, eventBus);
	}

	private void setupInitializationExpects() {
		view.setUserName("nickname");
		view.addClickChangeUserNameHandler(presenter.clickChangeUserNameHandler);
		eventBus.addHandler(presenter.eventCommandChangeNicknameHandler);
		replay(view, eventBus);
	}

	private void setupEmptyNicknameExpects() {
		reset(view, eventBus);
		expect(view.getNewNickname()).andReturn(nickname);
		replay(view, eventBus);
	}

	private void setupOkNicknameExpects() {
		reset(view, eventBus);
		expect(view.getNewNickname()).andReturn(nickname);
		eventBus.changeNickname(loginData.player, nickname);
		replay(view, eventBus);
	}

	private void simulateChangeNicknameClick() {
		presenter.clickChangeUserNameHandler.onClick(null);
	}

}
