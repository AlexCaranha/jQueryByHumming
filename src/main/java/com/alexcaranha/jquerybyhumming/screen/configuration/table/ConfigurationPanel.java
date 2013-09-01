package com.alexcaranha.jquerybyhumming.screen.configuration.table;

import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ConfigurationPanel extends JPanel {
    private Configuration configuration;
    protected JTable table;
    protected JScrollPane scroller;

    public ConfigurationPanel(Configuration configuration) {
        this.configuration = configuration;

        ConfigurationTableModel model = new ConfigurationTableModel(configuration);

        table = new ConfigurationTable(model);

        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));

        setLayout(new BorderLayout());
        this.add(scroller, BorderLayout.CENTER);
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public JTable getTable() {
        return this.table;
    }

    @Override
    public String toString() {
        return configuration.toString();
    }

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
