package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch;

import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class RelativePitch extends Encoding {
    
    @Override
    public String toString() {
        return "Relative Pitch";
    }
    
    public List<Double> execute(List<MelodyRepresentationNote> melody) {
        List<Double> result = new ArrayList<Double>();
        double currentNote, previousNote;
        
        for(int index = 0; index < melody.size(); index += 1) {
            MelodyRepresentationNote note = melody.get(index);
            currentNote = note.getPitchInMidi();
            if (currentNote == 0.0) continue;
            
            if (index == 0) {
                result.add(0.0);
                continue;
            } else {
                previousNote = melody.get(index).getPitchInMidi();
                result.add(currentNote - previousNote);
            }
        }
        
        return result;
    }
}
