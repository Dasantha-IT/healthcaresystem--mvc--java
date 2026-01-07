package healthcare.view;

import healthcare.repo.CsvRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TablePanel extends JPanel {
    private final JTable table;
    private final RepoTableModel model;

    public TablePanel(CsvRepository repo) {
        super(new BorderLayout());
        this.model = new RepoTableModel(repo);
        this.table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public int getSelectedRowIndex() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) return -1;
        return table.convertRowIndexToModel(viewRow);
    }

    public void refresh() { model.fireTableStructureChanged(); }

    private static class RepoTableModel extends AbstractTableModel {
        private final CsvRepository repo;
        RepoTableModel(CsvRepository repo) { this.repo = repo; }

        @Override public int getRowCount() { return repo.getRows().size(); }
        @Override public int getColumnCount() { return repo.getHeaders().size(); }
        @Override public String getColumnName(int col) { return repo.getHeaders().get(col); }

        @Override
        public Object getValueAt(int row, int col) {
            List<Map<String,String>> rows = repo.getRows();
            String key = repo.getHeaders().get(col);
            return rows.get(row).getOrDefault(key, "");
        }
    }
}
