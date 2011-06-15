package stegen.server.database;

import java.lang.reflect.*;

import stegen.server.command.*;

import com.google.gson.*;

public class Serializer {
	private final GsonBuilder gsonBuilder = new GsonBuilder();
	private final Gson gson;

	public Serializer() {
		// gsonBuilder.registerTypeAdapter(PlayerCommand.class, new
		// PlayerCommandCreator());
		gsonBuilder.registerTypeAdapter(PlayerCommand.class, new PlayerCommandDeserializer());
		gson = gsonBuilder.create();
	}

	public String deepSerialize(PlayerCommand command) {
		JsonObject jsonObject = (JsonObject) gson.toJsonTree(command);
		jsonObject.addProperty("class", command.getClass().getName());
		return gson.toJson(jsonObject);
	}

	public PlayerCommand deserialize(String commandInJsonFormat) {
		return gson.fromJson(commandInJsonFormat, PlayerCommand.class);
	}

	private class PlayerCommandDeserializer implements JsonDeserializer<PlayerCommand> {

		@SuppressWarnings("unchecked")
		@Override
		public PlayerCommand deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			JsonObject jsonObject = (JsonObject) element;
			String className = jsonObject.get("class").getAsString();
			Class<PlayerCommand> clazz = null;
			try {
				clazz = (Class<PlayerCommand>) Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return gson.fromJson(element, clazz);
		}
	}

}
