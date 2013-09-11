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
    
    public List<Object> execute(List<MelodyRepresentationNote> melody) {
        List<Object> result = new ArrayList<Object>();
        
        MelodyRepresentationNote currentNote, previousNote;
        double currentNoteValue, previousNoteValue;
        
        currentNote = null;
        for(int index = 0; index < melody.size(); index += 1) {
            if (melody.get(index).getPitchInMidi() == 0.0) continue;
            
            previousNote = currentNote;
            currentNote = melody.get(index);
            
            if (previousNote == null) {
                result.add(0.0);
                continue;
            }
            
            previousNoteValue = previousNote.getPitchInMidi();
            currentNoteValue = currentNote.getPitchInMidi();
            
            result.add(currentNoteValue - previousNoteValue);
        }
        
        return result;
    }
}
