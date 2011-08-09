package stegen.server.service;

import stegen.client.dto.*;
import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;
import stegen.shared.*;

import com.google.gwt.user.server.rpc.*;

public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {

	private static final long serialVersionUID = 1134570288972922306L;

	@Override
	public LoginDataDto userLoginStatus(String requestUri) {
		CheckLoginStatus command = new CheckLoginStatus(requestUri);
		command.execute();
		saveCommand(command);
		// saveCommandForNotRegisteredUsers(command);
		return command.getResult();
	}

	private void saveCommand(CheckLoginStatus command) {
		CommandInstance commandInstance = new CommandInstance(command, command.getEmail());
		CommandInstanceRepository.get().create(commandInstance);
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
		RegisterPlayer command = new RegisterPlayer(email);
		command.execute();
		CommandInstance commandInstance = new CommandInstance(command, email);
		CommandInstanceRepository.get().create(commandInstance);
	}

	@Override
	public void sendMessage(PlayerDto player, String message) {
		SendMessage command = new SendMessage(player.email, message);
		command.execute();
		CommandInstance commandInstance = new CommandInstance(command, player.email);
		CommandInstanceRepository.get().create(commandInstance);
	}

	@Override
	public void changeNickname(PlayerDto player, String nickname) {
		PlayerCommand command = new ChangeNickname(player, nickname);
		command.execute();
		CommandInstance commandInstance = new CommandInstance(command, player.email);
		CommandInstanceRepository.get().create(commandInstance);
	}

	@Override
	public String getNickname(EmailAddressDto player) {
		return StegenUserRepository.get().getOrCreateNickname(player);
	}
}
