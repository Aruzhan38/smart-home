package smart_home.decorator;

import smart_home.device.Device;

public class RemoteAccessDecorator extends DeviceDecorator {
    public RemoteAccessDecorator(Device d) {
        super(d);
    }

    public void operate(String action) {
        System.out.println("[Remote]");
        super.operate(action);
    }
}
