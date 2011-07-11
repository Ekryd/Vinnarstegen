package stegen.client.dto;

import java.io.*;

public class ChallengeMessageDto implements Serializable {
	private static final long serialVersionUID = 2612413882389891300L;

	public PlayerDto challenger;
	public PlayerDto challengee;
	public String insult;
	public String body;
	public String subject;

	/** For Serialization */
	protected ChallengeMessageDto() {}

	public ChallengeMessageDto(PlayerDto challenger, PlayerDto challengee, String insult, String body, String subject) {
		this.challenger = challenger;
		this.challengee = challengee;
		this.insult = insult;
		this.body = body;
		this.subject = subject;
	}

}
