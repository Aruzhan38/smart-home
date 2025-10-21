package smart_home.decorator;

import smart_home.device.Device;

import java.util.HashMap;
import java.util.Map;

public class VoiceControlDecorator extends DeviceDecorator {
    private final Map<String, String> voiceCommands = new HashMap<>();

    public VoiceControlDecorator(Device d) {
        super(d);
        voiceCommands.put("turn on", "ON");
        voiceCommands.put("turn off", "OFF");
        voiceCommands.put("play music", "PLAY");
        voiceCommands.put("stop music", "STOP");
    }

    public void voiceCommand(String command) {
        String action = voiceCommands.get(command.toLowerCase());
        if (action != null) {
            System.out.println("[Voice] Recognized: " + command);
            super.operate(action);
        } else {
            System.out.println("[Voice] Unknown command: " + command);
        }
    }

    @Override
    public void operate(String action) {
        System.out.println("[VoiceControl]");
        super.operate(action);
    }
}
