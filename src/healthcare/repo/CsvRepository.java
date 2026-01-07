package healthcare.repo;

import healthcare.util.CsvUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CsvRepository {
    private final File file;
    private final List<String> headers;
    private final List<Map<String, String>> rows = new ArrayList<>();

    public CsvRepository(File file, List<String> headers) {
        this.file = file;
        this.headers = new ArrayList<>(headers);
    }

    public List<String> getHeaders() { return Collections.unmodifiableList(headers); }
    public List<Map<String, String>> getRows() { return rows; }

    public void load() throws IOException {
        rows.clear();
        if (!file.exists()) {
            CsvUtil.writeCsv(file, headers, rows);
            return;
        }
        rows.addAll(CsvUtil.readCsv(file));
    }

    public void save() throws IOException {
        CsvUtil.writeCsv(file, headers, rows);
    }

    public void add(Map<String, String> row) { rows.add(new LinkedHashMap<>(row)); }
    public void update(int index, Map<String, String> row) { rows.set(index, new LinkedHashMap<>(row)); }
    public void delete(int index) { rows.remove(index); }
}
