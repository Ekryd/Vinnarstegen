package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class GameResultTable implements IsWidget {
	private final CellTable<GameResultsRow> baseWidget = new CellTable<GameResultsRow>();
	private final ListDataProvider<GameResultsRow> undoListDataProvider = new ListDataProvider<GameResultsRow>();
	private DateTimeFormats dateTimeFormats = new DateTimeFormatsImpl();

	public GameResultTable() {
		initColumns();
		initLayoutColumns();
		initProvider();
	}

	private void initColumns() {
		baseWidget.addColumn(new TextColumn<GameResultsRow>() {

			@Override
			public String getValue(GameResultsRow object) {
				return object.playerName;
			}
		}, "Utfört av");
		baseWidget.addColumn(new TextColumn<GameResultsRow>() {

			@Override
			public String getValue(GameResultsRow object) {
				return dateTimeFormats.formatDate(object.gameDateTime);
			}
		}, "Datum");
		baseWidget.addColumn(new TextColumn<GameResultsRow>() {

			@Override
			public String getValue(GameResultsRow object) {
				return object.result;
			}
		}, "Match");

	}

	private void initLayoutColumns() {
		baseWidget.addColumnStyleName(0, "playerColumn");
		baseWidget.addColumnStyleName(1, "dateColumn");
	}

	private void initProvider() {
		undoListDataProvider.addDataDisplay(baseWidget);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void changeList(List<GameResultsRow> result) {
		undoListDataProvider.setList(result);
	}
}
