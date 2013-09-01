package com.alexcaranha.jquerybyhumming.model.system.pitchTracking;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Signal;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author alexcaranha
 */
public class PT_CEPS extends PT {

    public PT_CEPS(int sampleRate, int frameLength, double overlap,
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
                            Util.getDirExecution("ceps_frame.pdf"),
                            Util.getDirExecution("ceps_frame.png"));
            }
            //------------------------------------------------------------------
            frame.multiplyInline(window).fftInline(fft);
            for (int index = 0; index < this.getFrameLength(); index++){
                double valueX = frame.getSampleReal(index);
                double valueY = frame.getSampleImaginary(index);

                frame.setSample(index, Math.log(valueX*valueX + valueY*valueY), 0.0);
            }
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "Log( |F(w)| . |F(w)| )",
                            false,
                            Signal.convertToXYSeries(frame.getRealSamples()),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("ceps_log.pdf"),
                            Util.getDirExecution("ceps_log.png"));
            }
            //------------------------------------------------------------------
            frame.ifftInline(fft);
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                            false,
                            Signal.convertToXYSeries(frame.getRealSamples()),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("ceps_step.pdf"),
                            Util.getDirExecution("ceps_step.png"));
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
        return "CEPS";
    }

    @Override
    public String getAlias() {
        return "CEPS";
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    /*
    public static void main(String[] args) throws Exception {
        PT ceps = (PT_CEPS) App.getContext().getBean("pt_ceps");
        if (ceps != null) {
            WavSignal signal = new WavSignal();
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream())));
            ceps.execute(Util.createMap(new KeyValue<String, Object>("wavSignal", signal)));

            Figure.save("", "tempo (s)", "Hz", false,
                        Util.createArray(ceps.getPitchTracking().convertToXYSeries("pitchTracking")),
                        Util.createArray(Color.black),
                        null,
                        null, null,
                        new Figure(500, 300),
                        Util.getDirExecution("ceps_final.pdf"),
                        Util.getDirExecution("ceps_final.png"));
        }
    }
    */
}