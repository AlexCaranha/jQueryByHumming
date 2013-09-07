package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.codification.time;

import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public abstract class TimeCodification implements IExecutable{
    protected List<Double> result;
    
    public TimeCodification() {
        this.result = new ArrayList<Double>();
    }
    
    public List<Double> getResult() {
        return this.result;
    }
}
