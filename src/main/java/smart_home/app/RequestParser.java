package smart_home.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import smart_home.app.exception.ExitRequestException;
import smart_home.app.exception.InvalidRequestException;
import smart_home.app.exception.UnknownRequestTypeException;

public class RequestParser {
    private static final String K_TYPE = "type";
    private static final String K_DEVICE_ID = "deviceId";
    private static final String K_ACTION = "action";
    private static final String K_NAME = "name";

    private final Gson gson = new Gson();

    public Request parse(String line) {
        if (line == null || line.isBlank()) throw new InvalidRequestException("EMPTY");
        if ("EXIT".equalsIgnoreCase(line)) throw new ExitRequestException();

        JsonObject json = gson.fromJson(line, JsonObject.class);
        if (json == null || !json.has(K_TYPE)) throw new InvalidRequestException("BAD_JSON_OR_NO_TYPE");

        String type = json.get(K_TYPE).getAsString().toLowerCase();
        return switch (type) {
            case "command" -> parseCommand(json);
            case "mode"    -> parseMode(json);
            default        -> throw new UnknownRequestTypeException(type);
        };
    }

    private CommandRequest parseCommand(JsonObject json) {
        if (!json.has(K_DEVICE_ID)) throw new InvalidRequestException("MISSING_DEVICE_ID");
        String id = json.get(K_DEVICE_ID).getAsString();

        String action = json.has(K_ACTION) ? json.get("action").getAsString() : null;
        String voice  = json.has("voice")  ? json.get("voice").getAsString()  : null;
        Boolean energy = json.has("energy") ? json.get("energy").getAsBoolean() : null;
        Boolean online = json.has("online") ? json.get("online").getAsBoolean() : null;

        if (action == null && voice == null && energy == null && online == null)
            throw new InvalidRequestException("NO_COMMAND_FIELDS");

        return new CommandRequest(id, action, voice, energy, online);
    }


    private ModeRequest parseMode(JsonObject json) {
        if (!json.has(K_NAME)) throw new InvalidRequestException("MISSING_MODE_NAME");
        return new ModeRequest(json.get(K_NAME).getAsString());
    }
}
