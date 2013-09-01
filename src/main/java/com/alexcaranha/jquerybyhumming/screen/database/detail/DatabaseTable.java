package com.alexcaranha.jquerybyhumming.screen.database.detail;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class DatabaseTable extends JTable {

    private Map<Integer, TableCellEditor> editors;

    public DatabaseTable(DatabaseTableModel model) {
        super(model);
        this.editors = new HashMap<Integer, TableCellEditor>();
    }

    public void addCellEditor(Integer row, TableCellEditor editor) {
        this.editors.put(row, editor);
    }

    public void removeCellEditor(Integer row) {
        this.editors.remove(row);
    }

    public void removeAllCellEditor() {
        this.editors.clear();
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        int modelRow = convertRowIndexToModel(row);

        TableCellEditor editor = this.editors.get(modelRow);

        if (editor == null) {
            return super.getCellEditor(modelRow, column);
        }
        return editor;
    }
}
