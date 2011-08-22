package stegen.shared;


public class LoginDataDtoFactory {

	public static LoginDataDto createLoginData() {
		EmailAddressDto email = new EmailAddressDto("address");
		PlayerDto player = new PlayerDto(email, "nickname");
		LoginDataDto result = LoginDataDto.userIsNotRegistered(player, "logoutUrl");
		return result;
	}

}
