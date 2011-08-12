package stegen.server.command;

import stegen.server.mail.*;
import stegen.shared.*;

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
		String msgBody = email.address + " " + message;
		EmailAddressDto admin = new EmailAddressDto("bjorn.ekryd@gmail.com");

		MailBuilder mailBuilder = new MailBuilder();
		mailBuilder.from(email, "Vinnarstegen").to(admin).subject("Vinnarstegen ropar").messageBody(msgBody).send();
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
