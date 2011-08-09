package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.view.client.*;

public class PlayerMiscCommandTable extends CellTable<PlayerCommandDto> implements PlayerCommandListener {

	private final ListDataProvider<PlayerCommandDto> undoListDataProvider = new ListDataProvider<PlayerCommandDto>();

	public PlayerMiscCommandTable(MessageCentral messageCentral) {
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
				return object.description;
			}
		}, "Senaste händelse");

	}

	private void initProvider() {
		undoListDataProvider.addDataDisplay(this);
	}

	@Override
	public void onPlayerMiscCommandListUpdate(List<PlayerCommandDto> result) {
		changeList(result);
	}

	private void changeList(List<PlayerCommandDto> result) {
		undoListDataProvider.setList(result);
	}

	@Override
	public void onUndoCommand(UndoPlayerCommandResult result) {}

	@Override
	public void onUndoCommandUpdate(PlayerCommandDto result) {}

	@Override
	public void onGameResultListUpdate(List<PlayerCommandDto> result) {}

	@Override
	public void onLoginStatusListUpdate(List<PlayerCommandDto> result) {}

}
