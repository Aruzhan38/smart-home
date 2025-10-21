package smart_home.factory;

import smart_home.device.*;

public class PhilipsFactory implements DeviceFactory {
    @Override
    public Device createDevice(String type, String name) {
        return switch (type.toLowerCase()) {
            case "light" -> new Light(name);
            case "musicsystem" -> new MusicSystem(name);
            case "thermostat" -> new Thermostat(name);
            case "securitycamera" -> new SecurityCamera(name);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
