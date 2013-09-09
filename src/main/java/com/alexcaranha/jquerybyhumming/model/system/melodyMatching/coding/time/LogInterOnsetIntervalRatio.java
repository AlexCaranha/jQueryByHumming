package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.time;

import com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding.Encoding;
import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class LogInterOnsetIntervalRatio extends Encoding{
    
    @Override
    public String toString() {
        return "Log Inter-Onset-Interval Ratio";
    }
    
    public List<Double> execute(List<MelodyRepresentationNote> melody) {
        List<Double> result = new ArrayList<Double>();
        MelodyRepresentationNote currentNote, previousNote;
        double value;
        
        for(int index = 0; index < melody.size(); index += 1) {
            currentNote = melody.get(index);
            
            if (currentNote.getPitchInMidi() == 0.0) continue;
            
            if (index == 0) {
                result.add(1.0);
                continue;
            }
            
            previousNote = melody.get(index - 1);
            value = Math.log(currentNote.getDuration() / previousNote.getDuration());
            result.add(value);
        }
        return result;
    }
}
