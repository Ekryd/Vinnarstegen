package stegen.server.database;

import java.text.*;
import java.util.*;

import javax.jdo.annotations.*;

import stegen.client.dto.*;

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
		return new PlayerScoreDto(new EmailAddressDto(emailString), score, new EmailAddressDto(changedByEmailString),
				dateFormat.format(changed));
	}

	public void clearScore(EmailAddressDto changedBy) {
		score = 0;
		setChangedBy(changedBy);
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
