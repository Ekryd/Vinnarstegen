package stegen.server.command;

import stegen.server.service.*;
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
		this.email = email;
		this.newNickname = nickname;
		this.oldNickname = NicknameService.get().getNickname(email);
	}

	@Override
	public void execute() {
		NicknameService.get().updateNickname(email, newNickname);
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
