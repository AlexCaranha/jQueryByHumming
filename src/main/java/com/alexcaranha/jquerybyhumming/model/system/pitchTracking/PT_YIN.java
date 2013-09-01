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
import java.util.Arrays;

/**
 *
 * @author alexcaranha
 */
public class PT_YIN extends PT {

    public PT_YIN(int sampleRate, int frameLength, double overlap,
                  double pitchMin, double pitchMax,
                  int samplesToSmooth, int samplesToInterpolation,
                  double amplitudeThreshold, double durationThreshold,
                  double threshold) {

        super(sampleRate, frameLength, overlap,
              pitchMin, pitchMax,
              samplesToSmooth, samplesToInterpolation,
              amplitudeThreshold, durationThreshold);

        ConfigurationTableItem variable = new ConfigurationTableItem<Double>("threshold", null, threshold, "Threshold of algorithm.");
        this.variables.put("threshold", variable);
    }
    
    private double getThreashold() {
        return Convert.toDouble(this.variables.get("threshold").getValue());
    }

    @Override
    public void processing() throws Exception {
        //----------------------------------------------------------------------
        boolean     debug = false;
        double      pitch;

        SignalXY    pitchTracking = (SignalXY) this.signals.get("pitchTracking");
        double[]    buffer = new double[this.getFrameLength()];
        double[]    bufferDifference = new double[super.getFrameLength()];
        double[]    bufferApproximationX;
        double[]    bufferApproximationY;
        double[]    x = new double[2];  Arrays.fill(x, 0.0);
        double[]    y = new double[2];  Arrays.fill(y, 0.0);
        double      difference, accumulatedSum, threshold = this.getThreashold();
        double      pitchMin = this.getPitchMin();
        double      pitchMax = this.getPitchMax();

        KeyValue<Double, Double>    vertex; // Parabola

        int     qtdSamplesPerSide = (this.getSamplesToInterpolation() - 1) / 2;
        int     iFrame, tau, sizeFrames = this.frames.size();
        int     frameLength = this.getFrameLength();
        int     sampleRate = this.getSampleRate();
        //----------------------------------------------------------------------
        // int half = (int) Math.floor((double)this.frameLength / 2);
        int     indexMin = (int) Math.floor((double) this.getSampleRate() / this.getPitchMax());
        int     indexMax = (int) Math.floor((double) this.getSampleRate() / this.getPitchMin());

        Frame   frame/*, window = Windows.hamming(frameLength)*/;

        ComplexDoubleFFT fft = new ComplexDoubleFFT(frameLength);
        for (iFrame = 0, pitch = 0.0; iFrame < sizeFrames; iFrame += 1) {
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
                            Util.getDirExecution("yin_frame.pdf"),
                            Util.getDirExecution("yin_frame.png"));
            }
            //------------------------------------------------------------------
            accumulatedSum = 0;
            Arrays.fill(buffer, 0.0); // clear buffer;
            //------------------------------------------------------------------
            for (tau = 0, accumulatedSum = 0; tau < frameLength; tau++) {
                difference = differenceFunction(frame.getRealSamples(), tau);
                accumulatedSum += difference;
                buffer[tau] = differenceFunctionNormalised(difference, accumulatedSum, tau);
                bufferDifference[tau] = difference;
            }
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude", false,
                            Util.createArray(Signal.convertToXYSeries(frame.getRealSamples())),
                            Util.createArray(Color.BLACK),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("yin_frame.pdf"),
                            Util.getDirExecution("yin_frame.png"));

                Figure.save("", "amostras", "amplitude", false,
                            Util.createArray(Signal.convertToXYSeries(bufferDifference)),
                            Util.createArray(Color.BLACK),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("yin_difference.pdf"),
                            Util.getDirExecution("yin_difference.png"));

                Figure.save("", "amostras", "amplitude", false,
                            Util.createArray(Signal.convertToXYSeries(buffer)),
                            Util.createArray(Color.BLACK),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("yin_differenceNormalized.pdf"),
                            Util.getDirExecution("yin_differenceNormalized.png"));
            }
            //------------------------------------------------------------------
            for (int sample = indexMin;
                 sample <= indexMax;
                 sample++) {

                int indexInitial = sample - qtdSamplesPerSide;
                int indexFinal = sample + qtdSamplesPerSide;

                if (indexInitial < 0 || indexFinal > frameLength) {
                    continue;
                }
                if (buffer[sample] >= threshold) {
                    continue;
                }

                bufferApproximationX = Util.createArithmeticProgressionSequence((double) indexInitial, (double) +1.0, (double) indexFinal);
                bufferApproximationY = Arrays.copyOfRange(buffer, indexInitial, indexFinal + 1);

                if (!Util.IsVertexDown(bufferApproximationY)) {
                    continue;
                }

                vertex = Util.estimateVertexOfParabola(bufferApproximationX, bufferApproximationY);
                if (vertex == null) continue;

                pitch = sampleRate / (double)vertex.getKey();

                while(pitch > pitchMax) {
                    pitch /= 2;
                }
                if (pitch < pitchMin) {
                    pitch = 0.0;
                }
                break;

            }
            //------------------------------------------------------------------
            pitchTracking.changeValueY(iFrame, pitch);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
    }

    private double differenceFunction(double[] frame, int tau) {
        //----------------------------------------------------------------------
        int j, l;
        double sum = 0;
        double difference;
        int frameLength = this.getFrameLength();
        //----------------------------------------------------------------------
        for (j = 0; j < frameLength; j++) {
            l = j + tau;
            if (l < frameLength) {
                difference = frame[j] - frame[l];
                sum += difference * difference;
            }
        }
        //----------------------------------------------------------------------
        return sum;
        //----------------------------------------------------------------------
    }

    private double differenceFunctionNormalised(double difference,
                                                double sumAccumulated,
                                                int tau) {
        //----------------------------------------------------------------------
        if (tau == 0 || tau == 1) {
            return 1;
        }
        //----------------------------------------------------------------------
        return (difference * tau / sumAccumulated);
        //----------------------------------------------------------------------
    }

    @Override
    public String getTitle() {
        return "YIN";
    }

    @Override
    public String getAlias() {
        return "YIN";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
    
    /*
    public static void main(String[] args) throws Exception {
        PT yin = (PT_YIN) App.getContext().getBean("pt_yin");
        if (yin != null) {
            WavSignal signal = new WavSignal();
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream())));
            yin.execute(Util.createMap(new KeyValue<String, Object>("wavSignal", signal)));

            Figure.save("", "tempo (s)", "Hz", false,
                        Util.createArray(yin.getPitchTracking().convertToXYSeries("pitchTracking")),
                        Util.createArray(Color.black),
                        null,
                        null, null,
                        new Figure(500, 300),
                        Util.getDirExecution("yin_final.pdf"),
                        Util.getDirExecution("yin_final.png"));
        }
    }
    */
}