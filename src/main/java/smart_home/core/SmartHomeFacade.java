package smart_home.core;

import smart_home.core.assemble.DeviceAssembler;
import smart_home.core.config.DeviceConfigLoader;
import smart_home.core.config.JsonDevice;
import smart_home.core.config.ModeConfigLoader;
import smart_home.core.modes.ModeService;
import smart_home.core.registry.DeviceRegistry;
import smart_home.device.Device;

import java.util.List;
import java.util.Map;

public class SmartHomeFacade {
    private final DeviceRegistry registry = new DeviceRegistry();
    private ModeService modes;

    public void loadDevices(String resourcePath) {
        List<JsonDevice> cfg = new DeviceConfigLoader().load(resourcePath);
        DeviceAssembler asm = new DeviceAssembler();
        if (cfg == null) return;
        for (JsonDevice jd : cfg) {
            Device d = asm.build(jd);
            registry.add(d, jd.type);
        }
        System.out.println("[Devices loaded]: " + registry.all().size());
    }

    public void loadModes(String resourcePath) {
        Map<String, List<smart_home.core.config.JsonAction>> map = new ModeConfigLoader().load(resourcePath);
        modes = new ModeService(map);
        map.forEach((k, v) -> System.out.println("[Mode loaded] " + k.substring(0,1).toUpperCase()+k.substring(1) + " (" + v.size() + " actions)"));
    }

    public void showDevices() {
        System.out.println("\n[Devices in system]:");
        for (Device d : registry.all()) System.out.println(" - " + d.getName());
    }

    public void control(String nameOrId, String action) {
        if (nameOrId == null || action == null) return;
        var d = registry.get(nameOrId);
        if (d == null) {
            System.out.println("Device not found: " + nameOrId);
            return;
        }
        d.operate(action);
    }

    public void activateMode(String name) {
        if (modes == null) {
            System.out.println("Modes not loaded");
            return;
        }
        modes.activate(name, registry);
    }
}
