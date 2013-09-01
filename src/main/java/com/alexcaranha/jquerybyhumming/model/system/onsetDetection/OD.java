package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.model.Constants;
import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.system.IExecutable;
import com.alexcaranha.jquerybyhumming.screen.configuration.Configuration;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;

/**
 *
 * @author alexcaranha
 */
public abstract class OD extends Configuration implements IExecutable {

    protected Map<String, Object> signals = null;

    public OD(int sampleRate, int frameLength, double overlap,
              int pointsToMovingAverage, double durationThreshold) {

        super();

        ConfigurationTableItem variable;
        variable = new ConfigurationTableItem<Integer>("sampleRage", null, sampleRate, "In Hz, defines the number of samples per unit of time (usually seconds) taken from a continuous signal to make a discrete signal.");
        this.variables.put("sampleRate", variable);

        variable = new ConfigurationTableItem<Integer>("frameLength", null, frameLength, "FrameLength is the length in samples of frame.");
        this.variables.put("frameLength", variable);

        variable = new ConfigurationTableItem<Double>("overlap", null, overlap, "Overlap is the percent of the frameLength that will be used.");
        this.variables.put("overlap", variable);

        variable = new ConfigurationTableItem<Integer>("pointsToMovingAverage", null, pointsToMovingAverage, "pointsToMovingAverage indicates a number of samples that will be used to process of smooth.");
        this.variables.put("pointsToMovingAverage", variable);

        variable = new ConfigurationTableItem<Double>("durationThreshold", null, durationThreshold, "In seconds, indicates the threshold utilized in the pos processing.");
        this.variables.put("durationThreshold", variable);
    }

    private void clear() {
        this.signals = new HashMap();
    }

    protected int getSampleRate() {
        return Convert.toInteger(this.variables.get("sampleRate").getValue());
    }

    protected int getFrameLength() {
        return Convert.toInteger(this.variables.get("frameLength").getValue());
    }

    protected double getOverlap() {
        return Convert.toDouble(this.variables.get("overlap").getValue());
    }

    protected double getDurationThreshold() {
        return Convert.toDouble(this.variables.get("durationThreshold").getValue());
    }

    // <editor-fold defaultstate="collapsed" desc="Pre-processing, processing and pós-processing.">
    public void preProcessing(WavSignal wavSignal) throws Exception {
        //----------------------------------------------------------------------
        // <editor-fold defaultstate="collapsed" desc="Step 1: Resample input signal.">
        SignalXY result = WavSignal.resampleWavSignalFromPath(Constants.PATH_TMP_WAVE_FILE, Convert.toInteger(this.variables.get("sampleRate").getValue()));        
        // </editor-fold>
        //----------------------------------------------------------------------
        // <editor-fold defaultstate="collapsed" desc="Step 2: Filtragem em oitavas.">
        OD_OctaveBandFilter bandFilter = new OD_OctaveBandFilter();
        List<SignalXY> bands = bandFilter.process(result.getX(), result.getY());
        this.signals.put("signalsFiltered", bands);
        // </editor-fold>
        //----------------------------------------------------------------------
    }

    public abstract SignalXY processing(SignalXY signalXY) throws Exception;

    @Override
    public void execute(Map<String, Object> params) throws Exception{
        //----------------------------------------------------------------------
        boolean         debug = false;
        WavSignal       signal = (WavSignal) params.get("wavSignal");

        clear();
        //----------------------------------------------------------------------
        // Pre-processing
        preProcessing(signal);
        //----------------------------------------------------------------------
        List<SignalXY>  signalsFiltered = (List<SignalXY>) this.signals.get("signalsFiltered");
        List<SignalXY>  signalsReduction = new ArrayList<SignalXY>();
        //----------------------------------------------------------------------
        // Processing
        for (int index = 0; index < signalsFiltered.size(); index += 1) {
            SignalXY signalXY = signalsFiltered.get(index).clone();
            SignalXY signalProcessed = processing(signalXY);
            signalsReduction.add(signalProcessed);

            if (debug) {
                Figure.save("", "tempo (s)", "amplitude",
                            true,
                            Util.createArray(signalProcessed.convertToXYSeries("onset detection")),
                            Util.createArray(Color.BLACK), null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution(String.format("%s_b%d_original.pdf", this.getAlias(), index)),
                            Util.getDirExecution(String.format("%s_b%d_original.png", this.getAlias(), index)));
            }
        }
        this.signals.put("signalsReduction", signalsReduction);
        //----------------------------------------------------------------------
        // Pos-processing
        posProcessing();
        //----------------------------------------------------------------------
    }

