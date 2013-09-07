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
public class AbsolutePitch extends PitchCodification {
    public static String Name = "Absolute Pitch";
    
    public void execute(Map<String, Object> params) throws Exception {
        MelodyRepresentation mr = (MelodyRepresentation) params.get("melodyRepresentation");
        Map<Double, MelodyRepresentationNote> notes = (Map<Double, MelodyRepresentationNote>) mr.getMidiNotes();
        List<MelodyRepresentationNote> list = new ArrayList<MelodyRepresentationNote>(notes.values());
                
        for(MelodyRepresentationNote note : list) {
            this.result.add(note.getPitchInMidi());            
        }
    }
}
