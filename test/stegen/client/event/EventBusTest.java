package stegen.client.event;

import static org.easymock.EasyMock.*;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.callback.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

@SuppressWarnings({ "unchecked" })
public class EventBusTest {

	private PlayerServiceAsync playerService;
	private PlayerCommandServiceAsync playerCommandService;
	private ScoreServiceAsync scoreService;
	private EventBus eventBus;

	@Before
	public void before() {
		playerService = createStrictMock(PlayerServiceAsync.class);
		playerCommandService = createStrictMock(PlayerCommandServiceAsync.class);
		scoreService = createStrictMock(ScoreServiceAsync.class);
		eventBus = EventBusImpl.create(playerCommandService, scoreService, playerService);
	}

	@After
	public void after() {
		verify(playerService, playerCommandService, scoreService);
	}

	@Test
	public void testUserLoginStatus() {

		UserLoginStatusCallback callback = createMockBuilder(UserLoginStatusCallback.class).createStrictMock();
		replay(callback);

		eventBus.addHandler(callback);

		playerService.getUserLoginStatus(eq("url"), isA(AsyncCallback.class));
		replay(playerCommandService, playerService);

		eventBus.getUserLoginStatus("url");
	}

	@Test
	public void testUpdateMessages() {
		EventBus eventBus = EventBusImpl.create(playerCommandService, null, playerService);

		UpdateSendMessageListCallback callback = createMockBuilder(UpdateSendMessageListCallback.class)
				.withConstructor().createStrictMock();
		callback.onSuccess(anyObject(List.class));
		replay(callback);

		eventBus.addHandler(callback);

		playerCommandService.getSendMessageCommandStack(eq(10), isA(AsyncCallback.class));
		simulateCallToMethodOnSuccessByService(new ArrayList<PlayerCommandDto>());
		replay(playerCommandService, playerService);

		eventBus.updateSendMessageList();
	}

	private void simulateCallToMethodOnSuccessByService(final Object returnValue) {
		IAnswer<Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				AsyncCallback<Object> callback = (AsyncCallback<Object>) getCurrentArguments()[1];
				callback.onSuccess(returnValue);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}

}
