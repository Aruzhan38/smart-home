package smart_home.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import smart_home.device.Device;
import smart_home.decorator.*;
import smart_home.factory.*;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SmartHomeFacade {
    private final List<Device> devices = new ArrayList<>();

    public void loadDevices(String path) {
        try (FileReader reader = new FileReader(path)) {
            Type type = new TypeToken<List<JsonDevice>>() {}.getType();
            List<JsonDevice> configs = new Gson().fromJson(reader, type);
            for (JsonDevice jd : configs) {
                DeviceFactory factory = jd.brand.equalsIgnoreCase("philips")
                        ? new PhilipsFactory()
                        : new XiaomiFactory();
                Device d = factory.createDevice(jd.type, jd.name);
                for (String deco : jd.decorators) {
                    d = switch (deco.toLowerCase()) {
                        case "energy" -> new EnergySavingDecorator(d);
                        case "voice" -> new VoiceControlDecorator(d);
                        case "remote" -> new RemoteAccessDecorator(d);
                        default -> d;
                    };
                }
                devices.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error loading devices: " + e.getMessage());
        }
    }

    public void loadModes(String path) {
        try (FileReader reader = new FileReader(path)) {
            Type type = new TypeToken<List<JsonMode>>() {}.getType();
            List<JsonMode> modes = new Gson().fromJson(reader, type);
            for (JsonMode m : modes) System.out.println("[Mode loaded] " + m.name);
        } catch (Exception e) {
            System.out.println("Error loading modes: " + e.getMessage());
        }
    }

    public void control(String id, String action) {
        for (Device d : devices)
            if (d.getName().equalsIgnoreCase(id)) d.operate(action);
    }

    public void activateMode(String name) {
        System.out.println("\n[Mode Activated]: " + name);
        for (Device d : devices) {
            if (name.equalsIgnoreCase("night") && d.getName().toLowerCase().contains("light"))
                d.operate("OFF");
            else if (name.equalsIgnoreCase("party") && d.getName().toLowerCase().contains("music"))
                d.operate("PLAY");
            else if (name.equalsIgnoreCase("leavinghome"))
                d.operate("OFF");
        }
    }

    private static class JsonDevice {
        String type;
        String brand;
        String name;
        List<String> decorators;
    }

    private static class JsonMode {
        String name;
    }
}
