package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;

/**
 *
 * @author alexcaranha
 */
public abstract class MelodyMatching extends Configuration implements IExecutable {
    public abstract double getCost();

    @Override
    public String getTitle() {
        return "MELODY MATCHING";
    }

    @Override
    public String getAlias() {
        return "MM";
    }
}
