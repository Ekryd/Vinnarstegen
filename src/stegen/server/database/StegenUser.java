package stegen.server.database;

import javax.jdo.annotations.*;

import stegen.shared.*;

@PersistenceCapable
public class StegenUser {
	@PrimaryKey
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String emailString;

	@Persistent()
	private String nickname;

	private StegenUser(String emailString, String nickname) {
		this.emailString = emailString;
		this.nickname = nickname;
	}

	public static StegenUser createUser(String emailString, String nickname) {
		return new StegenUser(emailString, nickname);
	}

	public static StegenUser createUserWithDefaults(EmailAddressDto email) {
		return new StegenUser(email.address, email.address);
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmailString() {
		return emailString;
	}

}
