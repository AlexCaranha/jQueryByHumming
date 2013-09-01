package com.alexcaranha.jquerybyhumming.model.system.pitchTracking;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Signal;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import com.alexcaranha.jquerybyhumming.screen.configuration.table.ConfigurationTableItem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class PT_HPS extends PT {

    public PT_HPS(int sampleRate, int frameLength, double overlap,
                  double pitchMin, double pitchMax,
                  int samplesToSmooth, int samplesToInterpolation,
                  double amplitudeThreshold, double durationThreshold,
                  int qtdHarmonics) {

        super(sampleRate, frameLength, overlap,
              pitchMin, pitchMax,
              samplesToSmooth, samplesToInterpolation,
              amplitudeThreshold, durationThreshold);

        ConfigurationTableItem variable = new ConfigurationTableItem<Integer>("qtdHarmonics", Util.createArray(2, 3, 4, 5, 6, 7, 8, 9, 10), qtdHarmonics, "Number of harmonics used in this algorithm.");
        this.variables.put("qtdHarmonics", variable);
    }

    @Override
    public void processing() throws Exception {
        //----------------------------------------------------------------------
        boolean     debug = false;
        double      pitch;

        SignalXY    pitchTracking = (SignalXY) this.signals.get("pitchTracking");
        double[]    x = new double[2];  Arrays.fill(x, 0.0);
        double[]    y = new double[2];  Arrays.fill(y, 0.0);

        KeyValue<Integer, Double>   maxPeak;
        KeyValue<Double, Double>    vertex; // Parabola

        int     qtdSamplesPerSide = (this.getSamplesToInterpolation() - 1) / 2;
        int     iFrame, sizeFrames = this.frames.size();

        List<KeyValue>   vertices = new ArrayList<KeyValue>();
        //----------------------------------------------------------------------
        int     indexMin = (int) Math.floor((double) this.getPitchMin() * this.getFrameLength() / this.getSampleRate());
        int     indexMax = (int) Math.floor((double) this.getPitchMax() * this.getFrameLength() / this.getSampleRate());

        Frame   frame, window = Windows.hamming(this.getFrameLength());

        ComplexDoubleFFT fft = new ComplexDoubleFFT(this.getFrameLength());
        for (iFrame = 0; iFrame < sizeFrames; iFrame += 1) {
            //------------------------------------------------------------------
            frame = this.frames.get(iFrame);
            //------------------------------------------------------------------
            if (frame.energy() < this.thresholdOfVoice) continue;
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                            false,
                            Signal.convertToXYSeries(frame.getRealSamples()),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("hps_frame" + iFrame + ".pdf"),
                            Util.getDirExecution("hps_frame" + iFrame + ".png"));
            }
            //------------------------------------------------------------------
            double[] modulo = frame.multiplyInline(window).fftOffline(fft).getModule();
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                            false,
                            Signal.convertToXYSeries(frame.getRealSamples()),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("hps_frameJanelado" + iFrame + ".pdf"),
                            Util.getDirExecution("hps_frameJanelado" + iFrame + ".png"));

                Figure.save("", "amostras", "amplitude",
                            false,
                            Signal.convertToXYSeries(modulo),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("hps_frameEspectro" + iFrame + ".pdf"),
                            Util.getDirExecution("hps_frameEspectro" + iFrame + ".png"));
            }
            double[] bufferOut = Arrays.copyOfRange(modulo, 0, this.getFrameLength());
            //------------------------------------------------------------------
            for (int iSample = 0; iSample < this.getFrameLength(); iSample += 1) {
                for (int iHarmonic = 2; iHarmonic <= this.getQtdHarmonics(); iHarmonic += 1) {
                    int index = iSample * iHarmonic;
                    if (index >= this.getFrameLength()) {
                        bufferOut[iSample] = 0;
                    } else {
                        bufferOut[iSample] *= modulo[index];
                    }
                }
            }
            //------------------------------------------------------------------
            Util.findVertices_Up(bufferOut, indexMin, indexMax, vertices);
            if (vertices.isEmpty()) continue;
            //------------------------------------------------------------------
            sortKeyValues_desc(vertices);

            while(vertices.size() > 2) {
                vertices.remove(vertices.size() - 1);
            }
            //------------------------------------------------------------------
            //maxPeak = vertices.get(0);
            if (vertices.size() == 1) {
                maxPeak = vertices.get(0);
            } else {
                int x1 = Convert.toInteger(vertices.get(0).getKey());
                int x2 = Convert.toInteger(vertices.get(1).getKey());

                double y1 = Convert.toDouble(vertices.get(0).getValue());
                double y2 = Convert.toDouble(vertices.get(1).getValue());

                if (x2 > x1) {
                    maxPeak = vertices.get(0);
                } else
                if ((x2 / (x1/2) > 0.9) && (y2 / y1 > 0.1)) {
                    maxPeak = vertices.get(1);
                }else{
                    maxPeak = vertices.get(0);
                }
            }
            //------------------------------------------------------------------
            vertex = Util.estimateVertexOfParabola_Up((int) maxPeak.getKey(), bufferOut, qtdSamplesPerSide);
            if (vertex == null) continue;
            //------------------------------------------------------------------
            pitch = (double) ((double) this.getSampleRate() * ((double) vertex.getKey())) / this.getFrameLength();

            while(pitch > this.getPitchMax()) {
                pitch /= 2;
            }
            if (pitch < this.getPitchMin()) {
                pitch = 0.0;
            }
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                            false,
                            Signal.convertToXYSeries(Arrays.copyOfRange(bufferOut, 0, (int) Math.ceil((double)bufferOut.length / 2))),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("hps_buffer" + iFrame + " - pitch " + pitch + ".pdf"),
                            Util.getDirExecution("hps_buffer" + iFrame + " - pitch " + pitch + ".png"));
            }
            pitchTracking.changeValueY(iFrame, pitch);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
    }

    private void sortKeyValues_desc(List<KeyValue> pairs) {
        double value1, value2;
        KeyValue obj1, obj2;

        for (int i = 0; i < pairs.size() - 1; i += 1) {
            for (int j = i + 1; j < pairs.size(); j += 1) {
                value1 = Convert.toDouble(pairs.get(i).getValue());
                value2 = Convert.toDouble(pairs.get(j).getValue());

                if (value1 < value2) {
                    obj2 = pairs.remove(j);
                    obj1 = pairs.remove(i);

                    pairs.add(i, obj2);
                    pairs.add(j, obj1);
                }
            }
        }
    }

    private int getQtdHarmonics() {
        return Convert.toInteger(this.variables.get("qtdHarmonics").getValue());
    }

    @Override
    public String getTitle() {
        return "HPS";
    }

    @Override
    public String getAlias() {
        return "HPS";
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    /*
    public static void main(String[] args) throws Exception {
        PT hps = (PT_HPS) App.getContext().getBean("pt_hps");
        if (hps != null) {
            WavSignal signal = new WavSignal();
            //signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream())));
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_solfejo.wav").getInputStream())));
            hps.execute(Util.createMap(new KeyValue<String, Object>("wavSignal", signal)));

            Figure.save("", "tempo (s)", "Hz", false,
                        Util.createArray(hps.getPitchTracking().convertToXYSeries("pitchTracking")),
                        Util.createArray(Color.black),
                        null,
                        null, null,
                        new Figure(500, 300),
                        Util.getDirExecution("hps_final.pdf"),
                        Util.getDirExecution("hps_final.png"));
        }
    }
    */
}