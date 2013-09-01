package com.alexcaranha.jquerybyhumming.screen.configurations;

import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class ConfigurationPitchTracking extends Configuration {
    public ConfigurationPitchTracking(Map<String, Configuration> options, String selected) {
        super(options, selected);
    }
    
    @Override
    public String getTitle() {
        return "PITCH TRACKING";
    }

    @Override
    public String getAlias() {
        return "PITCH-TRACKING";
    }
}
