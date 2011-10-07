package stegen.server.service;

import java.text.*;
import java.util.*;

import stegen.client.service.*;
import stegen.server.command.*;
import stegen.server.database.*;
import stegen.server.database.PlayerRepository.Func;
import stegen.shared.*;

import com.google.gwt.user.server.rpc.*;

/**
 * @author Björn Ekryd
 */
public class ScoreServiceImpl extends RemoteServiceServlet implements ScoreService {
	private static final long serialVersionUID = 5777230422402242088L;

	@Override
	public void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto result, PlayerDto changedBy) {
		if (winner.equals(loser)) {
			return;
		}
		PlayerCommand command = new PlayerWonOverPlayer(winner.email, loser.email, result, changedBy.email);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, changedBy.email);
		CommandInstanceRepository.get().create(commandInstanceToStore);
	}

	@Override
	public void clearAllScores(PlayerDto changedBy) {
		PlayerCommand command = new ClearAllScores(changedBy.email);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, changedBy.email);
		CommandInstanceRepository.get().create(commandInstanceToStore);
	}

	@Override
	public List<PlayerScoreDto> getPlayerScoreList() {
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

	@Override
	public void challengePlayer(ChallengeMessageDto message) {
		PlayerCommand command = new Challenge(message);
		command.execute();
		CommandInstance commandInstanceToStore = new CommandInstance(command, message.challengerEmail);
		CommandInstanceRepository.get().create(commandInstanceToStore);
	}

}
