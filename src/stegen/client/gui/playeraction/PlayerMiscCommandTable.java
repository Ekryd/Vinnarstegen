package stegen.client.gui.playeraction;

import java.util.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class PlayerMiscCommandTable implements IsWidget {
	private final CellTable<PlayerMiscCommandRow> baseWidget = new CellTable<PlayerMiscCommandRow>();
	private final ListDataProvider<PlayerMiscCommandRow> undoListDataProvider = new ListDataProvider<PlayerMiscCommandRow>();

	public PlayerMiscCommandTable() {
		init();
		initProvider();
	}

	private void init() {
		baseWidget.addColumn(new TextColumn<PlayerMiscCommandRow>() {

			@Override
			public String getValue(PlayerMiscCommandRow object) {
				return object.performedBy;
			}
		}, "Utfört av");
		baseWidget.addColumn(new TextColumn<PlayerMiscCommandRow>() {

			@Override
			public String getValue(PlayerMiscCommandRow object) {
				return object.description;
			}
		}, "Senaste händelse");

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
