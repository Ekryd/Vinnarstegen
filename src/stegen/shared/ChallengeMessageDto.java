package stegen.shared;

import java.io.*;

public class ChallengeMessageDto implements Serializable {
	private static final long serialVersionUID = 2612413882389891300L;

	public EmailAddressDto challengerEmail;
	public EmailAddressDto challengeeEmail;
	public String insult;
	public String body;
	public String subject;

	/** For Serialization */
	protected ChallengeMessageDto() {}

	public ChallengeMessageDto(EmailAddressDto challengerEmail, EmailAddressDto challengeeEmail, String insult,
			String body, String subject) {
		this.challengerEmail = challengerEmail;
		this.challengeeEmail = challengeeEmail;
		this.insult = insult;
		this.body = body;
		this.subject = subject;
	}

}
