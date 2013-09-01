package com.alexcaranha.jquerybyhumming.model.system.pitchTracking;

import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Signal;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.dsp.Filter;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexcaranha
 */
public abstract class PT extends Configuration implements IExecutable {

    protected List<Frame>           frames = null;
    protected double[]              tempos = null;
    protected Map<String, SignalXY> signals = null;

    protected double thresholdOfVoice;

    public PT(int sampleRate, int frameLength, double overlap,
              double pitchMin, double pitchMax,
              int samplesToSmooth, int samplesToInterpolation,
              double amplitudeThreshold, double durationThreshold) {

        super();

        ConfigurationTableItem variable;
        variable = new ConfigurationTableItem<Integer>("sampleRage", null, sampleRate, "In Hz, defines the number of samples per unit of time (usually seconds) taken from a continuous signal to make a discrete signal.");
        this.variables.put("sampleRate", variable);

        variable = new ConfigurationTableItem<Integer>("frameLength", null, frameLength, "FrameLength is the length in samples of frame.");
        this.variables.put("frameLength", variable);

        variable = new ConfigurationTableItem<Double>("overlap", null, overlap, "Overlap is the percent of the frameLength that will be used.");
        this.variables.put("overlap", variable);

        variable = new ConfigurationTableItem<Double>("pitchMin", null, pitchMin, "PitchMin is the minimum pitch choosed to this system.");
        this.variables.put("pitchMin", variable);

        variable = new ConfigurationTableItem<Double>("pitchMax", null, pitchMax, "PitchMin is the maximum pitch choosed to this system.");
        this.variables.put("pitchMax", variable);

        variable = new ConfigurationTableItem<Integer>("samplesToSmooth", null, samplesToSmooth, "SamplesToSmooth indicates a number of samples that will be used to process of smooth.");
        this.variables.put("samplesToSmooth", variable);

        variable = new ConfigurationTableItem<Integer>("samplesToInterpolation", null, samplesToInterpolation, "SamplesToInterpolation indicates a number of samples that will be used to process of interpolation.");
        this.variables.put("samplesToInterpolation", variable);

        variable = new ConfigurationTableItem<Double>("amplitudeThreshold", null, amplitudeThreshold, "Indicates the threshold in Hertz utilized in the pos processing.");
        this.variables.put("amplitudeThreshold", variable);

        variable = new ConfigurationTableItem<Double>("durationThreshold", null, durationThreshold, "In seconds, indicates the threshold utilized in the pos processing.");
        this.variables.put("durationThreshold", variable);
    }

    private void clear() {
        this.thresholdOfVoice = 0.0;
        this.frames = null;
        this.tempos = null;
        this.signals = new HashMap();
    }

    protected int getSamplesToInterpolation() {
        return Convert.toInteger(this.variables.get("samplesToInterpolation").getValue());
    }

    protected int getSampleRate() {
        return Convert.toInteger(this.variables.get("sampleRate").getValue());
    }

    protected double getPitchMin() {
        return Convert.toDouble(this.variables.get("pitchMin").getValue());
    }

    protected double getPitchMax() {
        return Convert.toDouble(this.variables.get("pitchMax").getValue());
    }

    protected int getFrameLength() {
        return Convert.toInteger(this.variables.get("frameLength").getValue());
    }

    // <editor-fold defaultstate="collapsed" desc="Pre-processing, processing and pós-processing.">
    public void preProcessing(WavSignal wavSignal) throws Exception {
        //----------------------------------------------------------------------
        // <editor-fold defaultstate="collapsed" desc="Step 1: Resample input signal.">
        SignalXY result = WavSignal.resampleWavSignalFromPath(Constants.PATH_TMP_WAVE_FILE, Convert.toInteger(this.variables.get("sampleRate").getValue()));
        double   tempo  = result.getLastX();
        double[] amplitudes = (double[]) result.getY();
        int      frameLength = Convert.toInteger(this.variables.get("frameLength").getValue());
        double   overlap = Convert.toDouble(this.variables.get("overlap").getValue());
        // </editor-fold>
        //----------------------------------------------------------------------
        // <editor-fold defaultstate="collapsed" desc="Step 2: Calculate the threshold of voice.">
        this.frames = DSPFunctions.convertArrayDoubleToFrames(amplitudes, frameLength, overlap);
        this.tempos = Util.createArithmeticProgressionSequence(0.0, this.frames.size(), tempo);
        this.signals.put("pitchTracking", new SignalXY(tempos, 0.0));

        double[] energy = new double[frames.size()];
        for (int index = 0; index < frames.size(); index++) {
            energy[index] = frames.get(index).energy();
        }
        this.thresholdOfVoice = (Util.max(energy) / 100);
        // </editor-fold>7
        //----------------------------------------------------------------------
    }

    public abstract void processing() throws Exception;

