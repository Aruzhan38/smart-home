package smart_home.device;

public class MusicSystem implements Device {
    private final String name;

    public MusicSystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void operate(String action) {
        System.out.println("MusicSystem(" + name + ") -> " + action);
    }
}