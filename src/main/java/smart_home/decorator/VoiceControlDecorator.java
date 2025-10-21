package smart_home.decorator;

import smart_home.device.Device;
import smart_home.device.capabilities.SupportsVoice;

public class VoiceControlDecorator extends DeviceDecorator implements SupportsVoice {
    public VoiceControlDecorator(Device d) { super(d); }

    @Override
    public void voiceCommand(String phrase) {
        System.out.println("[Voice] " + phrase);
        String mapped = switch (phrase.toLowerCase()) {
            case "turn on"     -> "ON";
            case "turn off"    -> "OFF";
            case "play music"  -> "PLAY";
            case "stop music"  -> "STOP";
            case "record"      -> "RECORD";
            default            -> null;
        };
        if (mapped != null) super.operate(mapped);
        else System.out.println("[Voice] Unknown phrase");
    }

    @Override
    public void operate(String action) {
        System.out.println("[Voice]");
        super.operate(action);
    }
}
