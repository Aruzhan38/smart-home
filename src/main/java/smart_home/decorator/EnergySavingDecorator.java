package smart_home.decorator;

import smart_home.device.Device;

public class EnergySavingDecorator extends DeviceDecorator {
    private boolean energyMode = false;

    public EnergySavingDecorator(Device d) {
        super(d);
    }

    @Override
    public void operate(String action) {
        if (action.equalsIgnoreCase("ON") && energyMode) {
            System.out.println("[EnergySaving] Reducing brightness or power usage...");
        }
        super.operate(action);
    }

    public void toggleEnergyMode(boolean enabled) {
        this.energyMode = enabled;
        System.out.println("Energy saving mode is now " + (enabled ? "ON" : "OFF"));
    }
}
