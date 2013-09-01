package com.alexcaranha.jquerybyhumming.screen.search.main;

import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.mvp.IModel;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class Search_Main_Model implements IModel {
    private Map<String, Object>     variables;

    public Search_Main_Model() throws Exception {
        this.variables = new HashMap<String, Object>();

        WavSignal signal = new WavSignal();

        String path = Constants.PATH_TMP_WAVE_FILE_SEARCH;
        if (Util.fileExist(path)) {
            FileInputStream fileInputStream = new FileInputStream(path);
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", fileInputStream)));
        }
        this.variables.put("wavSignal", signal);
        this.variables.put("processing", null);
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public Object getVariable(String name) {
        return this.variables.get(name);
    }
    
    public void setVariable(String name, Object value) {
        this.variables.put(name, value);
    }
}
