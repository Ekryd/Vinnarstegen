package stegen.client.service;

import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("login")
public interface PlayerService extends RemoteService {

	LoginDataDto getUserLoginStatus(String requestUri);

	void registerPlayer(EmailAddressDto email);

	void sendMessage(PlayerDto player, String message);

	PlayerDto changeNickname(PlayerDto player, String nickname);

	String getNickname(EmailAddressDto player);

}
