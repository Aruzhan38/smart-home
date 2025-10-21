package smart_home.factory;

import smart_home.device.*;

public class XiaomiFactory implements DeviceFactory {
    @Override
    public Device createDevice(String type, String name) {
        return switch (type.toLowerCase()) {
            case "light" -> new Light(name );
            case "thermostat" -> new Thermostat(name);
            case "musicsystem" -> new MusicSystem(name );
            case "securitycamera" -> new SecurityCamera(name );
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}