package stegen.server.mail;

import javax.mail.*;

public class MailException extends RuntimeException {
	private static final long serialVersionUID = -1255487115082633603L;

	public MailException(String arguments, MessagingException e) {
		super(arguments, e);
	}

	public MailException(MessagingException e) {
		super(e);
	}

}
