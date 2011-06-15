package stegen.client.dto;

import java.io.*;

public class LoginDataDto implements Serializable {
	private static final long serialVersionUID = 1772738605652680678L;
	public String nickname = "";
	public String logoutUrl = "";
	public LoginResult loginResponse = LoginResult.NOT_LOGGED_IN;
	public String signInUrl = "";
	public EmailAddressDto emailAddress;

	public static LoginDataDto userIsLoggedInAndRegistered(EmailAddressDto emailAddress, String nickname,
			String logoutUrl) {
		LoginDataDto data = new LoginDataDto();
		data.emailAddress = emailAddress;
		data.nickname = nickname;
		data.logoutUrl = logoutUrl;
		data.loginResponse = LoginResult.LOGGED_IN_AND_REGISTERED;
		return data;
	}

	public static LoginDataDto userIsNotLoggedIn(String signInUrl) {
		LoginDataDto data = new LoginDataDto();
		data.loginResponse = LoginResult.NOT_LOGGED_IN;
		data.signInUrl = signInUrl;
		data.emailAddress = new EmailAddressDto("");
		return data;
	}

	public static LoginDataDto userIsNotRegistered(EmailAddressDto emailAddress, String nickname, String logoutUrl) {
		LoginDataDto data = new LoginDataDto();
		data.emailAddress = emailAddress;
		data.nickname = nickname;
		data.logoutUrl = logoutUrl;
		data.loginResponse = LoginResult.LOGGED_IN_GMAIL;
		return data;
	}

}
