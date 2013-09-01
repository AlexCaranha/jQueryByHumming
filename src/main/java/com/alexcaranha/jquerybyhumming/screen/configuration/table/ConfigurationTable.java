package com.alexcaranha.jquerybyhumming.screen.configuration.table;

import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import org.elasticsearch.common.collect.Iterables;

public class ConfigurationTable extends JTable {

    private Map<Integer, TableCellEditor> editors;

    public ConfigurationTable(ConfigurationTableModel model) {
        super(model);
        this.editors = new HashMap<Integer, TableCellEditor>();
        configureEditors(model);
    }

    private void configureEditors(ConfigurationTableModel model) {

        Map<String, ConfigurationTableItem> variables = model.getConfiguration().getVariables();
        if (variables == null) return;
        Collection<ConfigurationTableItem> items = variables.values();

        int index = -1;
        for (ConfigurationTableItem item : items) {
            index += 1;
            Object[] options = item.getOptions();

            if (options == null) {
                this.addCellEditor(index, null);
            }else{
                this.addCellEditor(index, new DefaultCellEditor(new JComboBox(options)) {});
            }
        }
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

    @Override
    public String getToolTipText(MouseEvent e) {
        String hint = "";
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int realRowIndex = convertRowIndexToModel(rowIndex);

        ConfigurationTableModel model = (ConfigurationTableModel) this.getModel();
        ConfigurationTableItem item = Iterables.get(model.getConfiguration().getVariables().values(), realRowIndex);

        if (item != null) {
            hint = item.getDescription();
        }

        return hint;
    }
}
