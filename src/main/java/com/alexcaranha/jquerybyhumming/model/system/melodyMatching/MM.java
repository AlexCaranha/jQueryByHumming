package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.AbsolutePitch;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.ParsonsCode;
import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch.RelativePitch;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;

/**
 *
 * @author alexcaranha
 */
public abstract class MM extends Configuration implements IExecutable {
    protected Encoding[] pitchEncodingArray = Util.createArray(new AbsolutePitch(), new RelativePitch(), new ParsonsCode());
    public abstract double getCost();
    
    public MM() {
        /*
        ConfigurationTableItem variable;
        variable = new ConfigurationTableItem<Integer>("N", null, 20, "Indicates the size of the resulting list.");
        this.variables.put("Size of list resulting", variable);
        */ 
    }
    
    protected Encoding getPitchEncoding() {
        Object pitchEncodingObject = (Object)this.variables.get("pitchEncoding").getValue();
        
        if (pitchEncodingObject instanceof Encoding) {
            return (Encoding) pitchEncodingObject;
        }
        
        String pitchEncodingName = pitchEncodingObject.toString();
        for (Encoding encoding : pitchEncodingArray) {
            if (encoding.toString().equalsIgnoreCase(pitchEncodingName)) {
                return encoding;
            }
        }
        return null;
    }
}
