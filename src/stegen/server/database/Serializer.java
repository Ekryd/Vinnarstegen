package stegen.server.database;

import stegen.server.command.*;

import com.google.gson.*;

public class Serializer {
	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final Gson gson;

	public Serializer() {
		gson = gsonBuilder.create();
	}

	public String deepSerialize(PlayerCommand command) {
		return gson.toJson(command);
	}

	public <T> T deserialize(String commandInJsonFormat, Class<T> commandClass) {
		return gson.fromJson(commandInJsonFormat, commandClass);
	}
}
