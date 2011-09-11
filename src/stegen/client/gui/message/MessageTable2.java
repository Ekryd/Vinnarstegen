package stegen.client.gui.message;

import java.util.*;

import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class MessageTable2 implements IsWidget {
	private final CellTable<MessageTableRow> baseWidget = new CellTable<MessageTableRow>();
	private final ListDataProvider<MessageTableRow> dataProvider = new ListDataProvider<MessageTableRow>();
	private final DateTimeFormats dateTimeFormats = new DateTimeFormatsImpl();

	public MessageTable2() {
		init();
		initProvider();
	}

	private void init() {
		baseWidget.addColumn(new TextColumn<MessageTableRow>() {

			@Override
			public String getValue(MessageTableRow object) {
				return object.playerName;
			}
		}, "Utfört av");
		baseWidget.addColumn(new TextColumn<MessageTableRow>() {

			@Override
			public String getValue(MessageTableRow object) {
				return dateTimeFormats.formatDate(object.messageDate);
			}
		}, "Datum");
		baseWidget.addColumn(new TextColumn<MessageTableRow>() {

			@Override
			public String getValue(MessageTableRow object) {
				return object.message;
			}
		}, "Meddelande");

	}

	private void initProvider() {
		dataProvider.addDataDisplay(baseWidget);
	}

	public void changeContent(List<MessageTableRow> messageTableContent) {
		dataProvider.setList(messageTableContent);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

}
