package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.Signal;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.dsp.Filter;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class OD_DerivadaEnvoltoria extends OD {

    public OD_DerivadaEnvoltoria(int sampleRate, int frameLength, double overlap,
                                 int pointsToMovingAverage, double durationThreshold,
                                 int samplesToSmooth) {

        super(sampleRate, frameLength, overlap, samplesToSmooth, durationThreshold);

        ConfigurationTableItem variable = new ConfigurationTableItem<Integer>("samplesToSmooth", Util.createArray(3, 5, 7, 9, 11, 13), samplesToSmooth, "SamplesToSmooth indicates a number of samples that will be used to process of smooth.");
        this.variables.put("samplesToSmooth", variable);
    }

    @Override
    public SignalXY processing(SignalXY signalXY) throws Exception {
        //----------------------------------------------------------------------
        boolean     debug = false;
        double      overlap = this.getOverlap();

        List<Frame> frames = DSPFunctions.convertArrayDoubleToFrames(signalXY.getY(), this.getFrameLength(), overlap);

        SignalXY    signalXYProcessed = new SignalXY(frames.size());
        signalXYProcessed.setX(Util.createArithmeticProgressionSequence(0.0, frames.size(), signalXY.getLastX()));
        //----------------------------------------------------------------------
        double[]    buffer;
        double[]    envoltorias = new double[frames.size()];
        //----------------------------------------------------------------------
        for (int iFrame = 0; iFrame < frames.size(); iFrame += 1) {
            buffer = Util.abs(frames.get(iFrame).getRealSamples());
            envoltorias[iFrame] = Util.mean(buffer);
        }
        //----------------------------------------------------------------------
        if (debug) {
            Figure.save("", "tempo (s)", "amplitude",
                        true,
                        Util.createArray(Signal.convertToXYSeries("envoltoria", envoltorias)),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 300),
                        null,
                        Util.getDirExecution("envoltoria.png")
                    );
        }
        //----------------------------------------------------------------------
        Filter.median(envoltorias, this.getSamplesToSmooth());
        //----------------------------------------------------------------------
        if (debug) {
            Figure.save("", "tempo (s)", "Hz",
                        true,
                        Util.createArray(Signal.convertToXYSeries("envoltoria suavisada", envoltorias)),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 300),
                        null,
                        Util.getDirExecution("envoltoriaSuavisada.png")
                    );
        }
        //----------------------------------------------------------------------
        buffer = Util.derivative(envoltorias);
        //----------------------------------------------------------------------
        if (debug) {
            Figure.save("", "tempo (s)", "Hz",
                        true,
                        Util.createArray(Signal.convertToXYSeries("envoltoria suavisada", buffer)),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 300),
                        null,
                        Util.getDirExecution("envoltoriaSuavisadaDerivada.png")
                    );
        }
        //----------------------------------------------------------------------
        Util.thresholding(buffer, 0.0, 0.0);
        //----------------------------------------------------------------------
        signalXYProcessed.setY(buffer);
        //----------------------------------------------------------------------
        return signalXYProcessed;
    }

    @Override
    public String getTitle() {
        return "DERIVADA DA ENVOLTORIA";
    }

    @Override
    public String getAlias() {
        return "DE";
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    private int getSamplesToSmooth() {
        return Convert.toInteger(this.variables.get("samplesToSmooth").getValue());
    }
    
    /*
    public static void main(String[] args) throws Exception {
        OD od = (OD_DerivadaEnvoltoria) App.getContext().getBean("od_derivadaEnvoltoria");
        if (od != null) {
            WavSignal signal = new WavSignal();
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream())));            
            od.execute(Util.createMap(new KeyValue<String, Object>("wavSignal", signal)));

            Figure.save("", "tempo (s)", "amplitude", false,
                        Util.createArray(od.getOnsetDetection().convertToXYSeries("onsetDetection")),
                        Util.createArray(Color.black),
                        null,
                        null, null,
                        new Figure(500, 300),
                        Util.getDirExecution(String.format("%s_onsetDetection.pdf", od.getAlias())),
                        Util.getDirExecution(String.format("%s_onsetDetection.png", od.getAlias())));
        }
    }
    */ 
}