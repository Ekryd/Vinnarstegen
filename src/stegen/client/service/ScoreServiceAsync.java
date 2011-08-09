package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public interface ScoreServiceAsync {

	void getPlayerList(AsyncCallback<List<PlayerScoreDto>> callback);

	void playerWonOverPlayer(PlayerDto winnerEmail, PlayerDto loserEmail, GameResultDto result, PlayerDto changedBy,
			AsyncCallback<Void> callback);

	void clearAllScores(PlayerDto changedBy, AsyncCallback<Void> callback);

	void challenge(ChallengeMessageDto message, AsyncCallback<Void> callback);

}
