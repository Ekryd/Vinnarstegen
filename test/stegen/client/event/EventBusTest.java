package stegen.client.event;

import static org.easymock.EasyMock.*;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.callback.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventBusTest {

	private PlayerServiceAsync playerService;
	private PlayerCommandServiceAsync playerCommandService;

	@Before
	public void before() {
		playerService = createStrictMock(PlayerServiceAsync.class);
		playerCommandService = createStrictMock(PlayerCommandServiceAsync.class);
	}

	@After
	public void after() {
		verify(playerService, playerCommandService);
	}

	@Test
	public void testUserLoginStatus() {
		EventBus eventBus = EventBusImpl.create(playerCommandService, null, playerService);

		CheckUserLoginStatusCallback callback = createMockBuilder(CheckUserLoginStatusCallback.class)
				.createStrictMock();
		replay(callback);

		eventBus.addHandler(callback);

		playerService.userLoginStatus(eq("url"), isA(AsyncCallback.class));
		replay(playerCommandService, playerService);

		eventBus.checkUserLoginStatus("url");
	}

	@Test
	public void testUpdateMessages() {
		EventBus eventBus = EventBusImpl.create(playerCommandService, null, playerService);

		ChangedMessagesCallback callback = createMockBuilder(ChangedMessagesCallback.class).createStrictMock();
		callback.onSuccess(anyObject(List.class));
		replay(callback);

		eventBus.addHandler(callback);

		playerCommandService.getSendMessageCommandStack(eq(10), isA(AsyncCallback.class));
		simulateCallToMethodOnSuccessByService();
		replay(playerCommandService, playerService);

		eventBus.updateMessageList();
	}

	private void simulateCallToMethodOnSuccessByService() {
		IAnswer<Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				AsyncCallback<List<PlayerCommandDto>> callback = (AsyncCallback<List<PlayerCommandDto>>) getCurrentArguments()[1];
				List<PlayerCommandDto> result = new ArrayList<PlayerCommandDto>();
				callback.onSuccess(result);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}

}
