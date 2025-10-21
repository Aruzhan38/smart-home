package smart_home.device;

public enum DeviceType {
    LIGHT,
    THERMOSTAT,
    MUSICSYSTEM,
    SECURITYCAMERA;

    public static DeviceType from(String raw) {
        if (raw == null) return null;
        return switch (raw.toLowerCase().replaceAll("\\s+", "")) {
            case "light" -> LIGHT;
            case "thermostat" -> THERMOSTAT;
            case "musicsystem", "music_system" -> MUSICSYSTEM;
            case "securitycamera", "camera", "security_camera" -> SECURITYCAMERA;
            default -> throw new IllegalArgumentException("Unknown device type: " + raw);
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
