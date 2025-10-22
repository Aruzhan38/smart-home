package smart_home.core.util;

import smart_home.device.Device;
import smart_home.decorator.DeviceDecorator;

public final class Capabilities {
    private Capabilities() {}

    public static <T> T find(Device device, Class<T> capability) {
        Device cur = device;
        while (true) {
            if (capability.isInstance(cur)) return capability.cast(cur);
            if (cur instanceof DeviceDecorator dec) cur = dec.wrapped();
            else return null;
        }
    }
}
