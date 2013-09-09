package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch;

import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class AbsolutePitch extends Encoding {
    
    @Override
    public String toString() {
        return "Absolute Pitch";
    }
    
    public List<Double> execute(List<MelodyRepresentationNote> melody) {
        List<Double> result = new ArrayList<Double>();
        for(MelodyRepresentationNote note : melody) {
            result.add(note.getPitchInMidi());
        }
        return result;
    }
}
