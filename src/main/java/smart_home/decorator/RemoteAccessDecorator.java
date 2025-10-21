package smart_home.decorator;

import smart_home.device.Device;
import smart_home.device.capabilities.SupportsRemote;

public class RemoteAccessDecorator extends DeviceDecorator implements SupportsRemote {
    private boolean online = true;

    public RemoteAccessDecorator(Device d) { super(d); }

    @Override
    public void setOnline(boolean on) {
        online = on;
        System.out.println("[Remote] " + (on ? "Online" : "Offline"));
    }

    @Override
    public void operate(String action) {
        if (!online) {
            System.out.println("[Remote] Offline. Skip: " + action);
            return;
        }
        System.out.println("[Remote] Sending...");
        super.operate(action);
    }
}
