package stegen.server.database;

import stegen.server.command.*;
import stegen.shared.*;

public class CommandInstanceFactory {
	private static final EmailAddressDto email = new EmailAddressDto("address");
	private static final PlayerDto player = new PlayerDto(email, "nickname");

	public static CommandInstance getUserIsNotLoggedInCommand() {
		LoginDataDto result = LoginDataDto.userIsNotLoggedIn("signInUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", result);
		EmailAddressDto email = new EmailAddressDto("address");
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		return commandInstanceToStore;
	}

	public static CommandInstance getUserIsNotRegistered() {
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", result);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		return commandInstanceToStore;
	}

	public static CommandInstance getUserIsLoggedInAndRegistered() {
		LoginDataDto result = LoginDataDto.userIsLoggedInAndRegistered(player, "logoutUrl");
		CheckLoginStatus command = CheckLoginStatus.createForTest("requestUri", result);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		return commandInstanceToStore;
	}

	public static CommandInstance clearAllScores(EmailAddressDto playerEmail) {
		PlayerCommand command = new ClearAllScores(playerEmail);
		CommandInstance commandInstanceToStore = new CommandInstance(command, playerEmail);
		return commandInstanceToStore;
	}

	public static CommandInstance registerPlayer(EmailAddressDto playerEmail) {
		PlayerCommand command = new RegisterPlayer(playerEmail);
		CommandInstance commandInstanceToStore = new CommandInstance(command, playerEmail);
		return commandInstanceToStore;
	}

	public static CommandInstance playerWonOverPlayer(EmailAddressDto winnerEmail, EmailAddressDto loserEmail,
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
		return commandInstanceToStore;
	}

	public static CommandInstance sendMessage(String message) {
		SendMessage sendMessage = new SendMessage(player.email, message);
		CommandInstance commandInstanceToStore = new CommandInstance(sendMessage, player.email);
		return commandInstanceToStore;
	}
}
