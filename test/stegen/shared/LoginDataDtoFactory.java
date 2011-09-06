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

}
