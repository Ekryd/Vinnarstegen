package stegen.client.gui.gameresult;

import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.client.presenter.WinGameInputPresenter.Display;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;

public class WinGameInputView implements Display {

	private final Column<ScoreTableRow, String> winnerButtonColumn;
	private final Column<ScoreTableRow, String> loserButtonColumn;
	private final WinGameDialogBox2 winGameDialog;

	public WinGameInputView(Column<ScoreTableRow, String> winnerButtonColumn,
			Column<ScoreTableRow, String> loserButtonColumn, WinGameDialogBox2 winGameDialog) {
		this.winnerButtonColumn = winnerButtonColumn;
		this.loserButtonColumn = loserButtonColumn;
		this.winGameDialog = winGameDialog;
	}

	@Override
	public void addClickOpenWinGameInputHandler(WinGameFieldUpdater fieldUpdater) {
		winnerButtonColumn.setFieldUpdater(fieldUpdater.createFieldUpdater(ButtonType.WIN));
		loserButtonColumn.setFieldUpdater(fieldUpdater.createFieldUpdater(ButtonType.LOSE));
	}

	@Override
	public void addClickWinGameHandler(ClickHandler clickHandler) {
		winGameDialog.addClickWinGameHandler(clickHandler);
	}

	@Override
	public void setupWinGameInputDialog(String winnerName, String loserName) {
		winGameDialog.setupWinGameInputDialog(winnerName, loserName);
	}

	@Override
	public void openWinGameInputDialog() {
		winGameDialog.show();
	}

	@Override
	public GameResultDto getGameResult() {
		return convert(winGameDialog.getSetResult());
	}

	private GameResultDto convert(SetResult setResult) {
		GameResultDto returnValue = GameResultDto.createEmptyGameResult();
		switch (setResult) {
		case TRE_NOLL:
			returnValue.setScores[0] = new SetScoreDto(11, 1);
			returnValue.setScores[1] = new SetScoreDto(11, 1);
			returnValue.setScores[2] = new SetScoreDto(11, 1);
			return returnValue;
		case TRE_ETT:
			returnValue.setScores[0] = new SetScoreDto(1, 11);
			returnValue.setScores[1] = new SetScoreDto(11, 1);
			returnValue.setScores[2] = new SetScoreDto(11, 1);
			returnValue.setScores[3] = new SetScoreDto(11, 1);
			return returnValue;
		case TRE_TVA:
			returnValue.setScores[0] = new SetScoreDto(1, 11);
			returnValue.setScores[1] = new SetScoreDto(1, 11);
			returnValue.setScores[2] = new SetScoreDto(11, 1);
			returnValue.setScores[3] = new SetScoreDto(11, 1);
			returnValue.setScores[4] = new SetScoreDto(11, 1);
			return returnValue;
		}
		return returnValue;
	}
}
