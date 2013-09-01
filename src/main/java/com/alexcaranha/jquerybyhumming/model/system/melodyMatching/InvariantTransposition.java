package com.alexcaranha.jquerybyhumming.model.system.melodyMatching;

import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentation;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class InvariantTransposition implements IExecutable{
    private List<Integer> result;
    
    public InvariantTransposition() {
        this.result = new ArrayList<Integer>();
    }
    
    public List<Integer> getResult() {
        return this.result;
    }

    public void execute(Map<String, Object> params) throws Exception {
        MelodyRepresentation mr = (MelodyRepresentation) params.get("melodyRepresentation");
        Map<Double, MelodyRepresentationNote> notes = (Map<Double, MelodyRepresentationNote>) mr.getMidiNotes();
        List<MelodyRepresentationNote> list = new ArrayList<MelodyRepresentationNote>(notes.values());
        
        Integer previousNote;
        Integer currentNote = null;
        double keyNote;
        
        for(MelodyRepresentationNote note : list) {
            keyNote = note.getPitch(true);
            if (keyNote == 0.0) continue;
            
            previousNote = currentNote;            
            currentNote = (int)keyNote;
            
            if (previousNote == null) continue;
            
            int variation = currentNote - previousNote;
            this.result.add(variation);            
        }
    }
}
