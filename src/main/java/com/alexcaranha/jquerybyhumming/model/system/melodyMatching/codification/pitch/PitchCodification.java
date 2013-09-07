package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.codification.pitch;

import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author alexcaranha
 */
public abstract class PitchCodification implements IExecutable{
    protected List<Double> result;
    
    public PitchCodification() {
        this.result = new ArrayList<Double>();
    }
    
    public List<Double> getResult() {
        return this.result;
    }
}
