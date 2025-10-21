package smart_home.core.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DeviceConfigLoader {
    public List<JsonDevice> load(String resourcePath) {
        try (InputStream in = get(resourcePath);
             InputStreamReader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            Type t = new TypeToken<List<JsonDevice>>(){}.getType();
            return new Gson().fromJson(r, t);
        } catch (Exception e) {
            throw new IllegalArgumentException("devices: " + e.getMessage(), e);
        }
    }
    private InputStream get(String p){var in=getClass().getClassLoader().getResourceAsStream(p);
        if(in==null) throw new IllegalArgumentException("not found: "+p);
        return in;}
}
