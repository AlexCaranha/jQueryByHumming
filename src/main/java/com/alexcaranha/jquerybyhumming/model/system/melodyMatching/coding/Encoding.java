package com.alexcaranha.jquerybyhumming.model.system.melodyMatching.coding;

import com.alexcaranha.jquerybyhumming.model.system.melodyRepresentation.MelodyRepresentationNote;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public abstract class Encoding{    
    public abstract List<Object> execute(List<MelodyRepresentationNote> melody);
}
