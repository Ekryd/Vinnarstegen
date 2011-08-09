package stegen.shared;

import java.io.*;

public class PlayerDto implements Serializable {
	private static final long serialVersionUID = 1378207965709745502L;
	public EmailAddressDto email;
	public String nickname;

	/** For Serialization */
	protected PlayerDto() {}

	public PlayerDto(EmailAddressDto email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

}
