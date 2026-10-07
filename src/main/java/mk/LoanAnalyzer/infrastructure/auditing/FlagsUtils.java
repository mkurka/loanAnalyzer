package mk.LoanAnalyzer.infrastructure.auditing;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FlagsUtils {
    public static void putToMap(Map<Object, Set<Object>> map, Object key, Object value) {
        Set<Object> values;
        if (map.containsKey(key)) {
            values = map.get(key);
        } else {
            values = new HashSet<>();
            map.put(key, values);
        }
        values.add(value);
    }

    public static void mergeMaps(Map<Object, Set<Object>> destination, Map<Object, Set<Object>> source) {
        if (source == null || source.isEmpty()) {
            return;
        }
        source.entrySet().stream().forEach(e -> {
            Set<Object> values;
            if (destination.containsKey(e.getKey())) {
                values = destination.get(e.getKey());
            } else {
                values = new HashSet<>();
                destination.put(e.getKey(), values);
            }
            values.addAll(e.getValue());
        });

    }

}
