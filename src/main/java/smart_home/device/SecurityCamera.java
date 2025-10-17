package smart_home.device;

public class SecurityCamera implements Device {
    private final String name;

    public SecurityCamera(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void operate(String action) {
        System.out.println("SecurityCamera(" + name + ") -> " + action);
    }
}

