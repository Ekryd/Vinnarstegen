package stegen.client.service;

import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("login")
public interface PlayerService extends RemoteService {

	LoginDataDto getUserLoginStatus(String requestUri);

	void registerPlayer(EmailAddressDto email);

	void sendMessage(PlayerDto player, String message);

	String changeNickname(PlayerDto player, String nickname);

	String getNickname(EmailAddressDto player);

	boolean isNewUserPasswordOk(String registrationCode);

	void removePlayer(EmailAddressDto email);

}
