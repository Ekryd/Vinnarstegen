package stegen.server.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import stegen.shared.*;

public class CalculateRankingTest {

	@Before
	public void setUp() throws Exception {}

	@Test
	public void rankingShouldBeSetOnList() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address1");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		int rank = 1;
		for (PlayerScoreDto playerScoreDto : actual) {
			assertThat(playerScoreDto.ranking, is(rank));
			rank++;
		}
	}

	@Test
	public void equalScoreShouldGetEqualRank() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address1");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		sortedPlayerScores.get(1).score = 9;
		sortedPlayerScores.get(2).score = 9;

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		assertThat(actual.get(0).ranking, is(1));

		assertThat(actual.get(1).ranking, is(2));
		assertThat(actual.get(2).ranking, is(2));

		assertThat(actual.get(3).ranking, is(3));
	}

	@Test
	public void callingPlayerShoudlNotBeAbleToChallengeOneSelf() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address1");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		assertThat(actual.get(0).showChallenge, is(true));

		assertThat(actual.get(1).showChallenge, is(false));

		assertThat(actual.get(2).showChallenge, is(true));
		assertThat(actual.get(3).showChallenge, is(true));
	}

	@Test
	public void onlyShowChallengeForRankThreeAboveAndBelow() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address4");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		assertThat(actual.get(0).showChallenge, is(false));

		assertThat(actual.get(1).showChallenge, is(true));
		assertThat(actual.get(2).showChallenge, is(true));
		assertThat(actual.get(3).showChallenge, is(true));

		assertThat(actual.get(4).showChallenge, is(false));

		assertThat(actual.get(5).showChallenge, is(true));
		assertThat(actual.get(6).showChallenge, is(true));
		assertThat(actual.get(7).showChallenge, is(true));

		assertThat(actual.get(8).showChallenge, is(false));
		assertThat(actual.get(9).showChallenge, is(false));
	}

	@Test
	public void onlyShowChallengeForRankThreeAboveAndBelowAtTopOfList() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address0");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		assertThat(actual.get(0).showChallenge, is(false));

		assertThat(actual.get(1).showChallenge, is(true));
		assertThat(actual.get(2).showChallenge, is(true));
		assertThat(actual.get(3).showChallenge, is(true));

		assertThat(actual.get(4).showChallenge, is(false));
		assertThat(actual.get(5).showChallenge, is(false));
		assertThat(actual.get(6).showChallenge, is(false));
		assertThat(actual.get(7).showChallenge, is(false));
		assertThat(actual.get(8).showChallenge, is(false));
		assertThat(actual.get(9).showChallenge, is(false));
	}

	@Test
	public void onlyShowChallengeForRankThreeAboveAndBelowAtBottomOfList() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address9");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		assertThat(actual.get(0).showChallenge, is(false));
		assertThat(actual.get(1).showChallenge, is(false));
		assertThat(actual.get(2).showChallenge, is(false));
		assertThat(actual.get(3).showChallenge, is(false));
		assertThat(actual.get(4).showChallenge, is(false));
		assertThat(actual.get(5).showChallenge, is(false));

		assertThat(actual.get(6).showChallenge, is(true));
		assertThat(actual.get(7).showChallenge, is(true));
		assertThat(actual.get(8).showChallenge, is(true));

		assertThat(actual.get(9).showChallenge, is(false));
	}

	@Test
	public void showChallengeShouldUseRankNotListPosition() {
		EmailAddressDto currentPlayerEmail = new EmailAddressDto("address5");
		List<PlayerScoreDto> sortedPlayerScores = createSortedScoreList();
		for (PlayerScoreDto playerScoreDto : sortedPlayerScores) {
			assertThat(playerScoreDto.ranking, is(0));
		}

		sortedPlayerScores.get(4).score = 6;
		sortedPlayerScores.get(5).score = 6;
		sortedPlayerScores.get(6).score = 6;

		CalculateRanking calculateRanking = new CalculateRanking(currentPlayerEmail, sortedPlayerScores);

		List<PlayerScoreDto> actual = calculateRanking.getList();
		assertThat(actual.get(0).showChallenge, is(false));

		assertThat(actual.get(1).showChallenge, is(true));
		assertThat(actual.get(2).showChallenge, is(true));
		assertThat(actual.get(3).showChallenge, is(true));

		// Same rank
		assertThat(actual.get(4).showChallenge, is(true));
		assertThat(actual.get(5).showChallenge, is(false));
		assertThat(actual.get(6).showChallenge, is(true));

		assertThat(actual.get(7).showChallenge, is(true));
		assertThat(actual.get(8).showChallenge, is(true));
		assertThat(actual.get(9).showChallenge, is(true));
	}

	private List<PlayerScoreDto> createSortedScoreList() {
		List<PlayerScoreDto> sortedPlayerScores = new ArrayList<PlayerScoreDto>();
		for (int i = 0; i < 10; i++) {
			EmailAddressDto email = new EmailAddressDto("address" + i);
			PlayerDto player = new PlayerDto(email, email.address);
			PlayerScoreDto playerScoreDto = new PlayerScoreDto(player, 10 - i, player, "date");
			sortedPlayerScores.add(playerScoreDto);
		}
		return sortedPlayerScores;
	}

}
