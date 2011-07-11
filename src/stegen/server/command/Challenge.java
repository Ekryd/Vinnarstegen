package stegen.server.command;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import stegen.client.dto.*;
import stegen.server.database.*;

public class Challenge implements PlayerCommand {
	private static final long serialVersionUID = 1381534401064229916L;
	private final String insult;
	private final EmailAddressDto challenger;
	private final EmailAddressDto challengee;
	private final transient String messageBody;
	private final transient String subject;

	/** Only for serialization */
	protected Challenge() {
		this.insult = null;
		this.challenger = null;
		this.challengee = null;
		this.messageBody = null;
		this.subject = null;
	}

	public Challenge(ChallengeMessageDto message) {
		this.insult = message.insult;
		this.challenger = message.challenger.email;
		this.challengee = message.challengee.email;
		this.messageBody = message.body;
		this.subject = message.subject;
	}

	@Override
	public void execute() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		String challengerNickname = stegenUserRepository.getOrCreateNickname(challenger);
		String challengeeNickname = stegenUserRepository.getOrCreateNickname(challengee);
		try {
			tryToCreateAndSendEmail(challengerNickname, challengeeNickname);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void tryToCreateAndSendEmail(String challengerNickname, String challengeeNickname)
			throws MessagingException, UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(challenger.address, challengerNickname));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(challengee.address, challengeeNickname));
		msg.setSubject(subject);
		msg.setText(messageBody);
		Transport.send(msg);
	}

	@Override
	public void undo() {
		// Nope
	}

	@Override
	public String getDescription() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		String challengerNickname = stegenUserRepository.getOrCreateNickname(challenger);
		String challengeeNickname = stegenUserRepository.getOrCreateNickname(challengee);
		return String.format("%s kallade %s för %s och utmanade därmed honom till duell", challengerNickname,
				challengeeNickname, insult);
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
