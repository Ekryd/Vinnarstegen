package stegen.client.presenter;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.RegisteredUserPresenter.Display;
import stegen.shared.*;

public class RegisteredUserPresenterTest {

	private RegisteredUserPresenter presenter;
	private Display view;
	private LoginDataDto result;
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

	private void setupPresenter() {
		view = EasyMock.createStrictMock(Display.class);
		result = createLoginData();
		eventBus = EasyMock.createStrictMock(EventBus.class);
		presenter = new RegisteredUserPresenter(view, result, eventBus);
	}

	private LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return result;
	}

	private void setupInitializationExpects() {
		view.setUserName("nickname");
		view.addClickChangeUserNameHandler(presenter.clickChangeUserNameHandler);
		eventBus.addHandler(presenter.eventChangeNicknameHandler);
		EasyMock.replay(view, eventBus);
	}

	private void setupEmptyNicknameExpects() {
		EasyMock.reset(view, eventBus);
		EasyMock.expect(view.getNewNickname()).andReturn(nickname);
		EasyMock.replay(view, eventBus);
	}

	private void setupOkNicknameExpects() {
		EasyMock.reset(view, eventBus);
		EasyMock.expect(view.getNewNickname()).andReturn(nickname);
		eventBus.changeNickname(result.player, nickname);
		EasyMock.replay(view, eventBus);
	}

	private void simulateChangeNicknameClick() {
		presenter.clickChangeUserNameHandler.onClick(null);
	}

}
