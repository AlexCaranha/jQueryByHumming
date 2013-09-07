package com.alexcaranha.jquerybyhumming.screen.configuration.table;

import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import javax.swing.table.AbstractTableModel;
import org.elasticsearch.common.collect.Iterables;

public class ConfigurationTableModel extends AbstractTableModel {

    public static final String[] headers = {"Field", "Value"};
    private Configuration configuration;

    public ConfigurationTableModel(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public int getRowCount() {
        return configuration.getVariables().size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int col) {
        return headers[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return Iterables.get(configuration.getVariables().values(), rowIndex).getLabel();
        }
        return Iterables.get(configuration.getVariables().values(), rowIndex).getValue();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 1) {
            ConfigurationTableItem item = Iterables.get(configuration.getVariables().values(), row);
            //boolean bolSuccess = false;

            if (classesCompatibles(item.getValue().getClass(), Double.class)){
                if (Util.isDouble(String.valueOf(value))){
                    Double valueItem = Double.valueOf(String.valueOf(value));
                    item.setValue(valueItem);

                    //bolSuccess = true;
                    this.fireTableCellUpdated(row, col);
                }
            }else
            if (classesCompatibles(item.getValue().getClass(), Integer.class)){
                if (Util.isInteger(String.valueOf(value))){
                    Integer valueItem = Integer.valueOf(String.valueOf(value));
                    item.setValue(valueItem);

                    //bolSuccess = true;
                    this.fireTableCellUpdated(row, col);
                }
            }else {
                item.setValue(String.valueOf(value));

                //bolSuccess = true;
                this.fireTableCellUpdated(row, col);
            }

            /*
            if (bolSuccess) {
                try {
                    configuration.save();
                } catch (Exception ex) {
                    Logger.getLogger(ConfigurationTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            */
        }
    }

    private boolean classesCompatibles(Class class1, Class class2) {
        return class1.getCanonicalName().equalsIgnoreCase(class2.getCanonicalName());
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col == 1);
    }
}
