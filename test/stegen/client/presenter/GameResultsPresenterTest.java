package stegen.client.presenter;

import static org.mockito.Matchers.*;

import static org.mockito.Mockito.*;

import java.text.*;
import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import com.google.gwt.user.client.rpc.*;

import stegen.client.event.*;
import stegen.client.presenter.GameResultsPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class GameResultsPresenterTest {
	@Mock
	private Display view;
	@Mock
	private com.google.gwt.event.shared.EventBus gwtEventBus;
	@Mock
	private  PlayerCommandServiceAsync playerCommandService;

	private GameResultsPresenter presenter;
	
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	
	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	@Test
	public void testEventScoresUpdated() throws ParseException {
		setupPresenter();
		presenter.go();

		view.changeGameResultList(any(List.class));
		//verifyListContentForPreviousMethod();
		
		List<PlayerCommandDto> gameResults = new ArrayList<PlayerCommandDto>();
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(loginData.player, new SimpleDateFormat("yyyy-MM-dd").parse("2011-10-10"), "1 - 3");
		gameResults.add(playerScoreDto);
		presenter.eventUpdateGameResultListCallback.onSuccess(gameResults);
	}

	@Test
	public void testUpdateListEvents() {
		setupPresenter();
		
		presenter.clearScoresEventHandler.handleEvent(null);
		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
		reset(playerCommandService);
		
		presenter.changeNicknameEventHandler.handleEvent(null);
		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
		reset(playerCommandService);
		
		presenter.gamePlayedEventHandler.handleEvent(null);
		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
		reset(playerCommandService);
		
		presenter.refreshEventHandler.handleEvent(new RefreshEvent(RefreshType.CHANGES_ON_SERVER_SIDE));
		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
		reset(playerCommandService);
		
		presenter.undoEventHandler.handleEvent(null);
		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
	}

	private void setupPresenter() {
		presenter = new GameResultsPresenter(view,gwtEventBus, playerCommandService);
	}
	
	@Test
	public void shouldUpdateGareResultList(){
		setupPresenter();
		presenter.refreshEventHandler.handleEvent(new RefreshEvent(RefreshType.CHANGES_ON_SERVER_SIDE));

		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
	}

	private void setupInitializationExpects() {
		verify(gwtEventBus).addHandler(RefreshEvent.TYPE,presenter.refreshEventHandler);
		verify(gwtEventBus).addHandler(UndoEvent.TYPE, presenter.undoEventHandler);
		verify(gwtEventBus).addHandler(GamePlayedEvent.TYPE,presenter.gamePlayedEventHandler);
		verify(gwtEventBus).addHandler(ClearScoresEvent.TYPE, presenter.clearScoresEventHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE,presenter.changeNicknameEventHandler);
		verify(playerCommandService).getGameResultCommandStack(eq(10),any(AsyncCallback.class));
		
	}
/*
	private void verifyListContentForPreviousMethod() {
		IAnswer<? extends Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				@SuppressWarnings("unchecked")
				List<GameResultsRow> content = (List<GameResultsRow>) getCurrentArguments()[0];
				assertEquals(1, content.size());
				assertEquals(loginData.player.nickname, content.get(0).playerName);
				assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2011-10-10"), content.get(0).gameDateTime);
				assertEquals("1 - 3", content.get(0).result);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}
*/
}
