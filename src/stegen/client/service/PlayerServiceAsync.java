package stegen.client.service;

import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public interface PlayerServiceAsync {

	void getUserLoginStatus(String requestUri, AsyncCallback<LoginDataDto> callback);

	void registerPlayer(EmailAddressDto email, AsyncCallback<Void> callback);

	void sendMessage(PlayerDto player, String message, AsyncCallback<Void> callback);

	void changeNickname(PlayerDto player, String nickname, AsyncCallback<String> nicknameCallback);

	void getNickname(EmailAddressDto player, AsyncCallback<String> callback);

	void removePlayer(EmailAddressDto email, AsyncCallback<Void> callback);

}
