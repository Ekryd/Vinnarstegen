package stegen.shared;

public class LoginDataDtoFactory {

	public static LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto loginData = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return loginData;
	}

	public static LoginDataDto createOtherLoginData() {
		EmailAddressDto email = new EmailAddressDto("address2");
		PlayerDto player = new PlayerDto(email, "nickname2");
		LoginDataDto loginData = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return loginData;
	}

	public static PlayerDto createPlayerDto(String address) {
		EmailAddressDto email = new EmailAddressDto(address);
		return new PlayerDto(email, "nickname");
	}

	public static GameResultDto createGameResult41() {
		GameResultDto result = GameResultDto.createEmptyGameResult();
		result.setScores[0].gameWinnerScore = 11;
		result.setScores[0].gameLoserScore = 5;
		result.setScores[1].gameWinnerScore = 1;
		result.setScores[1].gameLoserScore = 5;
		result.setScores[2].gameWinnerScore = 11;
		result.setScores[2].gameLoserScore = 5;
		result.setScores[3].gameWinnerScore = 1;
		result.setScores[3].gameLoserScore = 5;
		result.setScores[4].gameWinnerScore = 11;
		result.setScores[4].gameLoserScore = 5;
		return result;
	}

	public static GameResultDto createGameResult30() {
		GameResultDto result = GameResultDto.createEmptyGameResult();
		result.setScores[0].gameWinnerScore = 11;
		result.setScores[0].gameLoserScore = 5;
		result.setScores[1].gameWinnerScore = 11;
		result.setScores[1].gameLoserScore = 5;
		result.setScores[2].gameWinnerScore = 11;
		result.setScores[2].gameLoserScore = 5;
		return result;
	}

}
