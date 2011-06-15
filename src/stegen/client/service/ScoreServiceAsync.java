package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

public interface ScoreServiceAsync {

	void getPlayerList(AsyncCallback<List<PlayerScoreDto>> callback);

	void playerWonOverPlayer(EmailAddressDto winnerEmail, EmailAddressDto loserEmail, GameResultDto result,
			EmailAddressDto changedBy, AsyncCallback<Void> callback);

	void clearAllScores(EmailAddressDto changedBy, AsyncCallback<Void> callback);

}
