package stegen.client.gui.playeraction;

import java.util.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class LoginStatusTable implements IsWidget {
	private final CellTable<LoginStatusRow> baseWidget = new CellTable<LoginStatusRow>();

	private final ListDataProvider<LoginStatusRow> undoListDataProvider = new ListDataProvider<LoginStatusRow>();

	public LoginStatusTable() {
		initColumns();
		initLayoutColumns();
		initProvider();
	}

	private void initColumns() {
		baseWidget.addColumn(new TextColumn<LoginStatusRow>() {

			@Override
			public String getValue(LoginStatusRow object) {
				return object.performedBy;
			}
		}, "Utfört av");
		baseWidget.addColumn(new TextColumn<LoginStatusRow>() {

			@Override
			public String getValue(LoginStatusRow object) {
				return object.description;
			}
		}, "Händelse");

	}

	private void initLayoutColumns() {
		baseWidget.addColumnStyleName(0, "playerColumn");
	}

	private void initProvider() {
		undoListDataProvider.addDataDisplay(baseWidget);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void changeList(List<LoginStatusRow> result) {
		undoListDataProvider.setList(result);
	}

}
