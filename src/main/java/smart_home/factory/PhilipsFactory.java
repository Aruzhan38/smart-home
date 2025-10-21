package smart_home.factory;

import smart_home.device.*;

public class PhilipsFactory implements DeviceFactory {
    @Override
    public Device createDevice(DeviceType type, String name) {
        return switch (type) {
            case LIGHT -> new Light(name);
            case MUSICSYSTEM -> new MusicSystem(name);
            case THERMOSTAT -> new Thermostat(name);
            case SECURITYCAMERA -> new SecurityCamera(name);
        };
    }
}
