package stegen.client.dto;

import java.io.*;

public class LoginDataDto implements Serializable {
	private static final long serialVersionUID = 1772738605652680678L;
	public String logoutUrl = "";
	public LoginResult loginResponse = LoginResult.NOT_LOGGED_IN;
	public String signInUrl = "";
	public PlayerDto player;

	/** For Serialization */
	protected LoginDataDto() {}

	public static LoginDataDto userIsLoggedInAndRegistered(PlayerDto player, String logoutUrl) {
		LoginDataDto data = new LoginDataDto();
		data.player = player;
		data.logoutUrl = logoutUrl;
		data.loginResponse = LoginResult.LOGGED_IN_AND_REGISTERED;
		return data;
	}

	public static LoginDataDto userIsNotLoggedIn(String signInUrl) {
		LoginDataDto data = new LoginDataDto();
		data.loginResponse = LoginResult.NOT_LOGGED_IN;
		data.signInUrl = signInUrl;
		data.player = new PlayerDto(new EmailAddressDto(""), "");
		return data;
	}

	public static LoginDataDto userIsNotRegistered(PlayerDto player, String logoutUrl) {
		LoginDataDto data = new LoginDataDto();
		data.player = player;
		data.logoutUrl = logoutUrl;
		data.loginResponse = LoginResult.LOGGED_IN_GMAIL;
		return data;
	}

}
