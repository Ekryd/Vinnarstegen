package stegen.client.presenter;

import stegen.shared.*;

public class ChallengeMessage {
	private static final String SUBJECT = "Utmaning från Vinnarstegen";
	private final EmailAddressDto challengerEmail;
	private final String challengerNickname;
	private final PlayerDto challengee;
	private final String challengeDateTime;
	private String message;
	private final String insult;
	private final String alternativeInsult;

	public ChallengeMessage(EmailAddressDto challengerEmail, String challengerNickname, PlayerDto challengee, String insult,
			String alternativeInsult, String challengeDateTime) {
		this.challengerEmail = challengerEmail;
		this.challengerNickname = challengerNickname;
		this.challengee = challengee;
		this.alternativeInsult = alternativeInsult;
		this.challengeDateTime = challengeDateTime;
		this.insult = insult;
		createDefaultMessage();
	}

	private void createDefaultMessage() {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Jag, ").append(challengerNickname).append(" (").append(challengerEmail.address).append("), ");
		messageBuilder.append(" tycker att du, ").append(challengee.nickname).append(", är ").append(insult)
				.append("!\n");
		messageBuilder.append("Försvara din ära! Möt mig i pingis ").append(challengeDateTime).append(".\n");
		messageBuilder.append("Annars kommer hela världen att veta att du är ").append(alternativeInsult)
				.append("\n\n");
		messageBuilder.append("Med vänliga hälsningar\n");
		messageBuilder.append(challengerNickname);
		message = messageBuilder.toString();
	}

	public ChallengeMessageDto createDto() {
		return new ChallengeMessageDto(challengerEmail, challengee, insult, message, SUBJECT);
	}

	public String getMessage() {
		return message;
	}

	public String getSubject() {
		return SUBJECT;
	}

	public String getInsult() {
		return insult;
	}

}
