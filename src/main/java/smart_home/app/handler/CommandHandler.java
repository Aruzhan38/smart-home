package smart_home.app.handler;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import smart_home.app.CommandRequest;
import smart_home.core.SmartHomeFacade;

public class CommandHandler {
    private final SmartHomeFacade hub;
    private final Gson gson = new Gson();

    public CommandHandler(SmartHomeFacade hub) { this.hub = hub; }

    public String handle(CommandRequest req) {
        hub.control(req.deviceId(), req.action(), req.voice(), req.energy(), req.online());
        Map<String,Object> ok = new HashMap<>();
        ok.put("status","ok");
        ok.put("deviceId", req.deviceId());
        if (req.action()!=null) ok.put("action", req.action());
        if (req.voice()!=null) ok.put("voice", req.voice());
        if (req.energy()!=null) ok.put("energy", req.energy());
        if (req.online()!=null) ok.put("online", req.online());
        return gson.toJson(ok);
    }
}
