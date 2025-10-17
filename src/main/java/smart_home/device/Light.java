package smart_home.device;

public class Light implements Device {
    private final String name;

    public Light(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void operate(String action) {
        System.out.println("Light(" + name + ") -> " + action);
    }
}
