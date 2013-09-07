package com.alexcaranha.jquerybyhumming.screen.configurations;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;

/**
 *
 * @author alexcaranha
 */
public class ConfigurationMelodyMatching extends Configuration {
    
    public ConfigurationMelodyMatching(String hostName, int port) {
        super();
        this.variables.put("hostName", new ConfigurationTableItem<String>("hostName", null, hostName, "HostName."));
        this.variables.put("port", new ConfigurationTableItem<Integer>("port", null, port, "Port."));
    }

    public String getHostName() {
        return Convert.toString(this.variables.get("hostName").getValue());
    }

    public int getPort() {
        return Convert.toInteger(this.variables.get("port").getValue());
    }

    @Override
    public String getTitle() {
        return "MELODY MATCHING";
    }

    @Override
    public String getAlias() {
        return "MM";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
}
