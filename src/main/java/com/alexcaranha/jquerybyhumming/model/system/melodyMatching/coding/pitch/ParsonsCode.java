package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.pitch;

import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class ParsonsCode extends Encoding {
    
    @Override
    public String toString() {
        return "Parson's Code";
    }
    
    public List<Double> execute(List<MelodyRepresentationNote> melody) {
        List<Double> result = new ArrayList<Double>();
        double currentNote, previousNote;
        
        for(int index = 1; index < melody.size(); index += 1) {
            MelodyRepresentationNote note = melody.get(index);
            currentNote = note.getPitchInMidi();
            if (currentNote == 0.0) continue;
            
            previousNote = melody.get(index).getPitchInMidi();
            result.add(currentNote == previousNote 
                            ? 0.0 
                            : currentNote > previousNote 
                                ? +1.0 
                                : -1.0);
        }
        
        return result;
    }
}
