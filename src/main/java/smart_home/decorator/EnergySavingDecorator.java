package smart_home.decorator;

import smart_home.device.Device;
import smart_home.device.capabilities.SupportsEnergyMode;

public class EnergySavingDecorator extends DeviceDecorator implements SupportsEnergyMode {
    private boolean energy = false;

    public EnergySavingDecorator(Device d) { super(d); }

    @Override
    public void toggleEnergyMode(boolean on) {
        energy = on;
        System.out.println("[Energy] " + (on ? "ON" : "OFF"));
    }

    @Override
    public void operate(String action) {
        if (energy && action.equalsIgnoreCase("ON")) {
            System.out.println("[Energy] Reducing power/brightness...");
        }
        System.out.println("[EnergySaving]");
        super.operate(action);
    }
}
