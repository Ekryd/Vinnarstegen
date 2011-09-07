package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.message.*;
import stegen.client.presenter.MessagesPresenter.Display;
import stegen.client.service.*;
import stegen.client.service.messageprefix.*;
import stegen.shared.*;

public class MessagesPresenterTest {

	private Display view;
	private LoginDataDto loginData;
	private EventBus eventBus;
	private MessagesPresenter presenter;
	private MessagePrefixGenerator messagePrefixGenerator;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testOpenInputDialog() {
		setupPresenter();
		presenter.go();

		setupOpenDialogExpects();

		simulateOpenDialogClick();
	}

	@Test
	public void testSendEmptyMessage() {
		setupPresenter();
		presenter.go();
		simulateOpenDialogClick();

		setupSendEmptyMessageExpects();

		simulateSendMessage();
	}

	@Test
	public void testSendOkMessage() {
		setupPresenter();
		presenter.go();
		simulateOpenDialogClick();

		setupSendOkMessageExpects();

		simulateSendMessage();
	}

	@Test
	public void testSendMessageCallback() {
		setupPresenter();

		eventBus.updateSendMessageList();
		replay(eventBus);

		presenter.eventCommandSendMessageCallback.onSuccess(null);

		verify(eventBus);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateSendMessageListCallback() {
		setupPresenter();

		view.changeMessageList(anyObject(List.class));
		verifyListContentForPreviousMethod();
		replay(view);

		List<PlayerCommandDto> list = new ArrayList<PlayerCommandDto>();
		list.add(new PlayerCommandDto(loginData.player, new Date(10000L), "description"));
		presenter.eventUpdateSendMessageListCallback.onSuccess(list);

		verify(view);
	}

	@Test
	public void testChangeNicknameCallback() {
		setupPresenter();

		eventBus.updateSendMessageList();
		replay(eventBus, view);
		presenter.eventCommandChangeNicknameCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);
	}

	@Test
	public void testRefreshCallback() {
		setupPresenter();

		view.setMessageButtonTitle("buttonText");
		eventBus.updateSendMessageList();
		replay(eventBus, view);
		presenter.eventCommandRefreshCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		messagePrefixGenerator = createStrictMock(MessagePrefixGenerator.class);
		presenter = new MessagesPresenter(view, loginData, messagePrefixGenerator, eventBus);

		MessagePrefix messagePrefix = new MessagePrefix("buttonText", "actionText");
		expect(messagePrefixGenerator.getRandomizedPrefix()).andReturn(messagePrefix);
		replay(messagePrefixGenerator);
	}

	private void setupInitializationExpects() {
		view.setMessageButtonTitle("buttonText");
		view.addClickOpenMessageInputHandler(presenter.clickOpenMessageInputHandler);
		view.addClickSendMessageHandler(presenter.clickSendMessageHandler);
		eventBus.addHandler(presenter.eventCommandSendMessageCallback);
		eventBus.addHandler(presenter.eventUpdateSendMessageListCallback);
		eventBus.addHandler(presenter.eventCommandRefreshCallback);
		eventBus.addHandler(presenter.eventCommandChangeNicknameCallback);
		eventBus.updateSendMessageList();
		replay(view, eventBus);
	}

	private void setupOpenDialogExpects() {
		reset(view, eventBus, messagePrefixGenerator);
		view.setMessageInputTitle("nickname actionText");
		replay(view, eventBus, messagePrefixGenerator);
	}

	private void simulateOpenDialogClick() {
		presenter.clickOpenMessageInputHandler.onClick(null);
	}

	private void setupSendEmptyMessageExpects() {
		reset(view, eventBus, messagePrefixGenerator);
		expect(view.getMessageInputContent()).andReturn(" ");
		replay(view, eventBus, messagePrefixGenerator);
	}

	private void simulateSendMessage() {
		presenter.clickSendMessageHandler.onClick(null);
	}

	private void setupSendOkMessageExpects() {
		reset(view, eventBus, messagePrefixGenerator);
		expect(view.getMessageInputContent()).andReturn("message");
		eventBus.sendMessage(loginData.player, "nickname actionText message");
		replay(view, eventBus, messagePrefixGenerator);
	}

	private void verifyListContentForPreviousMethod() {
		IAnswer<Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				@SuppressWarnings("unchecked")
				List<MessageTableRow> content = (List<MessageTableRow>) getCurrentArguments()[0];
				Assert.assertEquals(1, content.size());
				Assert.assertEquals("description", content.get(0).message);
				Assert.assertEquals(10000L, content.get(0).messageDate.getTime());
				Assert.assertEquals("nickname", content.get(0).playerName);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}

}
