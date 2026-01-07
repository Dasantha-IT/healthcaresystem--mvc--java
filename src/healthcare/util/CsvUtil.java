package healthcare.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class CsvUtil {
    private CsvUtil() {}

    public static List<Map<String, String>> readCsv(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String headerLine = br.readLine();
            if (headerLine == null) return new ArrayList<>();

            List<String> headers = parseLine(headerLine);
            List<Map<String, String>> rows = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                List<String> values = parseLine(line);

                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    row.put(headers.get(i), i < values.size() ? values.get(i) : "");
                }
                rows.add(row);
            }
            return rows;
        }
    }

    public static void writeCsv(File file, List<String> headers, List<Map<String, String>> rows) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            bw.write(String.join(",", headers));
            bw.newLine();
            for (Map<String, String> row : rows) {
                List<String> out = new ArrayList<>();
                for (String h : headers) out.add(escape(row.getOrDefault(h, "")));
                bw.write(String.join(",", out));
                bw.newLine();
            }
        }
    }

    private static List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"'); i++;
                } else inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(cur.toString().trim());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        result.add(cur.toString().trim());
        return result;
    }

    private static String escape(String value) {
        if (value == null) return "";
        boolean needsQuotes = value.contains(",") || value.contains("\"") || value.contains("\n");
        String v = value.replace("\"", "\"\"");
        return needsQuotes ? "\"" + v + "\"" : v;
    }
}
