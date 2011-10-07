package stegen.server.mail;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import javax.mail.*;
import javax.mail.internet.*;

import stegen.shared.*;

public class MailBuilder {

	private static final Logger LOG = Logger.getLogger(MailBuilder.class.getName());
	private static final String DEFAULT_ENCODING = "UTF-8";
	final MimeMessage mailMessage;

	public MailBuilder() {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		mailMessage = new MimeMessage(session);

	}

	public MailBuilder from(EmailAddressDto email, String nickname) {
		try {
			mailMessage.setFrom(new InternetAddress(email.address, nickname, DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			throw new MailException(email.address + ":" + nickname, e);
		}
		return this;
	}

	public MailBuilder toAdmin() {
		try {
			mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("bjorn.ekryd@gmail.com"));
		} catch (MessagingException e) {
			throw new MailException(e);
		}
		return this;
	}

	public MailBuilder to(EmailAddressDto email) {
		try {
			mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email.address));
		} catch (MessagingException e) {
			throw new MailException(email.address, e);
		}
		return this;
	}

	public MailBuilder to(EmailAddressDto email, String nickname) {
		try {
			mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email.address, nickname,
					DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			throw new MailException(email.address + ":" + nickname, e);
		}
		return this;
	}

	public MailBuilder subject(String subject) {
		try {
			mailMessage.setSubject(subject, DEFAULT_ENCODING);
		} catch (MessagingException e) {
			throw new MailException(subject, e);
		}
		return this;
	}

	public MailBuilder messageBody(String messageBody) {
		try {
			mailMessage.setText(messageBody, DEFAULT_ENCODING);
		} catch (MessagingException e) {
			throw new MailException(messageBody, e);
		}
		return this;
	}

	public void send() {
		try {
			Transport.send(mailMessage);
			logSentMail();
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	private void logSentMail() {
		try {
			LOG.info(String.format("Sent message from %s to %s, Subject: %s\n%s", toString(mailMessage.getFrom()),
					toString(mailMessage.getRecipients(Message.RecipientType.TO)), mailMessage.getSubject(),
					mailMessage.getContent()));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String toString(Address[] from) {
		return Arrays.toString(from);
	}

}
