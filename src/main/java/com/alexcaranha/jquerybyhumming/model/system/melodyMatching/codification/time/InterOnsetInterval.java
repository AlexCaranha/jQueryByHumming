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
public class InterOnsetInterval extends TimeCodification{
    public static String Name = "Inter-Onset-Interval";
    
    public void execute(Map<String, Object> params) throws Exception {
        MelodyRepresentation mr = (MelodyRepresentation) params.get("melodyRepresentation");
        Map<Double, MelodyRepresentationNote> notes = (Map<Double, MelodyRepresentationNote>) mr.getMidiNotes();
        List<MelodyRepresentationNote> list = new ArrayList<MelodyRepresentationNote>(notes.values());
        
        for(int index = 0; index < list.size(); index += 1) {
            MelodyRepresentationNote note = list.get(index);
            if (note.getPitchInMidi() == 0.0) continue;
            
            this.result.add(note.getDuration());
        }
    }
}
