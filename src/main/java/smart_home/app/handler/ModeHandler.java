package smart_home.app.handler;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import smart_home.app.ModeRequest;
import smart_home.core.SmartHomeFacade;

public class ModeHandler {
    private final SmartHomeFacade hub;
    private final Gson gson = new Gson();

    public ModeHandler(SmartHomeFacade hub) { this.hub = hub; }

    public String handle(ModeRequest req) {
        hub.activateMode(req.name());
        Map<String,Object> ok = new HashMap<>();
        ok.put("status","ok");
        ok.put("mode", req.name());
        return gson.toJson(ok);
    }
}
