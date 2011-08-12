package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.MessagesPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

public class MessagesPresenterTest {

	private Display view;
	private LoginDataDto result;
	private EventBus eventBus;
	private MessagesPresenter presenter;
	private MessagePrefixGenerator messagePrefixGenerator;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	private void setupPresenter() {
		result = createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		messagePrefixGenerator = createStrictMock(MessagePrefixGenerator.class);
		presenter = new MessagesPresenter(view, result, messagePrefixGenerator, eventBus);

		MessagePrefix messagePrefix = new MessagePrefix("buttonText", "actionText");
		expect(messagePrefixGenerator.getRandomizedPrefix()).andReturn(messagePrefix);
		replay(messagePrefixGenerator);
	}

	private LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return result;
	}

	private void setupInitializationExpects() {
		view.setMessageButtonTitle("buttonText");
		view.addClickOpenMessageInputHandler(presenter.clickOpenMessageInputHandler);
		view.addClickSendMessageHandler(presenter.clickSendMessageHandler);
		eventBus.addHandler(presenter.eventSendMessageCallback);
		eventBus.addHandler(presenter.eventChangedMessagesCallback);
		eventBus.updateMessageList();
		replay(view, eventBus);
	}

	@Test
	public void testOpenInputDialog() {
		setupPresenter();
		presenter.go();

		setupOpenDialogExpects();

		simulateOpenDialogClick();
	}

	private void setupOpenDialogExpects() {
		reset(view, eventBus, messagePrefixGenerator);
		view.setMessageInputTitle("nickname actionText");
		replay(view, eventBus, messagePrefixGenerator);
	}

	private void simulateOpenDialogClick() {
		presenter.clickOpenMessageInputHandler.onClick(null);
	}

	@Test
	public void testSendEmptyMessage() {
		setupPresenter();
		presenter.go();
		simulateOpenDialogClick();

		setupSendEmptyMessageExpects();

		simulateSendMessage();
	}

	private void setupSendEmptyMessageExpects() {
		reset(view, eventBus, messagePrefixGenerator);
		expect(view.getMessageInputContent()).andReturn(" ");
		replay(view, eventBus, messagePrefixGenerator);
	}

	private void simulateSendMessage() {
		presenter.clickSendMessageHandler.onClick(null);
	}

	@Test
	public void testSendOkMessage() {
		setupPresenter();
		presenter.go();
		simulateOpenDialogClick();

		setupSendOkMessageExpects();

		simulateSendMessage();
	}

	private void setupSendOkMessageExpects() {
		reset(view, eventBus, messagePrefixGenerator);
		expect(view.getMessageInputContent()).andReturn("message");
		eventBus.sendMessage(result.player, "nickname actionText message");
		replay(view, eventBus, messagePrefixGenerator);
	}

}
