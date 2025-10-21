package smart_home.core.assemble;

import smart_home.core.config.JsonDevice;
import smart_home.decorator.EnergySavingDecorator;
import smart_home.decorator.RemoteAccessDecorator;
import smart_home.decorator.VoiceControlDecorator;
import smart_home.device.Device;
import smart_home.device.DeviceType;
import smart_home.factory.*;

import java.util.Locale;
import java.util.Objects;

public class DeviceAssembler {
    public Device build(JsonDevice jd) {
        String brand = low(jd.brand);
        DeviceType type = jd.type;
        String name  = Objects.requireNonNull(jd.name);
        DeviceFactory f = "philips".equals(brand) ? new PhilipsFactory() : new XiaomiFactory();
        Device d = f.createDevice(type, name);
        if (jd.decorators != null) {
            for (String deco : jd.decorators) {
                switch (low(deco)) {
                    case "energy" -> d = new EnergySavingDecorator(d);
                    case "voice"  -> d = new VoiceControlDecorator(d);
                    case "remote" -> d = new RemoteAccessDecorator(d);
                }
            }
        }
        return d;
    }
    private static String low(String s){return s==null? "": s.toLowerCase(Locale.ROOT);}
}
