package stegen.server.command;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import stegen.client.dto.*;

public class SendMessage implements PlayerCommand {
	private static final long serialVersionUID = -3955114274841770714L;
	private final EmailAddressDto email;
	private final String message;

	/** Only for serialization */
	protected SendMessage() {
		this.email = null;
		this.message = null;
	}

	public SendMessage(EmailAddressDto email, String message) {
		this.email = email;
		this.message = message;
	}

	@Override
	public void execute() {
		sendMailToAdmin();
	}

	private void sendMailToAdmin() {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = email.address + " " + message;
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(email.address, "Vinnarstegen"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("admin@vinnarstegen.appspotmail.com"));
			msg.setSubject("Vinnarstegen ropar");
			msg.setText(msgBody);
			Transport.send(msg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undo() {
		// Nope
	}

	@Override
	public String getDescription() {
		return message;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
