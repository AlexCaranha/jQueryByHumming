package com.alexcaranha.jquerybyhumming.testes;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import static com.lowagie.text.SpecialSymbol.index;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public class GenerateFigureWavs {
    public static void main(String[] args) throws IOException, Exception {
        
        Map<String, Object>     map;
        WavSignal               ws;
        //----------------------------------------------------------------------
        map = new HashMap();
        ws = new WavSignal();
        
        map.put("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_solfejo.wav").getInputStream());
        map.put("normalized", true);
        ws.loadFromWavFile(map);                
        
        Figure.save("", "tempo (s)", "amplitude", false, 
                    Util.createArray(ws.getSignalXY().convertToXYSeries("signal")), 
                    Util.createArray(Color.BLACK), null,
                    new KeyValue<Double, Double>(0.0, 13.0), new KeyValue<Double, Double>(-1.0, +1.0),
                    new Figure(400, 150),
                    Util.getDirExecution("parabens_solfejo.pdf"),
                    Util.getDirExecution("parabens_solfejo.png"));
        //----------------------------------------------------------------------
        map = new HashMap();
        ws = new WavSignal();
        
        map.put("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream());
        map.put("normalized", true);
        ws.loadFromWavFile(map);                
        
        Figure.save("", "tempo (s)", "amplitude", false, 
                    Util.createArray(ws.getSignalXY().convertToXYSeries("signal")), 
                    Util.createArray(Color.BLACK), null,
                    new KeyValue<Double, Double>(0.0, 13.0), new KeyValue<Double, Double>(-1.0, +1.0),
                    new Figure(400, 150),
                    Util.getDirExecution("parabens_piano.pdf"),
                    Util.getDirExecution("parabens_piano.png"));
        //----------------------------------------------------------------------
        System.out.println("Figure generated.");
    }
}
