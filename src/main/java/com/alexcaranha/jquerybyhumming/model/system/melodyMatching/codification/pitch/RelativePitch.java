package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.codification.pitch;

import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class RelativePitch extends PitchCodification {
    public static String Name = "Relative Pitch";

    public void execute(Map<String, Object> params) throws Exception {
        MelodyRepresentation mr = (MelodyRepresentation) params.get("melodyRepresentation");
        Map<Double, MelodyRepresentationNote> notes = (Map<Double, MelodyRepresentationNote>) mr.getMidiNotes();
        List<MelodyRepresentationNote> list = new ArrayList<MelodyRepresentationNote>(notes.values());
        
        double currentNote, previousNote;
        
        for(int index = 0; index < list.size(); index += 1) {
            MelodyRepresentationNote note = list.get(index);
            currentNote = note.getPitchInMidi();
            if (currentNote == 0.0) continue;
            
            if (index == 0) {
                this.result.add(0.0);
                continue;
            } else {
                previousNote = list.get(index).getPitchInMidi();
                this.result.add(currentNote - previousNote);
            }
        }
    }
}
