package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("score")
public interface ScoreService extends RemoteService {

	List<PlayerScoreDto> getPlayerList();

	void playerWonOverPlayer(PlayerDto winnerEmail, PlayerDto loserEmail, GameResultDto result, PlayerDto changedBy);

	void clearAllScores(PlayerDto changedBy);

	void challenge(ChallengeMessageDto message);

}
