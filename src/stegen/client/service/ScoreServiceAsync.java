package stegen.client.service;

import java.util.*;

import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public interface ScoreServiceAsync {

	void getPlayerScoreList(EmailAddressDto currentPlayerEmail, AsyncCallback<List<PlayerScoreDto>> callback);

	void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto result, PlayerDto changedBy,
			AsyncCallback<Void> callback);

	void clearAllScores(PlayerDto changedBy, AsyncCallback<Void> callback);

	void challengePlayer(ChallengeMessageDto message, AsyncCallback<Void> callback);

}
