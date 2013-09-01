package com.alexcaranha.jquerybyhumming.screen.configuration;

import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.util.HashMap;
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
        
        //load();
    }

    public Configuration() {
        this.variables = new HashMap<String, ConfigurationTableItem>();
        this.options = null;
        this.selected = null;
        this.isGroup = false;
        
        //load();
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

    /*
    public void save() throws FileNotFoundException, IOException {
        String path = Util.getDirExecution(this.getAlias() + "-CONFIG.JSON").toLowerCase();
        FileOutputStream fos = new FileOutputStream(new File(path));

        XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        xstream.alias(this.getAlias().toLowerCase(), this.getClass());
        xstream.toXML(this, fos);
        fos.close();
    }

    private void load() {
        String path = Util.getDirExecution(this.getAlias() + "-CONFIG.JSON").toLowerCase();
        if (Util.fileExist(path)) {
            XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
            xstream.alias(this.getAlias().toLowerCase(), this.getClass());
            Configuration configuration = (Configuration) xstream.fromXML(path);

            this.isGroup = configuration.isGroup;
            this.options = configuration.options;
            this.selected = configuration.selected;
            this.variables = configuration.variables;
        }
    }
    */ 

    @Override
    public String toString() {
        return this.getTitle();
    }
}