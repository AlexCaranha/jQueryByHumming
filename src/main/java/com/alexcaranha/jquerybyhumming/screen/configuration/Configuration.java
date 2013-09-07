package com.alexcaranha.jquerybyhumming.screen.configuration;

import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import com.thoughtworks.xstream.XStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public abstract class Configuration {
    protected Configuration father;
    
    protected Map<String, ConfigurationTableItem> variables;
    protected Map<String, Configuration> options;
    protected Configuration selected;
    
    protected boolean isGroup;
    
    public Configuration(Map<String, Configuration> options, String selected) {
        this.variables = null;
        this.options = options;
        this.selected = options.get(selected);
        this.isGroup = true;
    }

    public Configuration() {
        this.variables = new HashMap<String, ConfigurationTableItem>();
        this.options = null;
        this.selected = null;
        this.isGroup = false;
    }
    
    public Configuration getSelected() {
        return this.selected;
    }
    
    public boolean isGroup() {
        return this.isGroup;
    }

    public abstract String getTitle();
    public abstract String getAlias();
    
    public Map<String, ConfigurationTableItem> getVariables() {
        return this.variables;
    }
    
    public Map<String, Configuration> getOptions() {
        return this.options;
    }
    
    public void loadLocalConfiguration() throws FileNotFoundException {
        String path = Util.getDirExecution("configuration.xml");
        if (!Util.fileExist(path)) return;
        
        XStream xstream = new XStream();
        List<Configuration> configurations = (List<Configuration>) xstream.fromXML(new FileInputStream(path));
        
        for(Configuration config : configurations) {
            if (config.getAlias().equalsIgnoreCase(this.getAlias())) {
                this.variables = config.variables;
                this.options = config.getOptions();
                this.selected = config.selected;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}