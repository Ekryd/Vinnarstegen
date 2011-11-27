package stegen.server.command;

import stegen.server.database.*;
import stegen.server.service.*;
import stegen.shared.*;

public class RegisterPlayer implements PlayerCommand {
	private static final long serialVersionUID = -3955114274841770714L;
	private final EmailAddressDto email;

	/** Only for serialization */
	protected RegisterPlayer() {
		this.email = null;
	}

	public RegisterPlayer(EmailAddressDto email) {
		this.email = email;
	}

	@Override
	public void execute() {
		Player player = Player.createPlayer(email);
		PlayerRepository.get().create(player);
	}

	@Override
	public void undo() {
		// Nope
	}

	@Override
	public String getDescription() {
		return "Registrerade " + NicknameService.get().getNickname(email);
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
