package healthcare.util;

// This class used to generate new IDs for records

import java.util.List;
import java.util.Map;
import java.util.regex.*;

public final class IdGenerator {
    private IdGenerator() {}

    public static String nextId(List<Map<String, String>> rows, String idColumn, String prefix, int width) {
        int max = 0;
        Pattern p = Pattern.compile("^" + Pattern.quote(prefix) + "(\\d+)$");

        for (Map<String, String> r : rows) {
            String id = r.getOrDefault(idColumn, "").trim();
            Matcher m = p.matcher(id);
            if (m.find()) {
                try { max = Math.max(max, Integer.parseInt(m.group(1))); }
                catch (NumberFormatException ignored) {}
            }
        }
        return prefix + String.format("%0" + width + "d", max + 1);
    }
}
