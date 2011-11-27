package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class PlayerMiscCommandTable implements IsWidget {
	private final CellTable<PlayerMiscCommandRow> baseWidget = new CellTable<PlayerMiscCommandRow>();
	private final ListDataProvider<PlayerMiscCommandRow> undoListDataProvider = new ListDataProvider<PlayerMiscCommandRow>();
	private final DateTimeFormats dateTimeFormats = new DateTimeFormatsImpl();

	public PlayerMiscCommandTable() {
		initColumns();
		initLayoutColumns();
		initProvider();
	}

	private void initColumns() {
		baseWidget.addColumn(new TextColumn<PlayerMiscCommandRow>() {

			@Override
			public String getValue(PlayerMiscCommandRow object) {
				return object.performedBy;
			}
		}, "Utfört av");
		baseWidget.addColumn(new TextColumn<PlayerMiscCommandRow>() {

			@Override
			public String getValue(PlayerMiscCommandRow object) {
				return dateTimeFormats.formatDate(object.performedDate);
			}
		}, "Datum");
		baseWidget.addColumn(new TextColumn<PlayerMiscCommandRow>() {

			@Override
			public String getValue(PlayerMiscCommandRow object) {
				return object.description;
			}
		}, "Senaste händelse");

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

	public void changeList(List<PlayerMiscCommandRow> result) {
		undoListDataProvider.setList(result);
	}
}
