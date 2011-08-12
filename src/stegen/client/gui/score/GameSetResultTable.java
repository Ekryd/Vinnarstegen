package stegen.client.gui.score;

import java.util.*;

import stegen.shared.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.view.client.*;

public class GameSetResultTable extends CellTable<SetScoreDto> {
	private final UpdateScoreCallback scoreCallback;
	private final EmailAddressDto winnerEmail;
	private final EmailAddressDto loserEmail;
	private final GameResultDto gameResult;

	public GameSetResultTable(UpdateScoreCallback scoreCallback, EmailAddressDto winnerEmail,
			EmailAddressDto loserEmail, GameResultDto gameResult) {
		this.scoreCallback = scoreCallback;
		this.winnerEmail = winnerEmail;
		this.loserEmail = loserEmail;
		this.gameResult = gameResult;
		initComponent();
		initDataProvider();
	}

	private void initComponent() {
		Column<SetScoreDto, String> firstColumn = new Column<SetScoreDto, String>(
				new SelectionCell(getSetCountValues())) {
			@Override
			public String getValue(SetScoreDto object) {
				return convertScore(object.gameWinnerScore);
			}
		};
		firstColumn.setFieldUpdater(new FieldUpdater<SetScoreDto, String>() {
			@Override
			public void update(int index, SetScoreDto object, String value) {
				object.gameWinnerScore = convertScore(value);
				scoreCallback.onScoreChange();
			}
		});
		addColumn(firstColumn, winnerEmail.address);

		Column<SetScoreDto, String> secondColumn = new Column<SetScoreDto, String>(new SelectionCell(
				getSetCountValues())) {
			@Override
			public String getValue(SetScoreDto object) {
				return convertScore(object.gameLoserScore);
			}
		};
		secondColumn.setFieldUpdater(new FieldUpdater<SetScoreDto, String>() {
			@Override
			public void update(int index, SetScoreDto object, String value) {
				object.gameLoserScore = convertScore(value);
				scoreCallback.onScoreChange();
			}
		});
		addColumn(secondColumn, loserEmail.address);

	}

	private String convertScore(Integer i) {
		if (i == null) {
			return "";
		}
		return "" + i;
	}

	private Integer convertScore(String s) {
		if (s == null || s.trim().isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private List<String> getSetCountValues() {
		List<String> returnValue = new ArrayList<String>();
		returnValue.add("");
		for (int i = 0; i < 22; i++) {
			returnValue.add("" + i);
		}
		return returnValue;
	}

	private void initDataProvider() {
		final ListDataProvider<SetScoreDto> dataProvider = new ListDataProvider<SetScoreDto>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(this);

		List<SetScoreDto> list = dataProvider.getList();
		for (SetScoreDto setScore : gameResult.setScores) {
			list.add(setScore);
		}
	}

}
