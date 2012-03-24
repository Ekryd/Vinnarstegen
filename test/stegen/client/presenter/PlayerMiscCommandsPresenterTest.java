package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.presenter.PlayerMiscCommandsPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerMiscCommandsPresenterTest {

	@Mock
	private Display view;
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;
	private PlayerMiscCommandsPresenter presenter;
	//private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	@Mock
	private PlayerCommandServiceAsync playerCommandService;

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	/*
	@SuppressWarnings("unchecked")
	@Test
	public void testEventScoresUpdated() throws ParseException {
		setupPresenter();
		presenter.go();

		reset(view, eventBus);
		view.changePlayerMiscCommandList(anyObject(List.class));
		verifyListContentForPreviousMethod();
		replay(view, eventBus);

		List<PlayerCommandDto> gameResults = new ArrayList<PlayerCommandDto>();
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(loginData.player, new SimpleDateFormat("yyyy-MM-dd").parse("2011-10-10"), "Loggade in");
		gameResults.add(playerScoreDto);
		presenter.eventUpdatePlayerMiscCommandListCallback.onSuccess(gameResults);
	}
	
	private void verifyListContentForPreviousMethod() {
		IAnswer<? extends Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				@SuppressWarnings("unchecked")
				List<PlayerMiscCommandRow> content = (List<PlayerMiscCommandRow>) getCurrentArguments()[0];
				assertEquals(1, content.size());
				assertEquals(loginData.player.nickname, content.get(0).performedBy);
				assertEquals("Loggade in", content.get(0).description);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}
*/
	@Test
	public void testUpdateListEvents() {
		setupPresenter();


		presenter.challangeEventHandler.handleEvent(new ChallengeEvent());
		verify(playerCommandService).getMiscPlayerCommandStack(10, presenter.miscPlayerCallback);
		reset(playerCommandService);

		presenter.changeNicknameEventHandler.handleEvent(new ChangeNicknameEvent(null));
		verify(playerCommandService).getMiscPlayerCommandStack(10, presenter.miscPlayerCallback);
		reset(playerCommandService);
		
		presenter.refreshEventHandler.handleEvent(new RefreshEvent(RefreshType.CHANGES_ON_SERVER_SIDE));
		verify(playerCommandService).getMiscPlayerCommandStack(10, presenter.miscPlayerCallback);
		reset(playerCommandService);

		presenter.undoEventHandler.handleEvent(new UndoEvent(null));
		verify(playerCommandService).getMiscPlayerCommandStack(10, presenter.miscPlayerCallback);
		reset(playerCommandService);
	}

	private void setupPresenter() {
		presenter = new PlayerMiscCommandsPresenter(view,gwtEventBus,playerCommandService);
	}

	private void setupInitializationExpects() {
		verify(gwtEventBus).addHandler(RefreshEvent.TYPE,presenter.refreshEventHandler);
		verify(gwtEventBus).addHandler(UndoEvent.TYPE, presenter.undoEventHandler);
		verify(gwtEventBus).addHandler(ChallengeEvent.TYPE, presenter.challangeEventHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE, presenter.changeNicknameEventHandler);
		verify(playerCommandService).getMiscPlayerCommandStack(10, presenter.miscPlayerCallback);
	}

	

}
