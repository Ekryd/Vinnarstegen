package stegen.client.event;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.callback.*;
import stegen.client.service.*;
import stegen.shared.*;

public class EventBusImplTest {

	private PlayerServiceAsync playerService;
	private PlayerCommandServiceAsync playerCommandService;
	private ScoreServiceAsync scoreService;
	private EventBus eventBus;
	private LoginDataDto loginDataDto = LoginDataDtoFactory.createLoginData();

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
	public void testGetUserLoginStatus() {
		UpdateLoginStatusCallback callback = creteAndAddCallbackToEventBus(UpdateLoginStatusCallback.class);

		playerService.getUserLoginStatus("url", callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.getUserLoginStatus("url");
	}

	@Test
	public void testChangeNickname() {
		CommandChangeNicknameCallback callback = creteAndAddCallbackToEventBus(CommandChangeNicknameCallback.class);

		playerService.changeNickname(loginDataDto.player, "nickname", callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.changeNickname(loginDataDto.player, "nickname");
	}

	@Test
	public void testRegisterPlayer() {
		CommandRegisterPlayerCallback callback = creteAndAddCallbackToEventBus(CommandRegisterPlayerCallback.class);

		playerService.registerPlayer(loginDataDto.player.email, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.registerPlayer(loginDataDto.player.email);
	}

	@Test
	public void testNewUserPasswordOk() {
		UpdateIsNewUserPasswordOkCallback callback = creteAndAddCallbackToEventBus(UpdateIsNewUserPasswordOkCallback.class);
		final String NEW_USER_PASSWORD = "Waldner";

		playerService.isNewUserPasswordOk(NEW_USER_PASSWORD, callback);

		replay(playerCommandService, playerService, scoreService);

		eventBus.isNewUserPasswordOk(NEW_USER_PASSWORD);
	}

	@Test
	public void testSendMessage() {
		CommandSendMessageCallback callback = creteAndAddCallbackToEventBus(CommandSendMessageCallback.class);

		playerService.sendMessage(loginDataDto.player, "message", callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.sendMessage(loginDataDto.player, "message");
	}

	@Test
	public void testUpdateSendMessageList() {
		UpdateSendMessageListCallback callback = creteAndAddCallbackToEventBus(UpdateSendMessageListCallback.class);

		playerCommandService.getSendMessageCommandStack(10, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.updateSendMessageList();
	}

	@Test
	public void testUpdatePlayerScoreList() {
		UpdatePlayerScoreListCallback callback = creteAndAddCallbackToEventBus(UpdatePlayerScoreListCallback.class);

		scoreService.getPlayerScoreList(loginDataDto.player.email, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.updatePlayerScoreList(loginDataDto.player.email);
	}

	@Test
	public void testClearAllScores() {
		CommandClearScoresCallback callback = creteAndAddCallbackToEventBus(CommandClearScoresCallback.class);

		scoreService.clearAllScores(loginDataDto.player, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.clearAllScores(loginDataDto.player);
	}

	@Test
	public void testChallengePlayer() {
		ChallengeMessageDto message = createNiceMock(ChallengeMessageDto.class);
		CommandChallengeCallback callback = creteAndAddCallbackToEventBus(CommandChallengeCallback.class);

		scoreService.challengePlayer(message, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.challengePlayer(message);
	}

	@Test
	public void testUndoPlayerCommand() {
		CommandUndoCallback callback = creteAndAddCallbackToEventBus(CommandUndoCallback.class);

		playerCommandService.undoPlayerCommand(loginDataDto.player, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.undoPlayerCommand(loginDataDto.player);
	}

	@Test
	public void testPlayerWonOverPlayer() {
		GameResultDto result = GameResultDto.createEmptyGameResult();
		CommandPlayerWonCallback callback = creteAndAddCallbackToEventBus(CommandPlayerWonCallback.class);

		scoreService.playerWonOverPlayer(loginDataDto.player, loginDataDto.player, result, loginDataDto.player,
				callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.playerWonOverPlayer(loginDataDto.player, loginDataDto.player, result, loginDataDto.player);
	}

	@Test
	public void testRefresh() {
		UpdateRefreshCallback callback = createMockBuilder(UpdateRefreshCallback.class).withConstructor().createMock();
		eventBus.addHandler(callback);

		new RefreshServiceStub(playerCommandService).alwaysReturnServerChange();

		callback.onSuccess(RefreshType.CHANGES_ON_SERVER_SIDE);
		replay(callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.refresh();
		verify(callback);
	}

	@Test
	public void testUpdateGameResultList() {
		UpdateGameResultListCallback callback = creteAndAddCallbackToEventBus(UpdateGameResultListCallback.class);

		playerCommandService.getGameResultCommandStack(10, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.updateGameResultList();
	}

	@Test
	public void testUpdateUndoCommand() {
		UpdateUndoCommandCallback callback = creteAndAddCallbackToEventBus(UpdateUndoCommandCallback.class);

		playerCommandService.getUndoCommand(callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.updateUndoCommand();
	}

	@Test
	public void testUpdatePlayerMiscCommandList() {
		UpdatePlayerMiscCommandListCallback callback = creteAndAddCallbackToEventBus(UpdatePlayerMiscCommandListCallback.class);

		playerCommandService.getMiscPlayerCommandStack(10, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.updatePlayerMiscCommandList();
	}

	@Test
	public void testUpdateLoginStatusList() {
		UpdateLoginStatusListCallback callback = creteAndAddCallbackToEventBus(UpdateLoginStatusListCallback.class);

		playerCommandService.getLoginStatusCommandStack(10, callback);
		replay(playerCommandService, playerService, scoreService);

		eventBus.updateLoginStatusList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testEmptyCallback() {
		Class<EmptyCallback> expectedCallbackClass = EmptyCallback.class;
		playerCommandService.getLoginStatusCommandStack(eq(10), anyObject(expectedCallbackClass));
		replay(playerCommandService, playerService, scoreService);

		eventBus.updateLoginStatusList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testClearCallbacks() {
		creteAndAddCallbackToEventBus(UpdateLoginStatusListCallback.class);
		eventBus.clearCallbacks();

		Class<EmptyCallback> expectedCallbackClass = EmptyCallback.class;
		playerCommandService.getLoginStatusCommandStack(eq(10), anyObject(expectedCallbackClass));
		replay(playerCommandService, playerService, scoreService);

		eventBus.updateLoginStatusList();
	}

	private <T extends EventCallback<?>> T creteAndAddCallbackToEventBus(Class<T> callbackClass) {
		T callback = createStrictMock(callbackClass);
		eventBus.addHandler(callback);
		return callback;
	}

}
