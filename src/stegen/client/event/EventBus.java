package stegen.client.event;

import stegen.shared.*;

public interface EventBus {

	void addHandler(EventCallback<?> callback);

	void getApplicationVersion();

	void getUserLoginStatus(String hostPageBaseURL);

	void changeNickname(final PlayerDto player, final String nickname);

	void registerPlayer(EmailAddressDto email);

	void isNewUserPasswordOk(String newUserPassword);

	void sendMessage(PlayerDto player, String message);

	void updateSendMessageList();

	void updatePlayerScoreList(EmailAddressDto currentPlayerEmail);

	void clearAllScores(PlayerDto changedBy);

	void clearCallbacks();

	void undoPlayerCommand(PlayerDto player);

	void challengePlayer(ChallengeMessageDto message);

	void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto result, PlayerDto changedBy);

	void refresh();

	void updateGameResultList();

	void updateUndoCommand();

	void updateLoginStatusList();

	void updatePlayerMiscCommandList();
}
