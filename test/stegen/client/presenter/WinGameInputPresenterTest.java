package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.client.presenter.WinGameInputPresenter.Display;
import stegen.shared.*;

public class WinGameInputPresenterTest {

	private Display view;
	private LoginDataDto result;
	private EventBus eventBus;
	private WinGameInputPresenter presenter;
	private PlayerDto otherPlayer = new PlayerDto(new EmailAddressDto("otherEmail"), "otherName");

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
		setupOpenDialogExpectsLostGame();

		simulateOpenDialogClickLostGame();

		setupLostGameExpects();

		simulateClickWonOrLostButton();
	}

	private void setupPresenter() {
		result = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new WinGameInputPresenter(view, result, eventBus);
	}

	private void setupInitializationExpects() {
		view.addClickOpenWinGameInputHandler(presenter.openWinGameInputhandler);
		view.addClickWinGameHandler(presenter.clickWinGameHandler);
		replay(view, eventBus);
	}

	private void setupOpenDialogExpectsWonGame() {
		reset(view, eventBus);
		view.setupWinGameInputDialog(result.player.nickname, otherPlayer.nickname);
		view.openWinGameInputDialog();
		replay(view, eventBus);
	}

	private void setupOpenDialogExpectsLostGame() {
		reset(view, eventBus);
		view.setupWinGameInputDialog(otherPlayer.nickname, result.player.nickname);
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
		GameResultDto gameResult = GameResultDto.createEmptyGameResult();
		gameResult.setScores[0] = new SetScoreDto(11, 7);
		expect(view.getGameResult()).andReturn(gameResult);
		eventBus.playerWonOverPlayer(result.player, otherPlayer, gameResult, result.player);
		replay(view, eventBus);
	}

	private void setupLostGameExpects() {
		reset(view, eventBus);
		GameResultDto gameResult = GameResultDto.createEmptyGameResult();
		gameResult.setScores[0] = new SetScoreDto(11, 7);
		expect(view.getGameResult()).andReturn(gameResult);
		eventBus.playerWonOverPlayer(otherPlayer, result.player, gameResult, result.player);
		replay(view, eventBus);
	}

	private void simulateClickWonOrLostButton() {
		presenter.clickWinGameHandler.onClick(null);
	}

}
