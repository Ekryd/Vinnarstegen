package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.PlayerMiscCommandsPresenter.Display;

public class PlayerMiscCommandsView implements Display {

	private final PlayerMiscCommandTable2 table;

	public PlayerMiscCommandsView(PlayerMiscCommandTable2 table) {
		this.table = table;
	}

	@Override
	public void changePlayerMiscCommandList(List<PlayerMiscCommandRow> content) {
		table.changeList(content);
	}
}
