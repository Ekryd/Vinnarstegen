package stegen.client.presenter;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.playeraction.*;
import stegen.client.presenter.GameResultsPresenter.Display;
import stegen.shared.*;

import com.google.appengine.repackaged.org.joda.time.*;

public class GameResultsPresenterTest {

	private Display view;
	private EventBus eventBus;
	private GameResultsPresenter presenter;
	private LoginDataDto loginData;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEventScoresUpdated() {
		setupPresenter();
		presenter.go();

		reset(view, eventBus);
		view.changeGameResultList(anyObject(List.class));
		verifyListContentForPreviousMethod();
		replay(view, eventBus);

		List<PlayerCommandDto> gameResults = new ArrayList<PlayerCommandDto>();
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(loginData.player, new LocalDate(2011, 10, 10)
				.toDateMidnight().toDate(), "1 - 3");
		gameResults.add(playerScoreDto);
		presenter.eventUpdateGameResultListCallback.onSuccess(gameResults);
	}

	@Test
	public void testUpdateListEvents() {
		setupPresenter();

		eventBus.updateGameResultList();
		replay(eventBus, view);
		presenter.eventCommandClearScoresCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updateGameResultList();
		replay(eventBus, view);
		presenter.eventCommandChangeNicknameCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updateGameResultList();
		replay(eventBus, view);
		presenter.eventCommandPlayerWonCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updateGameResultList();
		replay(eventBus, view);
		presenter.eventCommandRefreshCallback.onSuccess(RefreshType.CHANGES_ON_SERVER_SIDE);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updateGameResultList();
		replay(eventBus, view);
		presenter.eventCommandUndoCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new GameResultsPresenter(view, eventBus);
	}

	private void setupInitializationExpects() {
		eventBus.addHandler(presenter.eventUpdateGameResultListCallback);
		eventBus.addHandler(presenter.eventCommandRefreshCallback);
		eventBus.addHandler(presenter.eventCommandUndoCallback);
		eventBus.addHandler(presenter.eventCommandPlayerWonCallback);
		eventBus.addHandler(presenter.eventCommandClearScoresCallback);
		eventBus.addHandler(presenter.eventCommandChangeNicknameCallback);
		eventBus.updateGameResultList();
		replay(view, eventBus);
	}

	private void verifyListContentForPreviousMethod() {
		IAnswer<? extends Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				@SuppressWarnings("unchecked")
				List<GameResultsRow> content = (List<GameResultsRow>) getCurrentArguments()[0];
				assertEquals(1, content.size());
				assertEquals(loginData.player.nickname, content.get(0).playerName);
				assertEquals(new LocalDate(2011, 10, 10).toDateMidnight().toDate(), content.get(0).gameDateTime);
				assertEquals("1 - 3", content.get(0).result);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}

}
