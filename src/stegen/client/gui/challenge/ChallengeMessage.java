package stegen.client.gui.challenge;

import java.util.*;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.gwt.i18n.client.*;

public class ChallengeMessage {
	private final static InsultFactory INSULT_FACTORY = new InsultFactory();
	private final DateTimeFormat dateFormat = DateTimeFormat.getFormat("'kl 'HH:mm' den 'dd/MM");

	private static final String SUBJECT = "Utmaning från Vinnarstegen";
	private final PlayerDto challenger;
	private final PlayerDto challengee;
	private final Date challengeDateTime;
	private String message;
	private final String insult;

	public ChallengeMessage(PlayerDto challenger, PlayerDto challengee) {
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengeDateTime = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
		this.insult = INSULT_FACTORY.createCompleteInsult();
		createDefaultMessage();
	}

	private void createDefaultMessage() {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Jag, ").append(challenger.nickname).append(" (").append(challenger.email.address)
				.append("), ");
		messageBuilder.append(" tycker att du, ").append(challengee.nickname).append(", är ").append(insult)
				.append("!\n");
		messageBuilder.append("Försvara din ära! Möt mig i pingis ").append(dateFormat.format(challengeDateTime))
				.append(".\n");
		messageBuilder.append("Annars kommer hela världen att veta att du är ")
				.append(INSULT_FACTORY.createCompleteInsult()).append("\n\n");
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

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return SUBJECT;
	}

	public String getInsult() {
		return insult;
	}

}
