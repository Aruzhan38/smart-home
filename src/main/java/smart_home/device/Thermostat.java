package smart_home.device;

public class Thermostat implements Device {
    private final String name;

    public Thermostat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void operate(String action) {
        System.out.println("Thermostat(" + name + ") -> " + action);
    }
}