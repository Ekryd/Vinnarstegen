package stegen.client.service;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

public interface PlayerServiceAsync {

	void userLoginStatus(String requestUri, AsyncCallback<LoginDataDto> callback);

	void registerPlayer(EmailAddressDto email, AsyncCallback<Void> callback);

	void sendMessage(PlayerDto player, String message, AsyncCallback<Void> callback);

	void changeNickname(EmailAddressDto player, String nickname, AsyncCallback<Void> callback);

	void getNickname(EmailAddressDto player, AsyncCallback<String> callback);

}
