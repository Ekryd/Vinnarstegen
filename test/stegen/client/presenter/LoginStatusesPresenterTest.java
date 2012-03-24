package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.presenter.LoginStatusesPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginStatusesPresenterTest {

	@Mock
	private Display view;
	@Mock
	private com.google.gwt.event.shared.EventBus gwtEventBus;
	@Mock
	private PlayerCommandServiceAsync playerCommandService;
	private LoginStatusesPresenter presenter;
	//private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}


	@Test
	public void testUpdateListEvents() {
		setupPresenter();
		presenter.changeNicknameEventHandler.handleEvent(null);
		verify(playerCommandService).getLoginStatusCommandStack(10, presenter.loginStatusStackCallback);
		reset(playerCommandService);

		presenter.refreshEventHandler.handleEvent(new RefreshEvent( RefreshType.CHANGES_ON_SERVER_SIDE));
		verify(playerCommandService).getLoginStatusCommandStack(10, presenter.loginStatusStackCallback);
	}

	private void setupPresenter() {
		presenter = new LoginStatusesPresenter(view,playerCommandService,gwtEventBus);
	}

	private void setupInitializationExpects() {
		verify(gwtEventBus).addHandler(RefreshEvent.TYPE,presenter.refreshEventHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE, presenter.changeNicknameEventHandler);
		verify(playerCommandService).getLoginStatusCommandStack(10, presenter.loginStatusStackCallback);
	}
	/*
	@SuppressWarnings("unchecked")
	@Test
	public void testEventScoresUpdated() throws ParseException {
		setupPresenter();
		presenter.go();

		view.changeLoginStatusList(anyObject(List.class));
		verifyListContentForPreviousMethod();
		replay(view, eventBus);

		List<PlayerCommandDto> gameResults = new ArrayList<PlayerCommandDto>();
		PlayerCommandDto playerScoreDto = new PlayerCommandDto(loginData.player, new SimpleDateFormat("yyyy-MM-dd").parse("2011-10-10"), "Ångrade ingenting");
		gameResults.add(playerScoreDto);
		presenter.eventUpdateLoginStatusListCallback.onSuccess(gameResults);
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
	}*/
}
