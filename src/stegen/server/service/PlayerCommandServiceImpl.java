package stegen.server.service;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;
import stegen.shared.*;

import com.google.gwt.user.server.rpc.*;

public class PlayerCommandServiceImpl extends RemoteServiceServlet implements PlayerCommandService {
	private static final long serialVersionUID = 5777230422402242088L;

	@Override
	public List<PlayerCommandDto> getPlayerMiscCommandStack(int maxDepth) {
		@SuppressWarnings("unchecked")
		List<CommandInstance> commands = CommandInstanceRepository.get().getPlayerCommandStack(maxDepth,
				ChangeNickname.class, RegisterPlayer.class, UndoPlayerCommand.class, Challenge.class);
		return convertList(commands, maxDepth);
	}

	@Override
	public List<PlayerCommandDto> getSendMessageCommandStack(int maxDepth) {
		@SuppressWarnings("unchecked")
		List<CommandInstance> commands = CommandInstanceRepository.get().getPlayerCommandStack(maxDepth,
				SendMessage.class);
		return convertList(commands, maxDepth);
	}

	@Override
	public List<PlayerCommandDto> getGameResultCommandStack(int maxDepth) {
		@SuppressWarnings("unchecked")
		List<CommandInstance> commands = CommandInstanceRepository.get().getPlayerCommandStack(maxDepth,
				ClearAllScores.class, PlayerWonOverPlayer.class);
		return convertList(commands, maxDepth);
	}

	@Override
	public List<PlayerCommandDto> getLoginStatusCommandStack(int maxDepth) {
		@SuppressWarnings("unchecked")
		List<CommandInstance> commands = CommandInstanceRepository.get().getPlayerCommandStack(maxDepth,
				CheckLoginStatus.class);
		return convertList(commands, maxDepth);
	}

	@Override
	public PlayerCommandDto getUndoCommand() {
		CommandInstance latestUndoable = CommandInstanceRepository.get().getLatestUndoable();
		return latestUndoable.createPlayerCommandDto();
	}

	private List<PlayerCommandDto> convertList(List<CommandInstance> commands, int maxDepth) {
		List<PlayerCommandDto> returnValue = new ArrayList<PlayerCommandDto>();
		int index = 0;
		for (CommandInstance commandInstance : commands) {
			index++;
			if (index > maxDepth) {
				return returnValue;
			}
			returnValue.add(commandInstance.createPlayerCommandDto());
		}
		return returnValue;
	}

	@Override
	public UndoPlayerCommandResult undoPlayerCommand(PlayerDto player) {
		UndoPlayerCommand command = new UndoPlayerCommand(player.email);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, player.email);
		CommandInstanceRepository.get().create(commandInstanceToStore);
		return command.getResult();
	}
}
