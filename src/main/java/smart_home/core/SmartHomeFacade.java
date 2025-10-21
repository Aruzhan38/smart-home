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

    private static <T> T findCapability(smart_home.device.Device d, Class<T> cap) {
        var cur = d;
        while (true) {
            if (cap.isInstance(cur)) return cap.cast(cur);
            if (cur instanceof smart_home.decorator.DeviceDecorator dec) cur = dec.wrapped();
            else return null;
        }
    }

    public void control(String id, String action) {
        control(id, action, null, null, null);
    }

    public void control(String id, String action, String voice, Boolean energy, Boolean online) {
        if (id == null) return;
        var d = registry.get(id);
        if (d == null) {
            System.out.println("Device not found: " + id);
            return;
        }
        if (voice != null) {
            var v = findCapability(d, smart_home.device.capabilities.SupportsVoice.class);
            if (v != null) v.voiceCommand(voice);
            else System.out.println("Voice not supported by " + id);
            return;
        }
        if (energy != null) {
            var e = findCapability(d, smart_home.device.capabilities.SupportsEnergyMode.class);
            if (e != null) e.toggleEnergyMode(energy);
            else System.out.println("Energy mode not supported by " + id);
            return;
        }
        if (online != null) {
            var r = findCapability(d, smart_home.device.capabilities.SupportsRemote.class);
            if (r != null) r.setOnline(online);
            else System.out.println("Remote not supported by " + id);
            return;
        }
        if (action != null) d.operate(action);
    }

    public void activateMode(String name) {
        if (modes == null) {
            System.out.println("Modes not loaded");
            return;
        }
        modes.activate(name, registry);
    }
}
