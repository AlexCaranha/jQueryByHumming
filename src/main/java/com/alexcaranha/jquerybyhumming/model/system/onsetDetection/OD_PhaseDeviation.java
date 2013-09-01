package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.dsp.Frame;
import com.alexcaranha.jquerybyhumming.model.dsp.Windows;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class OD_PhaseDeviation extends OD {

    public OD_PhaseDeviation(int sampleRate, int frameLength, double overlap,
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
        Frame       frame, window = Windows.hamming(this.getFrameLength());

        double[][]  phases = new double[frames.size()][frameLength];
        double[]    buffer = new double[frameLength];

        ComplexDoubleFFT fft = new ComplexDoubleFFT(frameLength);
        //----------------------------------------------------------------------
        for (iFrame = 0; iFrame < frames.size(); iFrame += 1) {
            frame = frames.get(iFrame);
            frame.multiplyInline(window);
            phases[iFrame] = frame.fftInline(fft).getPhase();
        }
        //----------------------------------------------------------------------
        Util.unwrap_phases(phases, frames.size(), frameLength);
        //----------------------------------------------------------------------
        for (iFrame = 2; iFrame < sizeFrames; iFrame += 1) {
            //------------------------------------------------------------------
            double[] signal_A = phases[iFrame];
            double[] signal_B = phases[iFrame - 1];
            double[] signal_C = phases[iFrame - 2];
            //------------------------------------------------------------------
            Arrays.fill(buffer, 0.0);
            Util.addict(buffer, signal_A, Util.multiply(signal_B, -2), signal_C);
            Util.limitPhases(buffer);
            //------------------------------------------------------------------
            ///*
            // Way 1 - [Mean]
            value = Util.mean(Util.abs(buffer));
            //*/
            //------------------------------------------------------------------
            // Way 2 - [IQR]
            /*
            List<Double> superior = new ArrayList();
            List<Double> inferior = new ArrayList();

            double       mean = Utils.mean(buffer);
            double       sample;

            double superiorMediana = 0.0;
            double inferiorMediana = 0.0;

            for (int iSample = 0; iSample < this.frameLength; iSample += 1) {
                sample = buffer[iSample];
                if (sample >= mean) {
                    superior.add(sample);
                } else {
                    inferior.add(sample);
                }
            }

            if (superior.size() > 0) {
                superiorMediana = Utils.median(Doubles.toArray(superior));
            }
            if (inferior.size() > 0) {
                inferiorMediana = Utils.median(Doubles.toArray(inferior));
            }

            value = superiorMediana - inferiorMediana;
            value = value / (2 * Math.PI);
            */
            //------------------------------------------------------------------
            signalXYProcessed.changeValueY(iFrame, value);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        return signalXYProcessed;
    }

    @Override
    public String getTitle() {
        return "PHASE DEVIATION";
    }

    @Override
    public String getAlias() {
        return "PD";
    }

    @Override
    public boolean isGroup() {
        return false;
    }
    
    /*
    public static void main(String[] args) throws Exception {
        OD od = (OD_PhaseDeviation) App.getContext().getBean("od_phaseDeviation");
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