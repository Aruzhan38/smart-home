package smart_home.decorator;

import smart_home.device.Device;

public class RemoteAccessDecorator extends DeviceDecorator {
    private boolean online = true;

    public RemoteAccessDecorator(Device d) {
        super(d);
    }

    @Override
    public void operate(String action) {
        if (!online) {
            System.out.println("[Remote] Device is offline, cannot perform: " + action);
            return;
        }
        System.out.println("[Remote] Sending command over the network...");
        super.operate(action);
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
