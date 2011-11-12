package stegen.client.presenter;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.*;

import org.easymock.*;
import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.playeraction.*;
import stegen.client.presenter.LoginStatusesPresenter.Display;
import stegen.shared.*;

import com.google.appengine.repackaged.org.joda.time.*;

public class LoginStatusesPresenterTest {

	private Display view;
	private EventBus eventBus;
	private LoginStatusesPresenter presenter;
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
		view.changeLoginStatusList(anyObject(List.class));
		verifyListContentForPreviousMethod();
		replay(view, eventBus);

		List<PlayerCommandDto> gameResults = new ArrayList<PlayerCommandDto>();
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(loginData.player, new LocalDate(2011, 10, 10)
				.toDateMidnight().toDate(), "Ångrade ingenting");
		gameResults.add(playerScoreDto);
		presenter.eventUpdateLoginStatusListCallback.onSuccess(gameResults);
	}

	@Test
	public void testUpdateListEvents() {
		setupPresenter();

		eventBus.updateLoginStatusList();
		replay(eventBus, view);
		presenter.eventCommandChangeNicknameCallback.onSuccess(null);
		verify(eventBus, view);
		reset(eventBus, view);

		eventBus.updateLoginStatusList();
		replay(eventBus, view);
		presenter.eventCommandRefreshCallback.onSuccess(RefreshType.CHANGES_ON_SERVER_SIDE);
		verify(eventBus, view);
		reset(eventBus, view);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new LoginStatusesPresenter(view, eventBus);
	}

	private void setupInitializationExpects() {
		eventBus.addHandler(presenter.eventUpdateLoginStatusListCallback);
		eventBus.addHandler(presenter.eventCommandRefreshCallback);
		eventBus.addHandler(presenter.eventCommandChangeNicknameCallback);
		eventBus.updateLoginStatusList();
		replay(view, eventBus);
	}

	private void verifyListContentForPreviousMethod() {
		IAnswer<? extends Object> answer = new IAnswer<Object>() {

			@Override
			public Object answer() throws Throwable {
				@SuppressWarnings("unchecked")
				List<LoginStatusRow> content = (List<LoginStatusRow>) getCurrentArguments()[0];
				assertEquals(1, content.size());
				assertEquals(loginData.player.nickname, content.get(0).performedBy);
				assertEquals("Ångrade ingenting", content.get(0).description);
				return null;
			}
		};
		expectLastCall().andAnswer(answer);
	}

}
