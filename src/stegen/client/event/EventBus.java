package stegen.client.event;

import stegen.shared.*;

public interface EventBus {

	void addHandler(EventCallback<?> callback);

	void getUserLoginStatus(String hostPageBaseURL);

	void changeNickname(final PlayerDto player, final String nickname);

	void registerPlayer(EmailAddressDto email);

	void sendMessage(PlayerDto player, String message);

	void updateSendMessageList();

	void updatePlayerScoreList();

	void clearAllScores(PlayerDto changedBy);

}
