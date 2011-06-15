package stegen.client.dto;

import java.io.*;
import java.util.*;

public class PlayerCommandDto implements Serializable {
	private static final long serialVersionUID = -293215457404837346L;
	public EmailAddressDto player;
	public Date performedDateTime;
	public String description;

	protected PlayerCommandDto() {}

	public PlayerCommandDto(EmailAddressDto player, Date performedDateTime, String description) {
		this.player = player;
		this.performedDateTime = performedDateTime;
		this.description = description;
	}

}
