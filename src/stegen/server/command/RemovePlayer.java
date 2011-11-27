package stegen.server.command;

import stegen.server.database.*;
import stegen.server.service.*;
import stegen.shared.*;

public class RemovePlayer implements PlayerCommand {
	private static final long serialVersionUID = -3955114274841770714L;
	private final EmailAddressDto email;
	private final String alias;

	/** Only for serialization */
	protected RemovePlayer() {
		this.email = null;
		this.alias = null;
	}

	public RemovePlayer(EmailAddressDto email) {
		this.email = email;
		this.alias = NicknameService.get().getNickname(email);
	}

	@Override
	public void execute() {
		PlayerRepository.get().removePlayer(email);
		NicknameService.get().removePlayer(email);
	}

	@Override
	public void undo() {
		// Nope
	}

	@Override
	public String getDescription() {
		return String.format("Tog bort spelare %s, med email %s", alias, email.address);
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
