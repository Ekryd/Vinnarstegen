package stegen.client.gui.gameresult;

import stegen.client.gui.gameresult.WinGameFieldUpdater.ButtonType;
import stegen.client.gui.score.*;
import stegen.client.presenter.WinGameInputPresenter.Display;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;

public class WinGameInputView implements Display {

	private final Column<ScoreTableRow, String> winnerButtonColumn;
	private final Column<ScoreTableRow, String> loserButtonColumn;
	private final WinGameDialogBox winGameDialog;

	public WinGameInputView(Column<ScoreTableRow, String> winnerButtonColumn,
			Column<ScoreTableRow, String> loserButtonColumn, WinGameDialogBox winGameDialog) {
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
	public SetResult getGameResult() {
		return winGameDialog.getSetResult();
	}

}
