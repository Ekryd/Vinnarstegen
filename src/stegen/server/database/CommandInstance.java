package stegen.server.database;

import java.util.*;

import javax.jdo.annotations.*;

import stegen.server.command.*;
import stegen.shared.*;

import com.google.appengine.api.datastore.*;

@PersistenceCapable
public class CommandInstance {
	public static final CommandInstance NOT_FOUND = getNotFoundInstance();
	private transient final Serializer serializer = new Serializer();

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent(nullValue = NullValue.EXCEPTION)
	private String emailString;

	@Persistent(nullValue = NullValue.EXCEPTION)
	private Date dateTime;

	@Persistent
	private boolean undoable;

	@Persistent
	private Text commandInJsonFormat;

	@Persistent
	private String commandClassName;

	private CommandInstance() {}

	public CommandInstance(PlayerCommand command, EmailAddressDto email) {
		this.commandInJsonFormat = new Text(serializer.deepSerialize(command));
		this.emailString = email.address;
		this.dateTime = new Date();
		this.undoable = command.isUndoable();
		this.commandClassName = command.getClass().getName();
	}

	public void undo() {
		getCommand().undo();
	}

	public PlayerCommandDto createPlayerCommandDto() {
		StegenUserRepository stegenUserRepository = StegenUserRepository.get();
		return new PlayerCommandDto(stegenUserRepository.createPlayerDto(emailString), dateTime, getCommand()
				.getDescription());
	}

	public boolean belongsTo(EmailAddressDto player) {
		return emailString.equals(player.address);
	}

	public Long getId() {
		return id;
	}

	public boolean isUndoable() {
		return undoable;
	}

	private static CommandInstance getNotFoundInstance() {
		CommandInstance commandInstance = new CommandInstance() {
			@Override
			public PlayerCommandDto createPlayerCommandDto() {
				return null;
			}
		};
		commandInstance.id = 0L;
		return commandInstance;
	}

	String getCommandSerialized() {
		return commandInJsonFormat.getValue();
	}

	public PlayerCommand getCommand() {
		return serializer.deserialize(commandInJsonFormat.getValue(), getClassName());
	}

	@SuppressWarnings("unchecked")
	private Class<PlayerCommand> getClassName() {
		try {
			return (Class<PlayerCommand>) Class.forName(commandClassName);
		} catch (ClassNotFoundException e) {
			return PlayerCommand.class;
		}
	}

}
