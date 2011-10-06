package stegen.client.presenter;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.client.presenter.WinGameInputPresenter.Display;
import stegen.shared.*;

public class WinGameInputPresenterTest {

	private Display view;
	private LoginDataDto loginData;
	private EventBus eventBus;
	private WinGameInputPresenter presenter;
	private PlayerDto otherPlayer = new PlayerDto(new EmailAddressDto("otherEmail"), "otherName");

	@Test
	public void testConverterZeroZero() {
		GameResultDto gameResult = WinGameInputPresenter.convert(SetResult.NOLL_NOLL);
		assertEquals(5, gameResult.setScores.length);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[0]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[1]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[2]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[3]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[4]);
	}

	@Test
	public void testConverterThreeZero() {
		GameResultDto gameResult = WinGameInputPresenter.convert(SetResult.TRE_NOLL);
		assertEquals(5, gameResult.setScores.length);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[0]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[1]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[2]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[3]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[4]);
	}

	@Test
	public void testConverterThreeOne() {
		GameResultDto gameResult = WinGameInputPresenter.convert(SetResult.TRE_ETT);
		assertEquals(5, gameResult.setScores.length);
		assertSetWinnerAndLoserScoreEquals(1, 11, gameResult.setScores[0]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[1]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[2]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[3]);
		assertSetWinnerAndLoserScoreEquals(0, 0, gameResult.setScores[4]);
	}

	@Test
	public void testConverterThreeTwo() {
		GameResultDto gameResult = WinGameInputPresenter.convert(SetResult.TRE_TVA);
		assertEquals(5, gameResult.setScores.length);
		assertSetWinnerAndLoserScoreEquals(1, 11, gameResult.setScores[0]);
		assertSetWinnerAndLoserScoreEquals(1, 11, gameResult.setScores[1]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[2]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[3]);
		assertSetWinnerAndLoserScoreEquals(11, 1, gameResult.setScores[4]);
	}

	private void assertSetWinnerAndLoserScoreEquals(int winnerScore, int loserScore, SetScoreDto setScoreDto) {
		assertEquals(winnerScore, (int) setScoreDto.gameWinnerScore);
		assertEquals(loserScore, (int) setScoreDto.gameLoserScore);
	}

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testOpenInputDialogWonGame() {
		setupPresenter();
		presenter.go();

		setupOpenDialogExpectsWonGame();

		simulateOpenDialogClickWonGame();
	}

	@Test
	public void testOpenInputDialogLostGame() {
		setupPresenter();
		presenter.go();

		presenter.eventCommandChangeNicknameCallback.onSuccess("nick2");

		setupOpenDialogExpectsLostGame();

		simulateOpenDialogClickLostGame();
	}

	@Test
	public void testWonGame() {
		setupPresenter();
		presenter.go();
		setupOpenDialogExpectsWonGame();

		simulateOpenDialogClickWonGame();

		setupWonGameExpects();

		simulateClickWonOrLostButton();
	}

	@Test
	public void testLostGame() {
		setupPresenter();
		presenter.go();

		presenter.eventCommandChangeNicknameCallback.onSuccess("nick2");

		setupOpenDialogExpectsLostGame();

		simulateOpenDialogClickLostGame();

		setupLostGameExpects();

		simulateClickWonOrLostButton();
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new WinGameInputPresenter(view, loginData, eventBus);
	}

	private void setupInitializationExpects() {
		view.addClickOpenWinGameInputHandler(presenter.openWinGameInputhandler);
		view.addClickWinGameHandler(presenter.clickWinGameHandler);
		eventBus.addHandler(presenter.eventCommandChangeNicknameCallback);
		replay(view, eventBus);
	}

	private void setupOpenDialogExpectsWonGame() {
		reset(view, eventBus);
		view.setupWinGameInputDialog(loginData.player.nickname, otherPlayer.nickname);
		view.openWinGameInputDialog();
		replay(view, eventBus);
	}

	private void setupOpenDialogExpectsLostGame() {
		reset(view, eventBus);
		view.setupWinGameInputDialog(otherPlayer.nickname, "nick2");
		view.openWinGameInputDialog();
		replay(view, eventBus);
	}

	private void simulateOpenDialogClickWonGame() {
		presenter.openWinGameInputhandler.onButtonClick(createRow(), ButtonType.WIN);
	}

	private void simulateOpenDialogClickLostGame() {
		presenter.openWinGameInputhandler.onButtonClick(createRow(), ButtonType.LOSE);
	}

	private ScoreTableRow createRow() {
		return new ScoreTableRow(otherPlayer, null, null, null, null, false);
	}

	private void setupWonGameExpects() {
		reset(view, eventBus);
		SetResult setResult = SetResult.TRE_TVA;
		expect(view.getGameResult()).andReturn(setResult);
		eventBus.playerWonOverPlayer(eq(loginData.player), eq(otherPlayer), anyObject(GameResultDto.class),
				eq(loginData.player));
		replay(view, eventBus);
	}

	private void setupLostGameExpects() {
		reset(view, eventBus);
		SetResult setResult = SetResult.TRE_TVA;
		expect(view.getGameResult()).andReturn(setResult);
		eventBus.playerWonOverPlayer(eq(otherPlayer), eq(loginData.player), anyObject(GameResultDto.class),
				eq(loginData.player));
		replay(view, eventBus);
	}

	private void simulateClickWonOrLostButton() {
		presenter.clickWinGameHandler.onClick(null);
	}

}
