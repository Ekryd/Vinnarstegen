package stegen.server.database;

import java.text.*;
import java.util.*;

import javax.jdo.annotations.*;

import stegen.server.service.*;
import stegen.shared.*;

@PersistenceCapable
public class Player {
	@PrimaryKey
	@Persistent(nullValue = NullValue.EXCEPTION)
	private String emailString;
	@SuppressWarnings("unused")
	@Persistent(nullValue = NullValue.EXCEPTION)
	private Date createdDateTime;

	@Persistent(nullValue = NullValue.EXCEPTION)
	private int score;

	@Persistent(nullValue = NullValue.EXCEPTION)
	private String changedByEmailString;
	@Persistent(nullValue = NullValue.EXCEPTION)
	private Date changed;

	private Player(String emailString, Date createdDateTime, int score) {
		this.emailString = emailString;
		this.createdDateTime = createdDateTime;
		this.score = score;
	}

	public static Player createPlayer(EmailAddressDto email) {
		Player player = new Player(email.address, new Date(), 0);
		player.setChangedBy(email);
		return player;
	}

	private void setChangedBy(EmailAddressDto email) {
		changedByEmailString = email.address;
		Date now = new Date();
		changed = now;
	}

	public int getScore() {
		return score;
	}

	public PlayerScoreDto createDto(SimpleDateFormat dateFormat) {
		NicknameService nicknameService = NicknameService.get();
		return new PlayerScoreDto(nicknameService.createPlayerDto(emailString), score,
				nicknameService.createPlayerDto(changedByEmailString), dateFormat.format(changed));
	}

	public void changeScore(int newScore, EmailAddressDto changedBy) {
		score = newScore;
		setChangedBy(changedBy);
	}

	/**
	 * @return the emailString
	 */
	public EmailAddressDto getEmail() {
		return new EmailAddressDto(emailString);
	}
}
