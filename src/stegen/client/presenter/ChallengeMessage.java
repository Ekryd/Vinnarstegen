package stegen.client.presenter;

import stegen.shared.*;

public class ChallengeMessage {
	private static final String SUBJECT = "Utmaning från Vinnarstegen";
	private final EmailAddressDto challengerEmail;
	private final String challengerNickname;
	private final PlayerDto challengee;
	private final String challengeDateTime;
	private String message;
	public ChallengeMessage(EmailAddressDto challengerEmail, String challengerNickname, PlayerDto challengee, String challengeDateTime) {
		this.challengerEmail = challengerEmail;
		this.challengerNickname = challengerNickname;
		this.challengee = challengee;
		this.challengeDateTime = challengeDateTime;
		createDefaultMessage();
	}

	private void createDefaultMessage() {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Jag, ").append(challengerNickname).append(" (").append(challengerEmail.address).append("), ");
		messageBuilder.append(" vill utmana dig, ").append(challengee.nickname).append("!\n");
		messageBuilder.append("Försvara din ära! Möt mig i pingis ").append(challengeDateTime).append(".\n\n");
		messageBuilder.append("Med vänliga hälsningar\n");
		messageBuilder.append(challengerNickname);
		message = messageBuilder.toString();
	}

	public ChallengeMessageDto createDto() {
		return new ChallengeMessageDto(challengerEmail, challengee.email,  message, SUBJECT);
	}

	public String getMessage() {
		return message;
	}

	public String getSubject() {
		return SUBJECT;
	}

	public void setMessage(String changedMessageBody) {
		message = changedMessageBody;
	}

}
