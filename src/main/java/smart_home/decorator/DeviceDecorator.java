package smart_home.decorator;

import smart_home.device.Device;

public abstract class DeviceDecorator implements Device {
    protected final Device decorated;

    protected DeviceDecorator(Device decorated) {
        this.decorated = decorated;
    }

    public String getName() {
        return decorated.getName();
    }

    public void operate(String action) {
        decorated.operate(action);
    }
}
