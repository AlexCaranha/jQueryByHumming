package com.alexcaranha.jquerybyhumming.screen.database.detail;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DatabasePanel extends JPanel {

    private List<Database_Detail_Model> listDatabaseDetail;
    protected JTable table;
    protected JScrollPane scroller;

    public DatabasePanel(List<Database_Detail_Model> listDatabaseDetail) {

        this.listDatabaseDetail = listDatabaseDetail;

        DatabaseTableModel model = new DatabaseTableModel(listDatabaseDetail);

        table = new DatabaseTable(model);

        if (listDatabaseDetail.size() > 0) table.setRowSelectionInterval(0, 0);

        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));

        setLayout(new BorderLayout());
        this.add(scroller, BorderLayout.CENTER);
    }

    public List<Database_Detail_Model> getListDatabaseDetail() {
        return this.listDatabaseDetail;
    }

    public JTable getTable() {
        return this.table;
    }
    /*
    @Override
    public String toString() {
        return configuration.toString();
    }
    */

    /*
    public static void main(String[] args) throws FileNotFoundException {

        PitchTracking pt = new PitchTracking_ACF();
        pt.loadFromXML();

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            JFrame frame = new JFrame("Interactive Form");
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);}
            });
            frame.getContentPane().add(new ConfigurationPanel(pt));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
