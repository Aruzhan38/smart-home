package smart_home.factory;

import smart_home.device.Device;
import smart_home.device.DeviceType;

public interface DeviceFactory {
    Device createDevice(DeviceType type, String name);
}
