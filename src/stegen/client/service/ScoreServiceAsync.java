package stegen.client.service;

import java.util.*;

import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public interface ScoreServiceAsync {

	void getPlayerScoreList(AsyncCallback<List<PlayerScoreDto>> callback);

	void playerWonOverPlayer(PlayerDto winnerEmail, PlayerDto loserEmail, GameResultDto result, PlayerDto changedBy,
			AsyncCallback<Void> callback);

	void clearAllScores(PlayerDto changedBy, AsyncCallback<Void> callback);

	void challengePlayer(ChallengeMessageDto message, AsyncCallback<Void> callback);

}
