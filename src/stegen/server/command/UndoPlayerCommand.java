package stegen.server.command;

import stegen.server.database.*;
import stegen.shared.*;

public class UndoPlayerCommand implements PlayerCommand {
	private static final long serialVersionUID = -2335595487010073352L;
	private final EmailAddressDto player;
	private UndoPlayerCommandResult result = UndoPlayerCommandResult.FAILURE;
	private String commandUndoDescription = "";

	/** Only for serialization */
	protected UndoPlayerCommand() {
		this.player = null;
	}

	public UndoPlayerCommand(EmailAddressDto player) {
		this.player = player;
	}

	@Override
	public void execute() {
		CommandInstance commandToUndo = CommandInstanceRepository.get().getLatestUndoable();
		if (commandToUndo == null) {
			result = UndoPlayerCommandResult.FAILURE;
		} else if (!commandToUndo.belongsTo(player)) {
			commandUndoDescription = " för " + commandToUndo.createPlayerCommandDto().description;
			result = UndoPlayerCommandResult.FAILURE;
		} else {
			commandUndoDescription = commandToUndo.createPlayerCommandDto().description;
			commandToUndo.undo();
			CommandInstanceRepository.get().delete(commandToUndo);
			result = UndoPlayerCommandResult.SUCCESS;
		}
	}

	@Override
	public void undo() {
		// No undo for the undo-command
	}

	@Override
	public String getDescription() {
		if (result == UndoPlayerCommandResult.SUCCESS) {
			return "Ångrade " + commandUndoDescription;
		} else {
			return "Ångring misslyckades" + commandUndoDescription;
		}
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	public UndoPlayerCommandResult getResult() {
		return result;
	}
}
