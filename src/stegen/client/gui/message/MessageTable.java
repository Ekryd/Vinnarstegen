package stegen.client.gui.message;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.i18n.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.view.client.*;

public class MessageTable extends CellTable<PlayerCommandDto> implements MessageListener {

	private final ListDataProvider<PlayerCommandDto> undoListDataProvider = new ListDataProvider<PlayerCommandDto>();
	private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");

	public MessageTable(MessageCentral messageCentral) {
		init();
		initProvider();
		messageCentral.listeners.addListener(this);
	}

	private void init() {
		addColumn(new TextColumn<PlayerCommandDto>() {

			@Override
			public String getValue(PlayerCommandDto object) {
				return object.player.nickname;
			}
		}, "Utfört av");
		addColumn(new TextColumn<PlayerCommandDto>() {

			@Override
			public String getValue(PlayerCommandDto object) {
				return dateTimeFormat.format(object.performedDateTime);
			}
		}, "Datum");
		addColumn(new TextColumn<PlayerCommandDto>() {

			@Override
			public String getValue(PlayerCommandDto object) {
				return object.description;
			}
		}, "Meddelande");

	}

	private void initProvider() {
		undoListDataProvider.addDataDisplay(this);
	}

	private void changeList(List<PlayerCommandDto> result) {
		undoListDataProvider.setList(result);
	}

	@Override
	public void onMessageListUpdate(List<PlayerCommandDto> messages) {
		changeList(messages);
	}

}
