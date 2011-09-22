package stegen.server.command;

import stegen.server.database.*;
import stegen.server.mail.*;
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
		this.challengee = message.challengee.email;
		this.messageBody = message.body;
		this.subject = message.subject;
	}

	@Override
	public void execute() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		String challengerNickname = stegenUserRepository.getOrCreateNickname(challenger);
		String challengeeNickname = stegenUserRepository.getOrCreateNickname(challengee);
		MailBuilder mailBuilder = new MailBuilder();
		mailBuilder.from(challenger, challengerNickname).to(challengee, challengeeNickname).subject(subject)
				.messageBody(messageBody).send();
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
