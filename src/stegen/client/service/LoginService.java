package stegen.client.service;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	LoginDataDto userLoginStatus(String requestUri);

	void registerPlayer(EmailAddressDto email);

}
