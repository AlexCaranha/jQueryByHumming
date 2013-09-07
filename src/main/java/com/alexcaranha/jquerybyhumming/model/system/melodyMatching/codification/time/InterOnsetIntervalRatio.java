package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.codification.time;

import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class InterOnsetIntervalRatio extends TimeCodification{
    public static String Name = "Inter-Onset-Interval Ratio";
    
    public void execute(Map<String, Object> params) throws Exception {
        MelodyRepresentation mr = (MelodyRepresentation) params.get("melodyRepresentation");
        Map<Double, MelodyRepresentationNote> notes = (Map<Double, MelodyRepresentationNote>) mr.getMidiNotes();
        List<MelodyRepresentationNote> list = new ArrayList<MelodyRepresentationNote>(notes.values());
        
        MelodyRepresentationNote currentNote, previousNote;
        double value;
        
        for(int index = 0; index < list.size(); index += 1) {
            currentNote = list.get(index);
            
            if (currentNote.getPitchInMidi() == 0.0) continue;
            
            if (index == 0) {
                this.result.add(1.0);
                continue;
            }
            
            previousNote = list.get(index - 1);
            value = currentNote.getDuration() / previousNote.getDuration();
            this.result.add(value);
        }
    }
}
