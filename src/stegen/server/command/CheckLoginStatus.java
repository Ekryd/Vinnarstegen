package stegen.server.command;

import stegen.client.dto.*;
import stegen.server.database.*;

import com.google.appengine.api.users.*;

public class CheckLoginStatus implements PlayerCommand {
	private static final long serialVersionUID = 5466312464459679499L;
	private final transient String requestUri;
	private LoginDataDto result;

	/** Only for serialization */
	protected CheckLoginStatus() {
		requestUri = null;
	}

	public CheckLoginStatus(String requestUri) {
		this.requestUri = requestUri;
	}

	public static CheckLoginStatus createForTest(String requestUri, LoginDataDto result) {
		CheckLoginStatus checkLoginStatus = new CheckLoginStatus(requestUri);
		checkLoginStatus.result = result;
		return checkLoginStatus;
	}

	@Override
	public void execute() {
		UserService userService = UserServiceFactory.getUserService();
		if (!userService.isUserLoggedIn()) {
			result = userIsNotLoggedIn(userService);
		} else if (isUserRegistered(userService)) {
			result = userIsLoggedInAndRegistered(userService);
		} else {
			result = userIsNotRegistered(userService);
		}

	}

	private LoginDataDto userIsNotLoggedIn(UserService userService) {
		return LoginDataDto.userIsNotLoggedIn(userService.createLoginURL(requestUri));
	}

	private boolean isUserRegistered(UserService userService) {
		return PlayerRepository.get().isUserRegistered(userService.getCurrentUser().getEmail());
	}

	private LoginDataDto userIsLoggedInAndRegistered(UserService userService) {
		User currentUser = userService.getCurrentUser();
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		return LoginDataDto.userIsLoggedInAndRegistered(stegenUserRepository.createPlayerDto(currentUser.getEmail()),
				userService.createLogoutURL(requestUri));
	}

	private LoginDataDto userIsNotRegistered(UserService userService) {
		User currentUser = userService.getCurrentUser();
		return LoginDataDto.userIsNotRegistered(
				new PlayerDto(new EmailAddressDto(currentUser.getEmail()), currentUser.getEmail()),
				userService.createLogoutURL(requestUri));
	}

	@Override
	public void undo() {
		// Går inte
	}

	@Override
	public String getDescription() {
		switch (result.loginResponse) {
		case LOGGED_IN_GMAIL:
			String nickname = getBackwardCompabilityNickname(result.player);
			return nickname + " tittade till applikationen, men var inte registrerad";
		case LOGGED_IN_AND_REGISTERED:
			return "Loggade just in i applikationen";
		default:
			return "Någon tittade till applikationen, men var inte inloggad";
		}
	}

	private String getBackwardCompabilityNickname(PlayerDto player) {
		if (player == null) {
			return "Någon";
		}
		return player.nickname;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	public LoginDataDto getResult() {
		return result;
	}

	public EmailAddressDto getEmail() {
		return result.player.email;
	}
}
