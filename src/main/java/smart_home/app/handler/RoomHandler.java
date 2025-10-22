package smart_home.app.handler;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import smart_home.app.RoomRequest;
import smart_home.core.SmartHomeFacade;

public class RoomHandler {
    private final SmartHomeFacade hub;
    private final Gson gson = new Gson();

    public RoomHandler(SmartHomeFacade hub) { this.hub = hub; }

    public String handle(RoomRequest req) {
        hub.controlRoom(req.name(), req.action());
        Map<String,Object> ok = new HashMap<>();
        ok.put("status", "ok");
        ok.put("room", req.name());
        ok.put("action", req.action());
        return gson.toJson(ok);
    }
}