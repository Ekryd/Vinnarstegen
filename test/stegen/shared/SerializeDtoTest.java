package stegen.shared;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.*;

public class SerializeDtoTest {
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	private ChallengeMessageDto challengeMessage = new ChallengeMessageDto(loginData.player.email, loginData.player,
			"insult", "body", "subject");
	private EmailAddressDto emailAddress = loginData.player.email;
	private GameResultDto gameResult = GameResultDto.createEmptyGameResult();
	private LoginResult loginResult = LoginResult.LOGGED_IN_AND_REGISTERED;
	private PlayerCommandDto playerCommand = new PlayerCommandDto(loginData.player, new Date(), "desc");
	private PlayerDto player = loginData.player;
	private PlayerScoreDto playerScore = new PlayerScoreDto(loginData.player, 42, loginData.player, "date");
	private SetScoreDto setScore = new SetScoreDto(42, 43);
	private UndoPlayerCommandResult undoPlayerCommandResult = UndoPlayerCommandResult.SUCCESS;

	@Test
	public void testLoginDataDto() throws Exception {
		assertNotNull(loginData);
		assertNotNull(serializeAndDeserialize(loginData));
	}

	@Test
	public void testChallengeMessageDto() throws Exception {
		assertNotNull(challengeMessage);
		assertNotNull(serializeAndDeserialize(challengeMessage));
	}

	@Test
	public void testEmailAddressDto() throws Exception {
		assertNotNull(emailAddress);
		assertNotNull(serializeAndDeserialize(emailAddress));
	}

	@Test
	public void testGameResultDto() throws Exception {
		assertNotNull(gameResult);
		assertNotNull(serializeAndDeserialize(gameResult));
	}

	@Test
	public void testLoginResult() throws Exception {
		assertNotNull(loginResult);
		assertNotNull(serializeAndDeserialize(loginResult));
	}

	@Test
	public void testPlayerCommandDto() throws Exception {
		assertNotNull(playerCommand);
		assertNotNull(serializeAndDeserialize(playerCommand));
	}

	@Test
	public void testPlayerDto() throws Exception {
		assertNotNull(player);
		assertNotNull(serializeAndDeserialize(player));
	}

	@Test
	public void testPlayerScoreDto() throws Exception {
		assertNotNull(playerScore);
		assertNotNull(serializeAndDeserialize(playerScore));
	}

	@Test
	public void testSetScoreDto() throws Exception {
		assertNotNull(setScore);
		assertNotNull(serializeAndDeserialize(setScore));
	}

	@Test
	public void testUndoPlayerCommandResult() throws Exception {
		assertNotNull(undoPlayerCommandResult);
		assertNotNull(serializeAndDeserialize(undoPlayerCommandResult));
	}

	@Test
	public void testConstructChallengeMessageDto() {
		assertNotNull(new ChallengeMessageDto());
	}

	@Test
	public void testConstructPlayerScoreDto() {
		assertNotNull(new PlayerScoreDto());
	}

	@Test
	public void testConstructPlayerCommandDto() {
		assertNotNull(new PlayerCommandDto());
	}

	@Test
	public void testConstructSetScoreDto() {
		assertNotNull(new SetScoreDto());
	}

	@SuppressWarnings("unchecked")
	private <T> T serializeAndDeserialize(T dto) throws IOException, ClassNotFoundException {
		byte[] byteArray = serialize(dto);
		return (T) deserialize(byteArray);
	}

	private byte[] serialize(Object dto) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(dto);
		objectOutputStream.flush();
		return outputStream.toByteArray();
	}

	/** Apparently this does not invoke default constructor */
	private Object deserialize(byte[] byteArray) throws IOException, ClassNotFoundException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		return objectInputStream.readObject();
	}

}
