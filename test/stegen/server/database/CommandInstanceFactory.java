package stegen.server.database;

import stegen.server.command.*;
import stegen.shared.*;

public class CommandInstanceFactory {
	private static final EmailAddressDto email = new EmailAddressDto("address");
	private static final PlayerDto player = new PlayerDto(email, "nickname");
	private final PlayerRepository playerRepository = PlayerRepository.get();
	private final CommandInstanceRepository commandInstanceRepository = CommandInstanceRepository.get();

	public CommandInstanceFactory() {

	}

	public Player createPlayer() {
		Player player = Player.createPlayer(email);
		playerRepository.create(player);
		return player;
	}

	public CommandInstance addUserIsNotLoggedInCommand() {
		LoginDataDto loginData = LoginDataDto.userIsNotLoggedIn("signInUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", loginData);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addUserIsNotRegistered() {
		LoginDataDto loginData = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", loginData);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addUserIsLoggedInAndRegistered() {
		LoginDataDto loginData = LoginDataDto.userIsLoggedInAndRegistered(player, "logoutUrl");
		CheckLoginStatus command = CheckLoginStatus.createForTest("requestUri", loginData);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addClearAllScores() {
		PlayerCommand command = new ClearAllScores(email);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addClearAllScores(EmailAddressDto emailAddress) {
		PlayerCommand command = new ClearAllScores(emailAddress);
		CommandInstance commandInstanceToStore = new CommandInstance(command, emailAddress);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addRegisterPlayer() {
		PlayerCommand command = new RegisterPlayer(email);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addRegisterPlayer(EmailAddressDto emailAddress) {
		PlayerCommand command = new RegisterPlayer(emailAddress);
		CommandInstance commandInstanceToStore = new CommandInstance(command, emailAddress);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addPlayerWonOverPlayer(EmailAddressDto winnerEmail, EmailAddressDto loserEmail,
			EmailAddressDto changedBy) {
		GameResultDto result = GameResultDto.createEmptyGameResult();
		result.setScores[0].gameWinnerScore = 11;
		result.setScores[0].gameLoserScore = 0;
		result.setScores[1].gameWinnerScore = 11;
		result.setScores[1].gameLoserScore = 5;
		result.setScores[2].gameWinnerScore = 5;
		result.setScores[2].gameLoserScore = 11;
		result.setScores[3].gameWinnerScore = 11;
		result.setScores[3].gameLoserScore = 5;
		PlayerCommand command = new PlayerWonOverPlayer(winnerEmail, loserEmail, result, changedBy);
		CommandInstance commandInstanceToStore = new CommandInstance(command, changedBy);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addSendMessage(String message) {
		SendMessage sendMessage = new SendMessage(player.email, message);
		CommandInstance commandInstanceToStore = new CommandInstance(sendMessage, player.email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}

	public CommandInstance addChangeNickname() {
		ChangeNickname changeNickname = new ChangeNickname(email, "nickname");
		CommandInstance commandInstanceToStore = new CommandInstance(changeNickname, player.email);
		commandInstanceRepository.create(commandInstanceToStore);
		return commandInstanceToStore;
	}
}
