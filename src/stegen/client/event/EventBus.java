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

	void clearCallbacks();

	void undoPlayerCommand(PlayerDto player);

	void challengePlayer(ChallengeMessageDto message);

	void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto result, PlayerDto changedBy);

}
