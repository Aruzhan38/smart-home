package smart_home.core.registry;

import smart_home.device.Device;
import smart_home.device.DeviceType;

import java.util.*;

public class DeviceRegistry {
    private final List<Device> devices = new ArrayList<>();
    private final Map<String, Device> byName = new HashMap<>();
    private final Map<String, DeviceType> typeByName = new HashMap<>();
    private final Map<String, List<Device>> byRoom = new HashMap<>();

    public void add(Device d, DeviceType type, String room) {
        devices.add(d);
        String key = d.getName().toLowerCase(Locale.ROOT);
        byName.put(key, d);
        typeByName.put(key, type);
        byRoom.computeIfAbsent(room.toLowerCase(Locale.ROOT), k -> new ArrayList<>()).add(d);
    }

    public Device get(String name) {
        return byName.get(name.toLowerCase(Locale.ROOT));
    }

    public DeviceType typeOf(String name) {
        return typeByName.get(name.toLowerCase(Locale.ROOT));
    }

    public List<Device> all() {
        return Collections.unmodifiableList(devices);
    }

    public List<Device> inRoom(String room) {
        return byRoom.getOrDefault(room.toLowerCase(Locale.ROOT), List.of());
    }

    public Set<String> rooms() { return byRoom.keySet(); }
}
