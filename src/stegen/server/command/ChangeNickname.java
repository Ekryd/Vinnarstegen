package stegen.server.command;

import stegen.server.database.*;
import stegen.shared.*;

public class ChangeNickname implements PlayerCommand {
	private static final long serialVersionUID = 4813202485158945857L;
	private final EmailAddressDto email;
	private final String newNickname;
	private final String oldNickname;

	/** Only for serialization */
	protected ChangeNickname() {
		this.email = null;
		this.newNickname = null;
		this.oldNickname = null;
	}

	public ChangeNickname(EmailAddressDto email, String nickname) {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		this.email = email;
		this.newNickname = nickname;
		this.oldNickname = stegenUserRepository.getOrCreateNickname(email);
	}

	@Override
	public void execute() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		stegenUserRepository.updateNickname(email, newNickname);
	}

	@Override
	public void undo() {
		// Nope
	}

	@Override
	public String getDescription() {
		return String.format("%s bytte alias från %s till %s", email.address, oldNickname, newNickname);
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
