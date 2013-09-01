package com.alexcaranha.jquerybyhumming.database;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;

/**
 *
 * @author alexcaranha
 */
public class ConfigurationBD extends Configuration {
    public ConfigurationBD(boolean local, String hostName, int port) {
        super();
        this.variables.put("hostName", new ConfigurationTableItem<String>("hostName", null, hostName, "HostName."));
        this.variables.put("port", new ConfigurationTableItem<Integer>("port", null, port, "Port."));
    }
    
    public Boolean isLocal() {
        return Convert.toBoolean(this.variables.get("local").getValue());
    }

    public String getHostName() {
        return Convert.toString(this.variables.get("hostName").getValue());
    }

    public int getPort() {
        return Convert.toInteger(this.variables.get("port").getValue());
    }

    @Override
    public String getTitle() {
        return "DATABASE";
    }

    @Override
    public String getAlias() {
        return "DATABASE";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
}
