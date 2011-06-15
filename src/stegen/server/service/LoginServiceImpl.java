package stegen.server.service;

import stegen.client.dto.*;
import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;

import com.google.gwt.user.server.rpc.*;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	private static final long serialVersionUID = 1134570288972922306L;

	@Override
	public LoginDataDto userLoginStatus(String requestUri) {
		CheckLoginStatus command = new CheckLoginStatus(requestUri);
		command.execute();
		CommandInstance commandInstance = new CommandInstance(command, command.getEmail());
		CommandInstanceRepository.get().create(commandInstance);
		return command.getResult();
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		RegisterPlayer command = new RegisterPlayer(email);
		command.execute();
		CommandInstance commandInstance = new CommandInstance(command, command.getEmail());
		CommandInstanceRepository.get().create(commandInstance);
	}
}
