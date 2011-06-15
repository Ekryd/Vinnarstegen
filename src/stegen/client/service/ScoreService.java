package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("score")
public interface ScoreService extends RemoteService {

	List<PlayerScoreDto> getPlayerList();

	void playerWonOverPlayer(EmailAddressDto winnerEmail, EmailAddressDto loserEmail, GameResultDto result,
			EmailAddressDto changedBy);

	void clearAllScores(EmailAddressDto changedBy);

}
