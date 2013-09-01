package com.alexcaranha.jquerybyhumming.screen.configurations;

import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class ConfigurationOnsetDetection extends Configuration {
    public ConfigurationOnsetDetection(Map<String, Configuration> options, String selected) {
        super(options, selected);
    }
    
    @Override
    public String getTitle() {
        return "ONSET DETECTION";
    }

    @Override
    public String getAlias() {
        return "ONSET-DETECTION";
    }
}
