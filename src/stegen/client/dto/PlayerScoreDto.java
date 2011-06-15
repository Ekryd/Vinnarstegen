package stegen.client.dto;

import java.io.*;

public class PlayerScoreDto implements Serializable {
	private static final long serialVersionUID = -4143759459619965619L;

	public EmailAddressDto email;
	public int score;
	public int ranking;
	public EmailAddressDto changedBy;
	public String changedDateTime;

	protected PlayerScoreDto() {}

	public PlayerScoreDto(EmailAddressDto email, int score, EmailAddressDto changedBy, String changedDateTime) {
		this.email = email;
		this.score = score;
		this.changedBy = changedBy;
		this.changedDateTime = changedDateTime;
	}

}
