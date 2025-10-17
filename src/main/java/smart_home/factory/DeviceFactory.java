package smart_home.factory;

import smart_home.device.Device;

public interface DeviceFactory {
    Device createDevice(String type, String name);
}
