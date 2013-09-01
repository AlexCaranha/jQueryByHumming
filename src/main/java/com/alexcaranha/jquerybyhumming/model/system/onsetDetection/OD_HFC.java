package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class OD_HFC extends OD {

    public OD_HFC(int sampleRate, int frameLength, double overlap,
                           int pointsToMovingAverage, double durationThreshold) {

        super(sampleRate, frameLength, overlap,
              pointsToMovingAverage, durationThreshold);
    }

    @Override
    public SignalXY processing(SignalXY signalXY) throws Exception {
        //----------------------------------------------------------------------
        boolean     debug = false;
        double      value, overlap = this.getOverlap();
        double      HFCr, HFCrPrev, Er, numerador, denominador;

        int         frameLength = this.getFrameLength();
        List<Frame> frames = DSPFunctions.convertArrayDoubleToFrames(signalXY.getY(), frameLength, overlap);
        int         iFrame, sizeFrames = frames.size();

        SignalXY    signalXYProcessed = new SignalXY(frames.size());
        signalXYProcessed.setX(Util.createArithmeticProgressionSequence(0.0, frames.size(), signalXY.getLastX()));
        //----------------------------------------------------------------------
        Frame       frame, window = Windows.hamming(this.getFrameLength());

        double[][]  modules = new double[frames.size()][frameLength];

        ComplexDoubleFFT fft = new ComplexDoubleFFT(frameLength);
        //----------------------------------------------------------------------
        for (iFrame = 0; iFrame < sizeFrames; iFrame += 1) {
            frame = frames.get(iFrame);
            frame.multiplyInline(window);

            modules[iFrame] = frame.fftOffline(fft).getModule();
        }

        //----------------------------------------------------------------------
        for (iFrame = 1; iFrame < sizeFrames; iFrame += 1) {
            //------------------------------------------------------------------
            HFCr = HF(modules[iFrame]);
            HFCrPrev = HF(modules[iFrame-1]);
            Er = DSPFunctions.energy(modules[iFrame]);
            //------------------------------------------------------------------
            numerador   = HFCr * HFCr;
            denominador = HFCrPrev * Er;

            if (denominador == 0) {
                numerador = 0;
                denominador = 1;
            }
            value = numerador / denominador;
            //------------------------------------------------------------------
            signalXYProcessed.changeValueY(iFrame, value);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        // Define o limiar a partir da ordenação dos valores.
        ///*
        double[] arraySorted = Util.sortByValueDesc(signalXYProcessed.getY(), 2);
        double   limiar = arraySorted[1];
        Util.maximizing(signalXYProcessed.getY(), limiar);
        //*/
        //----------------------------------------------------------------------
        return signalXYProcessed;
        //----------------------------------------------------------------------
    }

    private double HF(double[] frame) {
        double  value = 0;
        int     i, w;
        int     high;

        for (i = 2, high = frame.length; i < frame.length; i += 1, high -= 1) {
            w = Math.min(i, high);
            value += w * (Math.pow(frame[i], 2.0));
        }
        return value;
    }

    @Override
    public String getTitle() {
        return "HIGH FREQUENCY CONTENT";
    }

    @Override
    public String getAlias() {
        return "HFC";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
    
    /*
    public static void main(String[] args) throws Exception {
        OD od = (OD_HFC) App.getContext().getBean("od_highFrequencyContent");
        if (od != null) {
            WavSignal signal = new WavSignal();
            signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_piano.wav").getInputStream())));
            //signal.loadFromWavFile(Util.createMap(new KeyValue<String, Object>("inputStream", App.getContext().getResource("classpath:samples/wav/parabens_solfejo.wav").getInputStream())));
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