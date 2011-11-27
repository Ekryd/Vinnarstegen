package stegen.server.command;

import stegen.server.mail.*;
import stegen.server.service.*;
import stegen.shared.*;

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
		this.challenger = message.challengerEmail;
		this.challengee = message.challengeeEmail;
		this.messageBody = message.body;
		this.subject = message.subject;
	}

	@Override
	public void execute() {
		sendEmailToChallengee();
		sendEmailToAdmin();
	}

	private void sendEmailToChallengee() {
		NicknameService nicknameService = NicknameService.get();
		String challengerNickname = nicknameService.getNickname(challenger);
		String challengeeNickname = nicknameService.getNickname(challengee);
		MailBuilder mailBuilder = new MailBuilder();
		mailBuilder.from(challenger, challengerNickname).to(challengee, challengeeNickname).subject(subject)
				.messageBody(messageBody).send();
	}

	private void sendEmailToAdmin() {
		String msgBody = String.format("Utmanaren skriver till %s, rubrik: %s \n%s", challengee.address, subject,
				messageBody);

		MailBuilder mailBuilder = new MailBuilder();
		mailBuilder.from(challenger, "Vinnarstegen").toAdmin().subject("Vinnarstegen utmanar").messageBody(msgBody)
				.send();
	}

	@Override
	public void undo() {
		// Nope
	}

	@Override
	public String getDescription() {
		NicknameService nicknameService = NicknameService.get();
		String challengerNickname = nicknameService.getNickname(challenger);
		String challengeeNickname = nicknameService.getNickname(challengee);
		return String.format("%s kallade %s för %s och utmanade därmed honom till duell", challengerNickname,
				challengeeNickname, insult);
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