    public void posProcessing() throws Exception {
        //----------------------------------------------------------------------
        boolean debug = false;
        //----------------------------------------------------------------------
        SignalXY signalPitchTracking = this.signals.get("pitchTracking");
        SignalXY signalPitchTrackingResult = signalPitchTracking.clone();
        //----------------------------------------------------------------------
        if (debug){
            Figure.save("", "tempo (s)", "Hz",
                        false,
                        Util.createArray(signalPitchTrackingResult.convertToXYSeries("pt")),
                        Util.createArray(Color.BLACK),
                        null,
                        new KeyValue(0.0, 13.0), new KeyValue(65.0, 1000.0),
                        new Figure(500, 300),
                        Util.getDirExecution("posprocessamento_1.pdf"),
                        Util.getDirExecution("posprocessamento_1.png"));
        }
        //----------------------------------------------------------------------
        // Step 1 : Median filter
        Filter.median(signalPitchTrackingResult.getY(), getSamplesToSmooth());
        //----------------------------------------------------------------------
        if (debug){
            Figure.save("", "tempo (s)", "Hz",
                        false,
                        Util.createArray(signalPitchTrackingResult.convertToXYSeries("pt")),
                        Util.createArray(Color.BLACK),
                        null,
                        new KeyValue(0.0, 13.0), new KeyValue(65.0, 1000.0),
                        new Figure(500, 300),
                        Util.getDirExecution("posprocessamento_2.pdf"),
                        Util.getDirExecution("posprocessamento_2.png"));

            Figure.save("", "tempo (s)", "Hz",
                        false,
                        Util.createArray(Signal.convertToXYSeries("derivada", this.tempos, Util.derivative(signalPitchTrackingResult.getY()))),
                        Util.createArray(Color.BLACK),
                        null,
                        new KeyValue(0.0, 13.0), new KeyValue(-700.0, +700.0),
                        new Figure(500, 300),
                        Util.getDirExecution("posprocessamento_3.pdf"),
                        Util.getDirExecution("posprocessamento_3.png"));
        }
        //----------------------------------------------------------------------
        // Step 2 : Derivative
        posProcessing_Step2(signalPitchTrackingResult.getY(), signalPitchTrackingResult.getX());
        //----------------------------------------------------------------------
        if (debug){
            Figure.save("", "tempo (s)", "Hz",
                        false,
                        Util.createArray(signalPitchTrackingResult.convertToXYSeries("pt")),
                        Util.createArray(Color.BLACK),
                        null,
                        new KeyValue(0.0, 13.0), new KeyValue(65.0, 1000.0),
                        new Figure(500, 300),
                        Util.getDirExecution("posprocessamento_4.pdf"),
                        Util.getDirExecution("posprocessamento_4.png"));

        }
        //----------------------------------------------------------------------
        this.signals.put("pitchTrackingResult", signalPitchTrackingResult);
        //----------------------------------------------------------------------
    }

    private void posProcessing_Step2(double[] pt, double[] tempos) throws Exception {
        double[] derivada  = Util.derivative(pt);
        double   variacao, value;
        double   thresholdAmplitude = Convert.toDouble(this.variables.get("amplitudeThreshold").getValue());
        double   thresholdDuration = Convert.toDouble(this.variables.get("durationThreshold").getValue());

        int       onset = -1, offset = -1;
        int       ultAtualizar = 0;

        for (int i = 0; i < derivada.length - 1; i += 1) {
            //System.out.println("i: " + i);
            if (derivada[i] > thresholdAmplitude) {
                onset = i;
                ultAtualizar = 1; // onset;
                /*
                System.out.println("index: " + i +
                               ", onset=[" + onset + ", " + ((onset > -1) ? tempo[onset] : "-") + "]" +
                               ", offset=[" + offset + ", " + ((offset > -1) ? tempo[offset] : "-") + "]" +
                               ", ultAtualizar=" + ((ultAtualizar == 1) ? "onset" : "offset"));
                */
            }
            if (derivada[i] < -thresholdAmplitude) {
                offset = i + 1;
                ultAtualizar = 2; // offset;
                /*
                System.out.println("index: " + i +
                               ", onset=[" + onset + ", " + ((onset > -1) ? tempo[onset] : "-") + "]" +
                               ", offset=[" + offset + ", " + ((offset > -1) ? tempo[offset] : "-") + "]" +
                               ", ultAtualizar=" + ((ultAtualizar == 1) ? "onset" : "offset"));
                */
            }

            if (onset > -1 && offset > -1) {
                variacao = Math.abs(tempos[offset] - tempos[onset]);
                //System.out.println("variacao: " + variacao);
                if (variacao < thresholdDuration) {

                    if (ultAtualizar == 2) {
                        value = onset > 0 ? pt[onset - 1] : 0;

                        Arrays.fill(pt, onset, offset + 1, value);
                    } else {
                        value = offset > 0 ? pt[offset - 1] : 0;

                        Arrays.fill(pt, offset, onset + 1, value);
                    }
                }

                if (ultAtualizar == 1) {
                    offset = -1;
                } else {
                    onset = -1;
                }
            }
        }
    }
    // </editor-fold>

    @Override
    public void execute(Map<String, Object> params) throws Exception{
        WavSignal signal = (WavSignal) params.get("wavSignal");

        clear();
        preProcessing(signal);
        processing();
        posProcessing();
    }

    @Override
    public String toString() {
        return getTitle();
    }

    protected int getSamplesToSmooth() {
        return Convert.toInteger(this.variables.get("samplesToSmooth").getValue());
    }

    public SignalXY getSignalXY() {
        return this.signals.get("pitchTrackingResult");
    }

    /*
    public static void main(String[] args) throws FileNotFoundException, Exception {
        PT apt[] = Utils.createArray(new PT_ACF(), new PT_HPS(), new PT_CEPS(), new PT_YIN());

        String  fileName = "sinal1_Miku";
        String  pathMidi = "/Resource/Database/PitchTracking/sinal1.mid";
        String  path = "/Resource/Database/PitchTracking/" + fileName + ".wav";

        System.out.println("Inicio");
        for (PT pt : apt) {
            //pt.test(fileName, path);
            pt.test(fileName, pathMidi, path);
        }
        System.out.println("Fim");
    }
    */
}