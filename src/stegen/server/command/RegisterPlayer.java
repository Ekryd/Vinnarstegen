package stegen.server.command;

import stegen.client.dto.*;
import stegen.server.database.*;

public class RegisterPlayer implements PlayerCommand {
	private static final long serialVersionUID = -3955114274841770714L;
	private final EmailAddressDto email;

	protected RegisterPlayer() {
		email = null;
	}

	public RegisterPlayer(EmailAddressDto email) {
		this.email = email;
	}

	public EmailAddressDto getEmail() {
		return email;
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
		return "Registrerade " + email.address;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

}
