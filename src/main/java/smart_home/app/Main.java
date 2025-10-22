package smart_home.app;

import smart_home.app.exception.ExitRequestException;
import smart_home.app.exception.InvalidRequestException;
import smart_home.app.exception.UnknownRequestTypeException;
import smart_home.app.handler.CommandHandler;
import smart_home.app.handler.ModeHandler;
import smart_home.app.handler.RoomHandler;
import smart_home.core.SmartHomeFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;

public class Main {
    private static final String CFG_DEVICES = "config/devices.json";
    private static final String CFG_MODES   = "config/modes.json";

    public static void main(String[] args) {
        SmartHomeFacade hub = new SmartHomeFacade();
        hub.loadDevices(CFG_DEVICES);
        hub.loadModes(CFG_MODES);
        hub.showDevices();
        hub.showRooms();

        RequestParser parser = new RequestParser();
        CommandHandler command = new CommandHandler(hub);
        ModeHandler mode = new ModeHandler(hub);
        RoomHandler room = new RoomHandler(hub);
        Gson gson = new Gson();

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                try {
                    Request req = parser.parse(line);
                    String out = switch (req.type()) {
                        case COMMAND -> command.handle((CommandRequest) req);
                        case MODE    -> mode.handle((ModeRequest) req);
                        case ROOM    -> room.handle((RoomRequest) req);
                    };
                    System.out.println(out);
                } catch (ExitRequestException e) {
                    break;
                } catch (InvalidRequestException e) {
                    System.out.println(error(gson, e.getMessage()));
                } catch (UnknownRequestTypeException e) {
                    System.out.println(error(gson, "UNKNOWN_TYPE"));
                } catch (Exception e) {
                    System.out.println(error(gson, "BAD_JSON"));
                }
            }
        }
    }

    private static String error(Gson gson, String code) {
        Map<String,String> m = new HashMap<>();
        m.put("status","error");
        m.put("error", code);
        return gson.toJson(m);
    }
}
