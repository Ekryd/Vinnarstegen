package stegen.server.service;

import java.text.*;
import java.util.*;

import stegen.client.dto.*;
import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;
import stegen.server.database.PlayerRepository.Func;

import com.google.gwt.user.server.rpc.*;

public class ScoreServiceImpl extends RemoteServiceServlet implements ScoreService {
	private static final long serialVersionUID = 5777230422402242088L;

	@Override
	public void playerWonOverPlayer(EmailAddressDto winnerEmail, EmailAddressDto loserEmail, GameResultDto result,
			EmailAddressDto changedBy) {
		if (winnerEmail.equals(loserEmail)) {
			return;
		}
		PlayerCommand command = new PlayerWonOverPlayer(winnerEmail, loserEmail, result, changedBy);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, changedBy);
		CommandInstanceRepository.get().create(commandInstanceToStore);
	}

	@Override
	public void clearAllScores(EmailAddressDto changedBy) {
		PlayerCommand command = new ClearAllScores(changedBy);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, changedBy);
		CommandInstanceRepository.get().create(commandInstanceToStore);
	}

	@Override
	public List<PlayerScoreDto> getPlayerList() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		final List<PlayerScoreDto> playerScoreList = new ArrayList<PlayerScoreDto>();
		PlayerRepository.get().processAndPersist(new Func() {

			@Override
			public void exec(Player player) {
				playerScoreList.add(player.createDto(dateFormat));
			}
		});
		List<PlayerScoreDto> playerScoreListWithRanking = calculateRanking(playerScoreList);
		return playerScoreListWithRanking;
	}

	private List<PlayerScoreDto> calculateRanking(List<PlayerScoreDto> sortedPlayerScores) {
		int currentScore = 0;
		int ranking = 0;
		for (PlayerScoreDto playerScore : sortedPlayerScores) {
			if (playerScore.score != currentScore) {
				currentScore = playerScore.score;
				ranking++;
			}
			playerScore.ranking = ranking;
		}
		return sortedPlayerScores;
	}

}
