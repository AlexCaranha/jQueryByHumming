package com.alexcaranha.jquerybyhumming.screen.configurations;

import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class ConfigurationMelodyMatching extends Configuration {    
    public ConfigurationMelodyMatching(Map<String, Configuration> options, String selected) {
        super(options, selected);
    }
    
    @Override
    public String getTitle() {
        return "MELODY MATCHING";
    }

    @Override
    public String getAlias() {
        return "MM";
    }
}
