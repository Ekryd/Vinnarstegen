package stegen.server.service;

import java.io.*;

import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;
import stegen.shared.*;

import com.google.gwt.user.server.rpc.*;

/**
 * @author Björn Ekryd and Askia Linder
 */
public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {

	private static final long serialVersionUID = 1134570288972922306L;

	@Override
	public LoginDataDto getUserLoginStatus(String requestUri) {
		CheckLoginStatus command = new CheckLoginStatus(requestUri);
		command.execute();
		saveCommandForRegisteredUsers(command);
		return command.getResult();
	}

	private void saveCommandForRegisteredUsers(CheckLoginStatus command) {
		if (command.getResult().loginResponse == LoginResult.LOGGED_IN_AND_REGISTERED) {
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
	public void removePlayer(EmailAddressDto email) {
		PlayerCommand command = new RemovePlayer(email);
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
	public String changeNickname(PlayerDto player, String nickname) {
		PlayerCommand command = new ChangeNickname(player.email, nickname);
		command.execute();
		saveCommand(command, player.email);
		return getNickname(player.email);
	}

	private void saveCommand(PlayerCommand command, EmailAddressDto email) {
		CommandInstance commandInstance = new CommandInstance(command, email);
		CommandInstanceRepository.get().create(commandInstance);
	}

	@Override
	public String getNickname(EmailAddressDto address) {
		return NicknameService.get().getNickname(address);
	}

	
	@Override
	public boolean isNewUserPasswordOk(String registrationCode) {
		// TODO dont hardcode the password, fetch it from property or db
		final String NEW_USER_PASSWORD="Waldner";		
		return NEW_USER_PASSWORD.equals(registrationCode);
	}
}
