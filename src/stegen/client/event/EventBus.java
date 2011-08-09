package stegen.client.event;

import stegen.client.messages.*;
import stegen.shared.*;

public interface EventBus {

	void addHandler(Event event, DefaultCallback<?> defaultCallback);

	void checkUserLoginStatus(String hostPageBaseURL);

	void changeNickname(final PlayerDto player, final String nickname);

	void registerPlayer(EmailAddressDto email);

}
