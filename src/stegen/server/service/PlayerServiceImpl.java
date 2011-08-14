package stegen.server.service;

import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;
import stegen.shared.*;

import com.google.gwt.user.server.rpc.*;

public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {

	private static final long serialVersionUID = 1134570288972922306L;

	@Override
	public LoginDataDto getUserLoginStatus(String requestUri) {
		CheckLoginStatus command = new CheckLoginStatus(requestUri);
		command.execute();
		saveCommand(command, command.getEmail());
		// saveCommandForNotRegisteredUsers(command);
		return command.getResult();
	}

	@SuppressWarnings("unused")
	private void saveCommandForNotRegisteredUsers(CheckLoginStatus command) {
		if (command.getResult().loginResponse != LoginResult.LOGGED_IN_AND_REGISTERED) {
			CommandInstance commandInstance = new CommandInstance(command, command.getEmail());
			CommandInstanceRepository.get().create(commandInstance);
		}
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		PlayerCommand command = new RegisterPlayer(email);
		command.execute();
		saveCommand(command, email);
	}

	@Override
	public void sendMessage(PlayerDto player, String message) {
		PlayerCommand command = new SendMessage(player.email, message);
		command.execute();
		saveCommand(command, player.email);
	}

	@Override
	public PlayerDto changeNickname(PlayerDto player, String nickname) {
		PlayerCommand command = new ChangeNickname(player, nickname);
		command.execute();
		saveCommand(command, player.email);
		return updatePlayerNickname(player, nickname);
	}

	private PlayerDto updatePlayerNickname(PlayerDto player, String nickname) {
		player.nickname = nickname;
		return player;
	}

	private void saveCommand(PlayerCommand command, EmailAddressDto email) {
		CommandInstance commandInstance = new CommandInstance(command, email);
		CommandInstanceRepository.get().create(commandInstance);
	}

	@Override
	public String getNickname(EmailAddressDto player) {
		return StegenUserRepository.get().getOrCreateNickname(player);
	}
}
