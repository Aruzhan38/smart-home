package smart_home.decorator;

import smart_home.device.Device;

public class VoiceControlDecorator extends DeviceDecorator {
    public VoiceControlDecorator(Device d) {
        super(d);
    }

    public void operate(String action) {
        System.out.println("[Voice]");
        super.operate(action);
    }
}
