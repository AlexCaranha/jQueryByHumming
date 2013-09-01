package com.alexcaranha.jquerybyhumming.screen.database.detail;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author alexcaranha
 */
public class DatabaseTableModel extends AbstractTableModel {

    private List<Database_Detail_Model> listDatabaseDetail;

    public DatabaseTableModel(List<Database_Detail_Model> listDatabaseDetail) {
        this.listDatabaseDetail = listDatabaseDetail;
    }

    public List<Database_Detail_Model> getListDatabaseDetail() {
        return this.listDatabaseDetail;
    }

    @Override
    public int getRowCount() {
        return listDatabaseDetail.size();
    }

    @Override
    public int getColumnCount() {
        if (listDatabaseDetail == null) return 0;
        return 2;
    }

    @Override
    public String getColumnName(int col) {
        switch(col) {
            case 0: return "Title";
            case 1: return "Author";
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        switch(columnIndex) {
            case 0: value = listDatabaseDetail.get(rowIndex).getTitle(); break;
            case 1: value = listDatabaseDetail.get(rowIndex).getAuthor(); break;
        }
        return value;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
