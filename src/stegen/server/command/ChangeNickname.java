package stegen.server.command;

import stegen.client.dto.*;
import stegen.server.database.*;

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

	public ChangeNickname(EmailAddressDto player, String nickname) {
		this.email = player;
		this.newNickname = nickname;
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		this.oldNickname = stegenUserRepository.getOrCreateNickname(player);
	}

	@Override
	public void execute() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		stegenUserRepository.updateNickname(email, newNickname);
	}

	@Override
	public void undo() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		stegenUserRepository.updateNickname(email, oldNickname);
	}

	@Override
	public String getDescription() {
		return String.format("%s bytte alias från %s till %s", email.address, oldNickname, newNickname);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

}
