package com.alexcaranha.jquerybyhumming.screen.configuration;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import com.alexcaranha.jquerybyhumming.mvp.IPresenter;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationPanel;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 *
 * @author alexcaranha
 */
public class Configuration_Presenter implements IPresenter {

    private List<Configuration> configurations;
    private int                 indexCurrentPresenter;

    private Configuration_View  view;
    
    private final String[] configs = {"database", "pitchTracking", "onsetDetection", "melodyMatching"};

    public Configuration_Presenter() throws IOException {
        //----------------------------------------------------------------------
        configurations = new ArrayList<Configuration>();
        //----------------------------------------------------------------------
        for(String name : configs) {
            configurations.add(App.getConfiguration(name));
        }
        //----------------------------------------------------------------------
        indexCurrentPresenter = 0;
        view = new Configuration_View(this);
        //----------------------------------------------------------------------
        load();
        //----------------------------------------------------------------------
    }
    
    public IModel getModel() {
        return null;
    }

    public boolean indexIsTheFirst() {
        return indexCurrentPresenter == 0;
    }

    public boolean indexIsTheLast() {
        return indexCurrentPresenter == configurations.size() - 1;
    }

    public int getIndexCurrentConfiguration() {
        return indexCurrentPresenter;
    }

    public ConfigurationPanel getCurrentConfigurationPanel() {
        Configuration configuration = this.configurations.get(indexCurrentPresenter);
        
        if (!configuration.isGroup) {
            return new ConfigurationPanel(configuration);
        }
        
        return new ConfigurationPanel(configuration.getSelected());
    }

    public Configuration getCurrentConfiguration() {
        return this.configurations.get(indexCurrentPresenter);
    }
    
    public void fillComboOptions(JComboBox<Configuration> comboOptions) {
        comboOptions.setEditable(true);
        
        Configuration configuration = getCurrentConfiguration();
        
        comboOptions.removeAllItems();
        for (Configuration item : configuration.getOptions().values()) {
            comboOptions.addItem(item);
        }
        comboOptions.setSelectedItem(configuration.getSelected());
        
        comboOptions.setEditable(false);
    }
    
    public void setSelected(Configuration configuration) throws FileNotFoundException, IOException {
        Configuration configurationActual = this.getCurrentConfiguration();
        
        configurationActual.selected = configuration;
        //configurationActual.save();
        
        view.updateTable();
    }

    public JComponent getView() {
        return this.view;
    }

    public void backward() {
        indexCurrentPresenter -= 1;
        view.update();
    }

    public void forward() {
        indexCurrentPresenter += 1;
        view.update();
    }

    public void save() throws FileNotFoundException, IOException {
        String path = Util.getDirExecution("configuration.xml");
        File file = new File(path);
        
        if (!file.exists()) file.createNewFile();
        
        FileOutputStream fos = new FileOutputStream(file);
        
        XStream xstream = new XStream();
        xstream.toXML(this.configurations, fos);
        fos.close();
    }

    public void load() throws FileNotFoundException {
        //----------------------------------------------------------------------
        configurations.clear();
        for(String name : configs) {
            configurations.add(App.reloadConfiguration(name));
        }
        //----------------------------------------------------------------------
        String path = Util.getDirExecution("configuration.xml");
        
        if (Util.fileExist(path)) {
            XStream xstream = new XStream();
            this.configurations = (List<Configuration>) xstream.fromXML(new FileInputStream(path));
        } else {            
            App.loadConfigurations();
        }
        //----------------------------------------------------------------------
        view.update();
        //----------------------------------------------------------------------
    }
}
