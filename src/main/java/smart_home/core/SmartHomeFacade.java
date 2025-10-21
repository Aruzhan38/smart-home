package smart_home.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import smart_home.device.Device;
import smart_home.decorator.EnergySavingDecorator;
import smart_home.decorator.RemoteAccessDecorator;
import smart_home.decorator.VoiceControlDecorator;
import smart_home.factory.DeviceFactory;
import smart_home.factory.PhilipsFactory;
import smart_home.factory.XiaomiFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SmartHomeFacade {
    private final List<Device> devices = new ArrayList<>();
    private final Map<String, Device> byName = new HashMap<>();
    private final Map<String, String> typesByName = new HashMap<>();
    private final Map<String, List<JsonAction>> modesByName = new HashMap<>();

    public void loadDevices(String resourcePath) {
        try (InputStream in = getRequiredResource(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<JsonDevice>>() {}.getType();
            List<JsonDevice> configs = new Gson().fromJson(reader, listType);
            if (configs == null) return;
            for (JsonDevice jd : configs) {
                String brand = safeLower(jd.brand);
                String type = safeLower(jd.type);
                String name = Objects.requireNonNull(jd.name);
                DeviceFactory factory = "philips".equals(brand) ? new PhilipsFactory() : new XiaomiFactory();
                Device d = factory.createDevice(type, name);
                if (jd.decorators != null) {
                    for (String deco : jd.decorators) {
                        String tag = safeLower(deco);
                        d = switch (tag) {
                            case "energy" -> new EnergySavingDecorator(d);
                            case "voice" -> new VoiceControlDecorator(d);
                            case "remote" -> new RemoteAccessDecorator(d);
                            default -> d;
                        };
                    }
                }
                devices.add(d);
                byName.put(name.toLowerCase(Locale.ROOT), d);
                typesByName.put(name.toLowerCase(Locale.ROOT), type);
            }
            System.out.println("[Devices loaded]: " + devices.size());
        } catch (Exception e) {
            System.out.println("Error loading devices: " + e.getMessage());
        }
    }

    public void loadModes(String resourcePath) {
        try (InputStream in = getRequiredResource(resourcePath);
             InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            Type listType = new TypeToken<List<JsonMode>>() {}.getType();
            List<JsonMode> modes = new Gson().fromJson(reader, listType);
            if (modes == null) return;
            for (JsonMode m : modes) {
                if (m == null || m.name == null) continue;
                List<JsonAction> actions = (m.actions != null) ? m.actions : List.of();
                modesByName.put(m.name.toLowerCase(Locale.ROOT), actions);
                System.out.println("[Mode loaded] " + m.name + " (" + actions.size() + " actions)");
            }
        } catch (Exception e) {
            System.out.println("Error loading modes: " + e.getMessage());
        }
    }

    public void showDevices() {
        System.out.println("\n[Devices in system]:");
        for (Device d : devices) {
            System.out.println(" - " + d.getName());
        }
    }

    public void control(String nameOrId, String action) {
        if (nameOrId == null || action == null) return;
        Device d = byName.get(nameOrId.toLowerCase(Locale.ROOT));
        if (d == null) {
            System.out.println("Device not found: " + nameOrId);
            return;
        }
        d.operate(action);
    }

    public void activateMode(String name) {
        if (name == null) return;
        System.out.println("\n[Mode Activated]: " + name);
        List<JsonAction> actions = modesByName.get(name.toLowerCase(Locale.ROOT));
        if (actions == null) {
            System.out.println("Mode not found: " + name);
            return;
        }
        for (JsonAction a : actions) {
            if (a == null || a.deviceType == null || a.action == null) continue;
            String targetType = a.deviceType.toLowerCase(Locale.ROOT);
            for (Device d : devices) {
                String deviceType = typesByName.getOrDefault(d.getName().toLowerCase(Locale.ROOT), "");
                if (targetType.equals(deviceType)) d.operate(a.action);
            }
        }
    }

    private static InputStream getRequiredResource(String resourcePath) {
        InputStream in = SmartHomeFacade.class.getClassLoader().getResourceAsStream(resourcePath);
        if (in == null) throw new IllegalArgumentException("Resource not found: " + resourcePath);
        return in;
    }

    private static String safeLower(String s) {
        return (s == null) ? "" : s.toLowerCase(Locale.ROOT);
    }

    private static class JsonDevice {
        String type;
        String brand;
        String name;
        List<String> decorators;
    }

    private static class JsonMode {
        String name;
        List<JsonAction> actions;
    }

    private static class JsonAction {
        String deviceType;
        String action;
    }
}
