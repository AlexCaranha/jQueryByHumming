package com.alexcaranha.jquerybyhumming.model.system.pitchTracking;

import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Signal;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import java.awt.Color;
import java.util.Arrays;
import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author alexcaranha
 */
public class PT_ACF extends PT {

    public PT_ACF(int sampleRate, int frameLength, double overlap,
                  double pitchMin, double pitchMax,
                  int samplesToSmooth, int samplesToInterpolation,
                  double amplitudeThreshold, double durationThreshold) {

        super(sampleRate, frameLength, overlap,
              pitchMin, pitchMax,
              samplesToSmooth, samplesToInterpolation,
              amplitudeThreshold, durationThreshold);
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
        //----------------------------------------------------------------------
        // int half = (int) Math.floor((double)this.frameLength / 2);
        int     indexMin = (int) Math.floor((double) this.getSampleRate() / this.getPitchMax());
        int     indexMax = (int) Math.floor((double) this.getSampleRate() / this.getPitchMin());

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
                            Util.getDirExecution("acf_frame.pdf"),
                            Util.getDirExecution("acf_frame.png"));
            }
            //------------------------------------------------------------------
            frame.multiplyInline(window).fftInline(fft);
            for (int index = 0; index < this.getFrameLength(); index++){
                Complex number1 = frame.getSample(index);
                Complex number2 = number1.conjugate();
                frame.setSample(index, number1.multiply(number2));
            }
            frame.fftInline(fft);
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                            false,
                            Signal.convertToXYSeries(frame.getRealSamples()),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("acf_final.pdf"),
                            Util.getDirExecution("acf_final.png"));
            }
            //------------------------------------------------------------------
            maxPeak = Util.max(frame.getRealSamples(), indexMin, indexMax);
            if (maxPeak == null) continue;
            //------------------------------------------------------------------
            vertex = Util.estimateVertexOfParabola_Up((int) maxPeak.getKey(), frame.getRealSamples(), qtdSamplesPerSide);
            if (vertex == null) continue;
            //------------------------------------------------------------------
            //pitch = (double) this.sampleRate / (double) vertex.getKey();
            //pitch = (double) this.sampleRate / vertex<Double>.getKey()
            pitch = (double) this.getSampleRate() / Double.parseDouble(vertex.getKey().toString());

            while(pitch > this.getPitchMax()) {
                pitch /= 2;
            }
            if (pitch < this.getPitchMin()) {
                pitch = 0.0;
            }

            pitchTracking.changeValueY(iFrame, pitch);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
    }

    @Override
    public String getTitle() {
        return "AUTO-CORRELATION FUNCTION";
    }

    @Override
    public String getAlias() {
        return "ACF";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
    
    /*
    public static void main(String[] args) throws Exception {
        PT acf = (PT_ACF) App.getContext().getBean("pt_acf");
        if (acf != null) {
            WavSignal signal = new WavSignal();
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream())));
            acf.execute(Util.createMap(new KeyValue<String, Object>("wavSignal", signal)));

            Figure.save("", "tempo (s)", "Hz", false,
                        Util.createArray(acf.getPitchTracking().convertToXYSeries("pitchTracking")),
                        Util.createArray(Color.black),
                        null,
                        null, null,
                        new Figure(500, 300),
                        Util.getDirExecution("acf_final.pdf"),
                        Util.getDirExecution("acf_final.png"));
        }
    }
    */
}