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
	private LoginDataDto result;

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
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(result.player, new LocalDate(2011, 10, 10)
				.toDateMidnight().toDate(), "1 - 3");
		gameResults.add(playerScoreDto);
		presenter.eventUpdateGameResultListCallback.onSuccess(gameResults);
	}

	private void setupPresenter() {
		result = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new GameResultsPresenter(view, eventBus);
	}

	private void setupInitializationExpects() {
		eventBus.addHandler(presenter.eventUpdateGameResultListCallback);
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
				assertEquals(result.player.nickname, content.get(0).playerName);
				assertEquals(new LocalDate(2011, 10, 10).toDateMidnight().toDate(), content.get(0).gameDateTime);
				assertEquals("1 - 3", content.get(0).result);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}

}
