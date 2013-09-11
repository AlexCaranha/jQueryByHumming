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
    
    public List<Object> execute(List<MelodyRepresentationNote> melody) {
        List<Object> result = new ArrayList<Object>();
        for(MelodyRepresentationNote note : melody) {
            result.add(note.getPitchInMidi());
        }
        return result;
    }
}
