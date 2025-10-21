package smart_home.core.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ModeConfigLoader {
    public Map<String, List<JsonAction>> load(String resourcePath) {
        try (InputStream in = get(resourcePath);
             InputStreamReader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            Type t = new TypeToken<List<JsonMode>>(){}.getType();
            List<JsonMode> modes = new Gson().fromJson(r, t);
            Map<String,List<JsonAction>> map = new HashMap<>();
            if (modes != null) for (JsonMode m : modes)
                map.put(m.name.toLowerCase(Locale.ROOT), m.actions!=null? m.actions: List.of());
            return map;
        } catch (Exception e) {
            throw new IllegalArgumentException("modes: " + e.getMessage(), e);
        }
    }
    private InputStream get(String p){var in=getClass().getClassLoader().getResourceAsStream(p);if(in==null)throw new IllegalArgumentException("not found: "+p);return in;}
}
