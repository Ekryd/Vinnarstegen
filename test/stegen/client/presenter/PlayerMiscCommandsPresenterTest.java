package stegen.client.presenter;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.playeraction.*;
import stegen.client.presenter.PlayerMiscCommandsPresenter.Display;
import stegen.shared.*;

import com.google.appengine.repackaged.org.joda.time.*;

public class PlayerMiscCommandsPresenterTest {

	private Display view;
	private EventBus eventBus;
	private PlayerMiscCommandsPresenter presenter;
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
		view.changePlayerMiscCommandList(anyObject(List.class));
		verifyListContentForPreviousMethod();
		replay(view, eventBus);

		List<PlayerCommandDto> gameResults = new ArrayList<PlayerCommandDto>();
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(loginData.player, new LocalDate(2011, 10, 10)
				.toDateMidnight().toDate(), "Loggade in");
		gameResults.add(playerScoreDto);
		presenter.eventUpdatePlayerMiscCommandListCallback.onSuccess(gameResults);
	}

	@Test
	public void testUpdateListEvents() {
		setupPresenter();

		eventBus.updatePlayerMiscCommandList();
		replay(eventBus, view);
		presenter.eventCommandChangeNicknameCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updatePlayerMiscCommandList();
		replay(eventBus, view);
		presenter.eventCommandChallengeCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updatePlayerMiscCommandList();
		replay(eventBus, view);
		presenter.eventCommandRefreshCallback.onSuccess(RefreshType.CHANGES_ON_SERVER_SIDE);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updatePlayerMiscCommandList();
		replay(eventBus, view);
		presenter.eventCommandUndoCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new PlayerMiscCommandsPresenter(view, eventBus);
	}

	private void setupInitializationExpects() {
		eventBus.addHandler(presenter.eventUpdatePlayerMiscCommandListCallback);
		eventBus.addHandler(presenter.eventCommandRefreshCallback);
		eventBus.addHandler(presenter.eventCommandUndoCallback);
		eventBus.addHandler(presenter.eventCommandChallengeCallback);
		eventBus.addHandler(presenter.eventCommandChangeNicknameCallback);
		eventBus.updatePlayerMiscCommandList();
		replay(view, eventBus);
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

}
