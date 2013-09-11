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
    private boolean DEBUG = false;
    
    @Override
    public String toString() {
        return "Parson's Code";
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
                result.add("*");
                continue;
            }
            
            previousNoteValue = previousNote.getPitchInMidi();
            currentNoteValue = currentNote.getPitchInMidi();
            
            double dist1 = Math.abs(currentNoteValue - previousNoteValue);
            double dist2 = (currentNoteValue - previousNoteValue);
            
            result.add(dist1 < 1 ? "R" : (dist2 >= 1) ? "U" : "D");
        }
        
        if (DEBUG) {
            System.out.print("Sequência:");
            for (Object item : result) {
                System.out.print(String.format(" %s", item.toString()));
            }
            System.out.println(".");
        }
        
        return result;
    }
}
