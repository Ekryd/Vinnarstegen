package stegen.server.database;

import stegen.client.dto.*;
import stegen.server.command.*;

public class CommandInstanceFactory {
	public static CommandInstance getUserIsNotLoggedInCommand() {
		LoginDataDto result = LoginDataDto.userIsNotLoggedIn("signInUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", result);
		EmailAddressDto email = new EmailAddressDto("address");
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		return commandInstanceToStore;
	}

	public static CommandInstance getUserIsNotRegistered() {
		EmailAddressDto email = new EmailAddressDto("address");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(email, "nick", "logoutUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", result);
		CommandInstance commandInstanceToStore = new CommandInstance(command, email);
		return commandInstanceToStore;
	}

	public static CommandInstance getUserIsLoggedInAndRegistered() {
		EmailAddressDto email = new EmailAddressDto("address");
		LoginDataDto result = LoginDataDto.userIsLoggedInAndRegistered(email, "nick", "logoutUrl");
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
		result.setScores[1].gameWinnerScore = 5;
		PlayerCommand command = new PlayerWonOverPlayer(winnerEmail, loserEmail, result, changedBy);
		CommandInstance commandInstanceToStore = new CommandInstance(command, changedBy);
		return commandInstanceToStore;
	}
}
