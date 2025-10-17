package smart_home.decorator;

import smart_home.device.Device;

public class EnergySavingDecorator extends DeviceDecorator {
    public EnergySavingDecorator(Device d) {
        super(d);
    }

    public void operate(String action) {
        System.out.println("[EnergySaving]");
        super.operate(action);
    }
}