package stegen.client.presenter;


import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.presenter.MessagesPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class MessagesPresenterTest {

	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	@Mock
	private  PlayerCommandServiceAsync playerCommandService;
	@Mock
	private  PlayerServiceAsync playerService;
	@Mock
	private  com.google.gwt.event.shared.EventBus gwtEventBus;
	private MessagesPresenter presenter;

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();	
	}

	
	@Test
	public void testSendEmptyMessage() {
		setupPresenter();
		presenter.go();
		
		setupSendEmptyMessageExpects();
		
		simulateSendMessage();
	}

	@Test
	public void testSendOkMessage() {
		setupPresenter();
		presenter.go();
		
		setupSendOkMessageExpects();

		simulateSendMessage();
		
		verify(playerService).sendMessage(loginData.player, "message", presenter.eventCommandSendMessageCallback);
	}

	@Test
	public void testSendMessageCallback() {
		setupPresenter();

		presenter.eventCommandSendMessageCallback.onSuccess(null);
		verify(playerCommandService).getSendMessageCommandStack(10, presenter.eventUpdateSendMessageListCallback);
		verify(view).clearText();
	}
/*
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateSendMessageListCallback() {
		setupPresenter();

		List<PlayerCommandDto> list = new ArrayList<PlayerCommandDto>();
		list.add(new PlayerCommandDto(loginData.player, new Date(10000L), "description"));
		
		presenter.eventUpdateSendMessageListCallback.onSuccess(list);
		
		view.changeMessageList(any(List.class));
		
		verifyListContentForPreviousMethod();
	}
*/
	@Test
	public void testChangeNicknameCallback() {
		setupPresenter();
		presenter.changeNicknameEventHandler.handleEvent(null);
		verify(playerCommandService).getSendMessageCommandStack(10, presenter.eventUpdateSendMessageListCallback);
	}

	@Test
	public void testRefreshCallback() {
		setupPresenter();
		presenter.refreshEventHandler.handleEvent(new RefreshEvent(RefreshType.CHANGES_ON_SERVER_SIDE));
		verify(playerCommandService).getSendMessageCommandStack(10, presenter.eventUpdateSendMessageListCallback);	}

	private void setupPresenter() {
		presenter = new MessagesPresenter(view, loginData,playerCommandService,playerService, gwtEventBus);
	}

	private void setupInitializationExpects() {
		verify(view).addSendMessageHandler(presenter.clickSendMessageHandler);
		verify(gwtEventBus).addHandler(RefreshEvent.TYPE,presenter.refreshEventHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE, presenter.changeNicknameEventHandler);
		verify(playerCommandService).getSendMessageCommandStack(10, presenter.eventUpdateSendMessageListCallback);
	}

	
	private void setupSendEmptyMessageExpects() {
		when(view.getMessageInputContent()).thenReturn(" ");
	}

	private void simulateSendMessage() {
		presenter.clickSendMessageHandler.onClick(null);
	}

	private void setupSendOkMessageExpects() {
		when(view.getMessageInputContent()).thenReturn("message");
	}

	/*private void verifyListContentForPreviousMethod() {
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
	}*/

}
