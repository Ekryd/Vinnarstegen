package stegen.client.event;

import stegen.client.event.callback.*;
import stegen.shared.*;

public interface EventBus {

	void addHandler(EventCallback<?> callback);

	void checkUserLoginStatus(String hostPageBaseURL);

	void changeNickname(final PlayerDto player, final String nickname);

	void registerPlayer(EmailAddressDto email);

	void sendMessage(PlayerDto player, String message);

	void updateMessageList();

	void updateScoreList();

}
