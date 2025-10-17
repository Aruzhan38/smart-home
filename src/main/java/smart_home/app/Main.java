package smart_home.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import smart_home.core.SmartHomeFacade;

import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        SmartHomeFacade hub = new SmartHomeFacade();

        hub.loadDevices("src/main/resources/config/devices.json");
        hub.loadModes("src/main/resources/config/modes.json");

        Scanner sc = new Scanner(System.in);
        while (true) {
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            if ("EXIT".equalsIgnoreCase(line)) break;

            try {
                JsonObject json = gson.fromJson(line, JsonObject.class);
                String type = json.has("type") ? json.get("type").getAsString() : "command";

                switch (type) {
                    case "command" -> {
                        String id = json.get("deviceId").getAsString();
                        String action = json.get("action").getAsString();
                        hub.control(id, action);
                    }
                    case "mode" -> hub.activateMode(json.get("name").getAsString());
                    default -> System.out.println("{\"status\":\"error\",\"error\":\"UNKNOWN_TYPE\"}");
                }
            } catch (Exception e) {
                System.out.println("{\"status\":\"error\",\"error\":\"BAD_JSON\"}");
            }
        }
    }
}
