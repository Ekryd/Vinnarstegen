package stegen.client.service;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

public interface LoginServiceAsync {

	void userLoginStatus(String requestUri, AsyncCallback<LoginDataDto> callback);

	void registerPlayer(EmailAddressDto email, AsyncCallback<Void> callback);

}
