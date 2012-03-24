package stegen.client.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.presenter.ScorePresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class ScorePresenterTest {
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;
	@Mock
	private ScoreServiceAsync scoreService;
	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	private ScorePresenter presenter;

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	@Test
	public void testPressCleanAllScores() {
		setupPresenter();
		presenter.go();

		simulateCleanAllScores();
		
		setupCleanAllScoresExpects();
	}

	@Test
	public void shouldChangeNickname() {
		setupPresenter();
		
		presenter.changeNicknameEventHandler.handleEvent(new ChangeNicknameEvent("Jan-Ove"));
		verify(scoreService).getPlayerScoreList(loginData.player.email,presenter.eventChangedScoresCallback);
		
	
	}

	@Test
	public void shouldUndo() {
		setupPresenter();

		
			presenter.undoEventHandler.handleEvent(null);
			verify(scoreService).getPlayerScoreList(loginData.player.email,presenter.eventChangedScoresCallback);
		}

	
	@Test
	public void shouldRefresh() {
		setupPresenter();

		presenter.refreshEventHandler.handleEvent( new RefreshEvent(RefreshType.CHANGES_ON_SERVER_SIDE));
		verify(scoreService).getPlayerScoreList(loginData.player.email,presenter.eventChangedScoresCallback);
	}

	@Test
	public void shouldCallPlayerWon() {
		setupPresenter();

		presenter.gamePlayedEventHandler.handleEvent(null);
		verify(scoreService).getPlayerScoreList(loginData.player.email,presenter.eventChangedScoresCallback);
	}
	@Test
	public void shouldClearScores() {
		setupPresenter();

		presenter.clearScoresCallback.onSuccess(null);
		verify(gwtEventBus).fireEvent(any(ClearScoresEvent.class));
	}

	private void setupPresenter() {
		presenter = new ScorePresenter(view, loginData, gwtEventBus,scoreService);
	}

	private void setupInitializationExpects() {
		verify(view).addCleanScoresHandler(presenter.clickCleanScoresHandler);
		verify(gwtEventBus).addHandler(RefreshEvent.TYPE,presenter.refreshEventHandler);
		verify(gwtEventBus).addHandler(ClearScoresEvent.TYPE, presenter.clearScoresEventHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE, presenter.changeNicknameEventHandler);
		verify(scoreService).getPlayerScoreList(loginData.player.email,presenter.eventChangedScoresCallback);
	}

	private void setupCleanAllScoresExpects() {
		verify(scoreService).clearAllScores(loginData.player,presenter.clearScoresCallback);
	}

	private void simulateCleanAllScores() {
		presenter.clickCleanScoresHandler.onClick(null);
	}

/*
	@SuppressWarnings("unchecked")
	@Test
	public void testEventScoresUpdated() {
		setupPresenter();
		presenter.go();

		view.changeScoreList(any(List.class));
		verifyListContentForPreviousMethod();
		
		List<PlayerScoreDto> scores = new ArrayList<PlayerScoreDto>();
		PlayerScoreDto playerScoreDto = new PlayerScoreDto(loginData.player, 42, loginData.player, "date");
		playerScoreDto.ranking = 10;
		scores.add(playerScoreDto);
		presenter.eventChangedScoresCallback.onSuccess(scores);
	}
	
	private void verifyListContentForPreviousMethod() {
		IAnswer<? extends Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				@SuppressWarnings("unchecked")
				List<ScoreTableRow> content = (List<ScoreTableRow>) getCurrentArguments()[0];
				assertEquals(1, content.size());
				assertEquals(loginData.player.nickname, content.get(0).changedBy);
				assertEquals("date", content.get(0).changedDateTime);
				assertEquals(true, content.get(0).currentUser);
				assertEquals(loginData.player.nickname, content.get(0).player.nickname);
				assertEquals("10", content.get(0).ranking);
				assertEquals("42", content.get(0).score);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}
*/
}
