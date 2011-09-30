package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.PlayerMiscCommandsPresenter.Display;

public class PlayerMiscCommandsView implements Display {

	private final PlayerMiscCommandTable table;

	public PlayerMiscCommandsView(PlayerMiscCommandTable table) {
		this.table = table;
	}

	@Override
	public void changePlayerMiscCommandList(List<PlayerMiscCommandRow> content) {
		table.changeList(content);
	}
}
