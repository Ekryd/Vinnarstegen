package stegen.server.service;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;

import com.google.gwt.user.server.rpc.*;

public class UndoServiceImpl extends RemoteServiceServlet implements UndoService {
	private static final long serialVersionUID = 5777230422402242088L;

	@Override
	public List<PlayerCommandDto> getPlayerCommandStack(int maxDepth) {
		List<CommandInstance> commands = CommandInstanceRepository.get().getPlayerCommandStack(maxDepth);
		return convertList(commands, maxDepth);
	}

	@Override
	public PlayerCommandDto getUndoCommand() {
		CommandInstance latestUndoable = CommandInstanceRepository.get().getLatestUndoable();
		return latestUndoable.getPlayerUndoCommand();
	}

	private List<PlayerCommandDto> convertList(List<CommandInstance> commands, int maxDepth) {
		List<PlayerCommandDto> returnValue = new ArrayList<PlayerCommandDto>();
		int index = 0;
		for (CommandInstance commandInstance : commands) {
			index++;
			if (index > maxDepth) {
				return returnValue;
			}
			returnValue.add(commandInstance.getPlayerUndoCommand());
		}
		return returnValue;
	}

	@Override
	public UndoPlayerCommandResult undoPlayerCommand(EmailAddressDto player) {
		UndoPlayerCommand command = new UndoPlayerCommand(player);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, player);
		CommandInstanceRepository.get().create(commandInstanceToStore);
		return command.getResult();
	}
}
