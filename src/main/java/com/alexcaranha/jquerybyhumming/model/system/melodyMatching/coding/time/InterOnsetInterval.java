package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.time;

import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class InterOnsetInterval extends Encoding{
    
    @Override
    public String toString() {
        return "Inter-Onset-Interval";
    }
    
    public List<Object> execute(List<MelodyRepresentationNote> melody) {
        List<Object> result = new ArrayList<Object>();
        for(int index = 0; index < melody.size(); index += 1) {
            MelodyRepresentationNote note = melody.get(index);
            if (note.getPitchInMidi() == 0.0) continue;
            
            result.add(note.getDuration());
        }
        return result;
    }
}
