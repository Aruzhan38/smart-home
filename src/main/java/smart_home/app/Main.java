package smart_home.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import smart_home.core.SmartHomeFacade;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        SmartHomeFacade hub = new SmartHomeFacade();

        hub.loadDevices("config/devices.json");
        hub.loadModes("config/modes.json");
        hub.showDevices();

        Scanner sc = new Scanner(System.in);
        while (true) {
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            if ("EXIT".equalsIgnoreCase(line)) break;

            try {
                JsonObject json = gson.fromJson(line, JsonObject.class);
                if (json == null || !json.has("type")) {
                    System.out.println("{\"status\":\"error\",\"error\":\"BAD_JSON_OR_NO_TYPE\"}");
                    continue;
                }

                String type = json.get("type").getAsString();

                switch (type.toLowerCase()) {
                    case "command" -> {
                        if (!json.has("deviceId") || !json.has("action")) {
                            System.out.println("{\"status\":\"error\",\"error\":\"MISSING_FIELDS\"}");
                            continue;
                        }
                        String id = json.get("deviceId").getAsString();
                        String action = json.get("action").getAsString();
                        hub.control(id, action);
                        System.out.println("{\"status\":\"ok\",\"deviceId\":\"" + id + "\",\"action\":\"" + action + "\"}");
                    }
                    case "mode" -> {
                        if (!json.has("name")) {
                            System.out.println("{\"status\":\"error\",\"error\":\"MISSING_MODE_NAME\"}");
                            continue;
                        }
                        String name = json.get("name").getAsString();
                        hub.activateMode(name);
                        System.out.println("{\"status\":\"ok\",\"mode\":\"" + name + "\"}");
                    }
                    default -> System.out.println("{\"status\":\"error\",\"error\":\"UNKNOWN_TYPE\"}");
                }
            } catch (Exception e) {
                System.out.println("{\"status\":\"error\",\"error\":\"BAD_JSON\"}");
            }
        }
    }
}
