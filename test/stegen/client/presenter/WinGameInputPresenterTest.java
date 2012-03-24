package stegen.client.presenter;


import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.client.presenter.WinGameInputPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class WinGameInputPresenterTest {

	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	@Mock
	private com.google.gwt.event.shared.EventBus gwtEventBus;
	@Mock
	ScoreServiceAsync scoreService;
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
		presenter.go();

		setupInitializationExpects();
	}

	@Test
	public void testOpenInputDialogWonGame() {
		setupPresenter();
		presenter.go();

		simulateOpenDialogClickWonGame();
		
		setupOpenDialogExpectsWonGame();

		
	}

	@Test
	public void testOpenInputDialogLostGame() {
		setupPresenter();
		presenter.go();

		presenter.changeNicknameEventHandler.handleEvent(new ChangeNicknameEvent("nick2"));

		simulateOpenDialogClickLostGame();
		
		setupOpenDialogExpectsLostGame();

		
	}

	@Test
	public void testWonGame() {
		setupPresenter();
		presenter.go();
		
		SetResult setResult = SetResult.TRE_TVA;
		when(view.getGameResult()).thenReturn(setResult);
		
		simulateOpenDialogClickWonGame();
		
		setupOpenDialogExpectsWonGame();
		
		simulateClickWonOrLostButton();
		
		setupWonGameExpects();
	}

	@Test
	public void testLostGame() {
		setupPresenter();
		presenter.go();

		presenter.changeNicknameEventHandler.handleEvent(new ChangeNicknameEvent("nick2"));

		SetResult setResult = SetResult.TRE_TVA;
		when(view.getGameResult()).thenReturn(setResult);
		
		simulateOpenDialogClickLostGame();
		
		setupOpenDialogExpectsLostGame();

		simulateClickWonOrLostButton();

		setupLostGameExpects();

		
	}

	private void setupPresenter() {
		presenter = new WinGameInputPresenter(view, loginData, gwtEventBus,scoreService);
	}

	private void setupInitializationExpects() {
		verify(view).addClickOpenWinGameInputHandler(presenter.openWinGameInputhandler);
		verify(view).addClickWinGameHandler(presenter.clickWinGameHandler);
		verify(gwtEventBus).addHandler(ChangeNicknameEvent.TYPE, presenter.changeNicknameEventHandler);
	}

	private void setupOpenDialogExpectsWonGame() {
		verify(view).setupWinGameInputDialog(loginData.player.nickname, otherPlayer.nickname);
		verify(view).openWinGameInputDialog();		
	}

	private void setupOpenDialogExpectsLostGame() {
		verify(view).setupWinGameInputDialog(otherPlayer.nickname, "nick2");
		verify(view).openWinGameInputDialog();
	}

	private void simulateOpenDialogClickWonGame() {
		presenter.openWinGameInputhandler.onButtonClick(createRow(), ButtonType.WIN);
	}

	private void simulateOpenDialogClickLostGame() {
		presenter.openWinGameInputhandler.onButtonClick(createRow(), ButtonType.LOSE);
	}

	private ScoreTableRow createRow() {
		return new ScoreTableRow(otherPlayer, null, null, null, null, false, true);
	}

	private void setupWonGameExpects() {
		verify(scoreService).playerWonOverPlayer(eq(loginData.player), eq(otherPlayer), any(GameResultDto.class),eq(loginData.player), eq(presenter.playerWonOverPlayerCallback));
	}

	private void setupLostGameExpects() {
	
		scoreService.playerWonOverPlayer(eq(otherPlayer), eq(loginData.player), any(GameResultDto.class),
				eq(loginData.player), eq(presenter.playerWonOverPlayerCallback));
	}

	private void simulateClickWonOrLostButton() {
		presenter.clickWinGameHandler.onClick(null);
	}

}
