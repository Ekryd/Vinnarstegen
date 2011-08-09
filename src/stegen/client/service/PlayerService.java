package stegen.client.service;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("login")
public interface PlayerService extends RemoteService {

	LoginDataDto userLoginStatus(String requestUri);

	void registerPlayer(EmailAddressDto email);

	void sendMessage(PlayerDto player, String message);

	void changeNickname(PlayerDto player, String nickname);

	String getNickname(EmailAddressDto player);

}
