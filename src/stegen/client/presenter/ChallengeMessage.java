package stegen.client.presenter;

import stegen.shared.*;

public class ChallengeMessage {
	private static final String SUBJECT = "Utmaning från Vinnarstegen";
	private final PlayerDto challenger;
	private final PlayerDto challengee;
	private final String challengeDateTime;
	private String message;
	private final String insult;
	private final String alternativeInsult;

	public ChallengeMessage(PlayerDto challenger, PlayerDto challengee, String insult, String alternativeInsult,
			String challengeDateTime) {
		this.challenger = challenger;
		this.challengee = challengee;
		this.alternativeInsult = alternativeInsult;
		this.challengeDateTime = challengeDateTime;
		this.insult = insult;
		createDefaultMessage();
	}

	private void createDefaultMessage() {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Jag, ").append(challenger.nickname).append(" (").append(challenger.email.address)
				.append("), ");
		messageBuilder.append(" tycker att du, ").append(challengee.nickname).append(", är ").append(insult)
				.append("!\n");
		messageBuilder.append("Försvara din ära! Möt mig i pingis ").append(challengeDateTime).append(".\n");
		messageBuilder.append("Annars kommer hela världen att veta att du är ").append(alternativeInsult)
				.append("\n\n");
		messageBuilder.append("Med vänliga hälsningar\n");
		messageBuilder.append(challenger.nickname);
		message = messageBuilder.toString();
	}

	public ChallengeMessageDto createDto() {
		return new ChallengeMessageDto(challenger, challengee, insult, message, SUBJECT);
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
