package stegen.shared;

import java.io.*;
import java.util.*;

public class PlayerCommandDto implements Serializable {
	private static final long serialVersionUID = -293215457404837346L;
	public PlayerDto player;
	public Date performedDateTime;
	public String description;

	/** For Serialization */
	protected PlayerCommandDto() {}

	public PlayerCommandDto(PlayerDto player, Date performedDateTime, String description) {
		this.player = player;
		this.performedDateTime = performedDateTime;
		this.description = description;
	}

}