    public void posProcessing() throws Exception {
        //----------------------------------------------------------------------
        int             index;
        boolean         debug = false;
        double[]        tempos = (double[]) this.signals.get("tempos");
        List<SignalXY>  signalSubBand = new ArrayList<SignalXY>();
        //----------------------------------------------------------------------
        List<SignalXY>  signalsReduction = (List<SignalXY>) this.signals.get("signalsReduction");
        SignalXY        signalResult = new SignalXY(signalsReduction.get(0).getX(), null);
        if (signalsReduction == null) return;
        //----------------------------------------------------------------------
        for (index = 0; index < signalsReduction.size(); index += 1) {
            //------------------------------------------------------------------
            SignalXY signalReduction = signalsReduction.get(index);
            signalSubBand.add(signalReduction);
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("signal", signalReduction.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep0.pdf", this.getAlias(), index)),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep0.png", this.getAlias(), index)));
            }
            //------------------------------------------------------------------
            // Passo 1: Normalizar pelo valor máximo.
            Util.normalizeByMaximum(signalReduction.getY());

            if (debug) {
                Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("signal", signalReduction.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep1.pdf", this.getAlias(), index)),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep1.png", this.getAlias(), index)));
            }
            //------------------------------------------------------------------
            // Passo 2: Derivar o sinal.
            Util.derivativeInset(signalReduction.getY());

            if (debug) {
                Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("signal", signalReduction.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep2.pdf", this.getAlias(), index)),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep2.png", this.getAlias(), index)));
            }
            //------------------------------------------------------------------
            // Passo 3: Limiarização através de média móvel de 3 pontos.
            double[] buffer = Util.movingAverage(signalReduction.getY(), this.getPointsToMovingAverage(), -0.1);

            if (debug) {
                Figure.save("", "amostras", "amplitude",
                        true,
                        Util.createArray(SignalXY.convertToXYSeries("limiar", buffer),
                                          SignalXY.convertToXYSeries("signal", signalReduction.getY())),
                        Util.createArray(Color.RED, Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep3.pdf", this.getAlias(), index)),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep3.png", this.getAlias(), index)));
            }

            for(int i = 0; i < signalReduction.getY().length; i += 1) {
                signalReduction.getY()[i] = (signalReduction.getY()[i] < buffer[i]) ? 0.0 : signalReduction.getY()[i];
            }

            if (debug) {
                Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("limiar", signalReduction.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep4.pdf", this.getAlias(), index)),
                        Util.getDirExecution(String.format("%s_b%d_posProcessingStep4.png", this.getAlias(), index)));
            }
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        // Passo 4: Unir sinais das bandas para compor sinal resultante.
        signalResult.joinSignalsFromIndices(signalSubBand);

        if (debug) {
            Figure.save("", "amostras", "amplitude",
                    false,
                    Util.createArray(SignalXY.convertToXYSeries("signalResult", signalResult)),
                    Util.createArray(Color.BLACK),
                    null,
                    null, null,
                    new Figure(500, 200),
                    Util.getDirExecution(String.format("%s_union_posProcessingStep5.pdf", this.getAlias())),
                    Util.getDirExecution(String.format("%s_union_posProcessingStep5.png", this.getAlias())));
        }

        for (int i = 0; i < signalResult.getLength(); i += 1) {
            double sample_i = signalResult.getY(i);
            double tempo_i  = signalResult.getX(i);

            for (int j = i + 1; j < signalResult.getLength(); j += 1) {
                double sample_j = signalResult.getY(j);
                double tempo_j  = signalResult.getX(j);

                double variacao = tempo_j - tempo_i;

                if (variacao < this.getDurationThreshold()) {

                    if (sample_i > 0.0 && sample_j > 0.0)
                    if (sample_j > sample_i) {
                        signalResult.changeValueY(i, 0.0);
                        j = signalResult.getLength();
                    } else {
                        signalResult.changeValueY(j, 0.0);
                    }
                } else break;
            }
        }

        if (debug) {
            Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("limiar", signalResult.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_union_posProcessingStep6.pdf", this.getAlias())),
                        Util.getDirExecution(String.format("%s_union_posProcessingStep6.png", this.getAlias())));
        }
        //----------------------------------------------------------------------
        // Passo 5: Normalizar pelo valor máximo.
        Util.normalizeByMaximum(signalResult.getY());

        if (debug) {
            Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("limiar", signalResult.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_union_posProcessingStep7.pdf", this.getAlias())),
                        Util.getDirExecution(String.format("%s_union_posProcessingStep7.png", this.getAlias())));
        }
        //----------------------------------------------------------------------
        // Passo 6: Aplicar limiar.
        Util.thresholding(signalResult.getY(), 0.1, 0.0);

        if (debug) {
            Figure.save("", "amostras", "amplitude",
                        false,
                        Util.createArray(SignalXY.convertToXYSeries("limiar", signalResult.getY())),
                        Util.createArray(Color.BLACK),
                        null,
                        null, null,
                        new Figure(500, 200),
                        Util.getDirExecution(String.format("%s_union_posProcessingStep8.pdf", this.getAlias())),
                        Util.getDirExecution(String.format("%s_union_posProcessingStep8.png", this.getAlias())));
        }
        //----------------------------------------------------------------------
        this.signals.put("onsetDetectionResult", signalResult);
        //----------------------------------------------------------------------
    }

    @Override
    public String toString() {
        return getTitle();
    }

    protected int getPointsToMovingAverage() {
        return Convert.toInteger(this.variables.get("pointsToMovingAverage").getValue());
    }

    protected int getSamplesToInterpolation() {
        return Convert.toInteger(this.variables.get("samplesToInterpolation").getValue());
    }

    public SignalXY getSignalXY() {
        return (SignalXY) this.signals.get("onsetDetectionResult");
    }
    
    public List<KeyValue> getMarkers() {
        List<KeyValue> listMarkers = new ArrayList<KeyValue>();
        SignalXY signalResultant = getSignalXY();
        
        for(int index = 0; index < signalResultant.getLength(); index += 1) {
            if (signalResultant.getY(index) > 0.0) {
                listMarkers.add(new KeyValue(index, signalResultant.getX(index)));
            }
        }
        
        return listMarkers;
    }
    
    public List<Marker> getMarkersForFigure(Color color) {
        List<KeyValue> listMarkers = getMarkers();
        List<Marker>   markers = new ArrayList<Marker>();
        
        for(KeyValue key : listMarkers) {
            Marker marker = new ValueMarker( Convert.toDouble(key.getValue()));
            marker.setPaint(color);
            markers.add(marker);
        }
        
        return markers;
    }

    /*
    public static void main(String[] args) throws FileNotFoundException, Exception {
        PT apt[] = Util.createArray(new PT_ACF(), new PT_HPS(), new PT_CEPS(), new PT_YIN());

        String  fileName = "sinal1_Miku";
        String  pathMidi = "/Resource/Database/OnsetDetection/sinal1.mid";
        String  path = "/Resource/Database/OnsetDetection/" + fileName + ".wav";

        System.out.println("Inicio");
        for (PT pt : apt) {
            //pt.test(fileName, path);
            pt.test(fileName, pathMidi, path);
        }
        System.out.println("Fim");
    }
    */
}