package stegen.client.gui.message;

import java.util.*;

import com.google.gwt.i18n.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class MessageTable2 implements IsWidget {
	public interface MessageTableContent {
		String getPlayerName();

		Date getMessageDate();

		String getMessage();
	}

	private final CellTable<MessageTableContent> baseWidget = new CellTable<MessageTableContent>();
	private final ListDataProvider<MessageTableContent> dataProvider = new ListDataProvider<MessageTableContent>();
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");

	public MessageTable2() {
		init();
		initProvider();
	}

	private void init() {
		baseWidget.addColumn(new TextColumn<MessageTableContent>() {

			@Override
			public String getValue(MessageTableContent object) {
				return object.getPlayerName();
			}
		}, "Utfört av");
		baseWidget.addColumn(new TextColumn<MessageTableContent>() {

			@Override
			public String getValue(MessageTableContent object) {
				return dateTimeFormat.format(object.getMessageDate());
			}
		}, "Datum");
		baseWidget.addColumn(new TextColumn<MessageTableContent>() {

			@Override
			public String getValue(MessageTableContent object) {
				return object.getMessage();
			}
		}, "Meddelande");

	}

	private void initProvider() {
		dataProvider.addDataDisplay(baseWidget);
	}

	public void changeContent(List<MessageTableContent> messageTableContent) {
		dataProvider.setList(messageTableContent);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

}
