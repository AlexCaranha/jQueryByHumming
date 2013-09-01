package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.Complex1D;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class OD_ComplexDomain extends OD {

    public OD_ComplexDomain(int sampleRate, int frameLength, double overlap,
                            int pointsToMovingAverage, double durationThreshold) {

        super(sampleRate, frameLength, overlap, pointsToMovingAverage, durationThreshold);
    }

    @Override
    public SignalXY processing(SignalXY signalXY) throws Exception {
        //----------------------------------------------------------------------
        boolean     debug = false;
        double      value, overlap = this.getOverlap();

        int         frameLength = this.getFrameLength();
        List<Frame> frames = DSPFunctions.convertArrayDoubleToFrames(signalXY.getY(), frameLength, overlap);
        int         iFrame, sizeFrames = frames.size();

        SignalXY    signalXYProcessed = new SignalXY(frames.size());
        signalXYProcessed.setX(Util.createArithmeticProgressionSequence(0.0, frames.size(), signalXY.getLastX()));
        //----------------------------------------------------------------------
        Frame       frame, window = Windows.hamming(this.getFrameLength());

        Complex1D[] dataFFT = new Complex1D[frames.size()];
        Complex1D   dataBuffer;

        double[][]  modules = new double[frames.size()][frameLength];
        double[][]  phases = new double[frames.size()][frameLength];

        double[]    buffer  = new double[frameLength];
        double[]    target_module, target_phase;

        ComplexDoubleFFT fft = new ComplexDoubleFFT(frameLength);
        //----------------------------------------------------------------------
        for (iFrame = 0; iFrame < frames.size(); iFrame += 1) {
            frame = frames.get(iFrame);
            frame.multiplyInline(window);

            dataBuffer = frame.fftOffline(fft);

            modules[iFrame] = dataBuffer.getModule();
            phases[iFrame] = dataBuffer.getPhase();

            dataFFT[iFrame] = dataBuffer;
        }
        //----------------------------------------------------------------------
        Util.unwrap_phases(phases, frames.size(), frameLength);
        //----------------------------------------------------------------------
        for (iFrame = 2, dataBuffer = new Complex1D(frameLength);
             iFrame < sizeFrames;
             iFrame += 1) {
            //------------------------------------------------------------------
            double[] signal_B = phases[iFrame - 1];
            double[] signal_C = phases[iFrame - 2];
            //------------------------------------------------------------------
            target_module = modules[iFrame - 1];
            //------------------------------------------------------------------
            // target_phase = 2 * phases[iFrame-1] - phases[iFrame-2];
            target_phase = Util.addict(buffer, Util.multiply(signal_B, 2), Util.multiply(signal_C, -1));
            //------------------------------------------------------------------
            dataBuffer.changeToNewValuesOf(target_module, target_phase);
            //------------------------------------------------------------------
            value = dataBuffer.distance(dataFFT[iFrame]);
            //------------------------------------------------------------------
            signalXYProcessed.changeValueY(iFrame, value);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        return signalXYProcessed;
    }

    @Override
    public String getTitle() {
        return "COMPLEX DOMAIN";
    }

    @Override
    public String getAlias() {
        return "CD";
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    /*
    public static void main(String[] args) throws Exception {
        OD od = (OD_ComplexDomain) App.getContext().getBean("od_complexDomain");
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