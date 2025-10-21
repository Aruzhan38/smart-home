package smart_home.core.modes;

import smart_home.core.config.JsonAction;
import smart_home.core.registry.DeviceRegistry;
import smart_home.device.Device;
import smart_home.device.DeviceType;

import java.util.*;

public class ModeService {
    private final Map<String, List<JsonAction>> modes;

    public ModeService(Map<String, List<JsonAction>> modes) {
        this.modes = modes;
    }

    public void activate(String name, DeviceRegistry reg) {
        if (name == null) return;
        System.out.println("\n[Mode Activated]: " + name);
        List<JsonAction> actions = modes.get(name.toLowerCase(Locale.ROOT));
        if (actions == null) {
            System.out.println("Mode not found: " + name);
            return;
        }
        for (JsonAction a : actions) {
            if (a == null || a.deviceType == null || a.action == null) continue;
            String target = a.deviceType.toLowerCase(Locale.ROOT);
            for (Device d : reg.all()) {
                DeviceType deviceType = reg.typeOf(d.getName());
                if (deviceType != null && deviceType.toString().equalsIgnoreCase(a.deviceType))
                    d.operate(a.action);
            }
        }
    }
}
