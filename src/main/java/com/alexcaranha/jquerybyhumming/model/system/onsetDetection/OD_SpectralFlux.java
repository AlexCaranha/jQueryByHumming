package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import com.alexcaranha.jquerybyhumming.model.Signal;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.WavSignal;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class OD_SpectralFlux extends OD {

    public OD_SpectralFlux(int sampleRate, int frameLength, double overlap,
     int pointsToMovingAverage, double durationThreshold) {

        super(sampleRate, frameLength, overlap, pointsToMovingAverage, durationThreshold);
    }

    @Override
    public SignalXY processing(SignalXY signalXY) throws Exception {
        //----------------------------------------------------------------------
        boolean     debug = false;
        double      value, overlap = this.getOverlap();
        double[]    previousFrame_module, currentFrame_module = null;

        int         index, frameLength = this.getFrameLength();
        List<Frame> frames = DSPFunctions.convertArrayDoubleToFrames(signalXY.getY(), frameLength, overlap);
        int         iFrame, sizeFrames = frames.size();

        SignalXY    signalXYProcessed = new SignalXY(frames.size());
        signalXYProcessed.setX(Util.createArithmeticProgressionSequence(0.0, frames.size(), signalXY.getLastX()));
        //----------------------------------------------------------------------
        Frame   frame, window = Windows.hamming(this.getFrameLength());

        ComplexDoubleFFT fft = new ComplexDoubleFFT(this.getFrameLength());
        for (iFrame = 0; iFrame < sizeFrames; iFrame += 1) {
            //------------------------------------------------------------------
            frame = frames.get(iFrame);
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                    false,
                    Signal.convertToXYSeries(frame.getRealSamples()),
                    Util.createArray(Color.black),
                    null,
                    null, null,
                    new Figure(500, 300),
                    Util.getDirExecution("sf_frame1.pdf"),
                    Util.getDirExecution("sf_frame1.png"));
            }
            //------------------------------------------------------------------
            frame.multiplyInline(window);
            //------------------------------------------------------------------
            if (debug) {
                Figure.save("", "amostras", "amplitude",
                    false,
                    Signal.convertToXYSeries(frame.getRealSamples()),
                    Util.createArray(Color.black),
                    null,
                    null, null,
                    new Figure(500, 300),
                    Util.getDirExecution("sf_frame2.pdf"),
                    Util.getDirExecution("sf_frame2.png"));
            }
            //------------------------------------------------------------------
            previousFrame_module = currentFrame_module;
            currentFrame_module = frame.fftInline(fft).getModule();
            //------------------------------------------------------------------
            if (previousFrame_module == null) {
                for (index = 0, value = 0.0; index < frameLength; index++){
                    value += currentFrame_module[index];
                }
            } else {
                for (index = 0, value = 0.0; index < frameLength; index++){
                    value += currentFrame_module[index] - previousFrame_module[index];
                }
            }
            if (value <= 0) continue;

            signalXYProcessed.changeValueY(iFrame, value);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        return signalXYProcessed;
    }

    @Override
    public String getTitle() {
        return "SPECTRAL FLUX";
    }

    @Override
    public String getAlias() {
        return "SF";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
    
    /*
    void main(String[] args) throws Exception {
        OD od = (OD_SpectralFlux) App.getContext().getBean("od_spectralFlux");
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