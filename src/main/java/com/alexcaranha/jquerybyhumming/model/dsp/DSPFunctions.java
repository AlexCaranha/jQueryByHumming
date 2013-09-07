package com.alexcaranha.jquerybyhumming.model.dsp;

import com.alexcaranha.jquerybyhumming.model.SignalFunctions;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DSPFunctions {

    public static double midiToHertz(double value) {
        return 440 * Math.pow(2, (double) (value - 69) / 12);
    }

    public static void midiToHertz(double[] values) {
        for (int i = 0; i < values.length; i += 1) {
            values[i] = midiToHertz(values[i]);
        }
    }

    public static double hertzToMidiWithRound(double value) {
        if (value == 0.0) return 0.0;

        return 69 + Math.round(12 * Math.log(value / 440) / Math.log(2));
    }

    public static double hertzToMidiWithoutRound(double value) {
        if (value == 0.0) return 0.0;

        return 69 + 12 * Math.log(value / 440) / Math.log(2);
    }

    public static double[] synthesis(SignalXY signalXY, double frameSample) {
        // ---------------------------------------------------------------------
        List<Double> sinalSintetizado = new ArrayList<Double>();

        int    i;
        double rate = (double) 1 / frameSample;
        double argument, fase, volume = 0.5;

        double[]    arrayTempo = SignalFunctions.createArithmeticProgressionSequence(0, rate, signalXY.getLastX());
        double[]    arrayPitch = SignalFunctions.multiply(arrayTempo, 0.0);

        double      pitchAnterior, pitch, tempo, value;
        //----------------------------------------------------------------------
        for (i = 0; i < arrayTempo.length; i+= 1) {
            arrayPitch[i] = signalXY.getYFromTime(arrayTempo[i]);
        }

        for (i = 0, fase = 0.0, tempo = 0.0, pitchAnterior = -1.0;
             i < arrayTempo.length - 1;
             i+= 1, pitchAnterior = pitch) {
            //------------------------------------------------------------------
            pitch = arrayPitch[i];
            if (i == 0 || pitch != pitchAnterior) {
                tempo += rate;
                fase = 2 * Math.PI * pitchAnterior * tempo + fase;
                tempo = 0.0;
            } else {
                tempo += rate;
            }
            //------------------------------------------------------------------
            argument = 2 * Math.PI * pitch * tempo + fase;
            value    = volume * Math.sin(argument);
            sinalSintetizado.add(value);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        return Doubles.toArray(sinalSintetizado);
        // ---------------------------------------------------------------------
    }
    /*
    public static boolean createWavFile(double[] signal, double fs, String path){
        try{
            //------------------------------------------------------------------
            long        qtdFrames = (long) signal.length;

            WavFile     wavFile = WavFile.newWavFile(new File(path), 1, qtdFrames, 16, (long) fs);
            double[]    buffer = new double[100];
            long        frameCounter = 0;
            int         iAmostra = -1;
            //------------------------------------------------------------------
            // Enquanto todos os frames não forem escritos no arquivo...
            while (frameCounter < qtdFrames){
                //--------------------------------------------------------------
                // Determina quantos quadros para escrever, up to a maximum of the buffer size
                long remaining = wavFile.getFramesRemaining();
                int  toWrite   = (remaining > 100) ? 100 : (int) remaining;
                //--------------------------------------------------------------
                // Preenche o buffer
                for (int s = 0;
                     s < toWrite;
                     s++, frameCounter++){

                    buffer[s] = signal[++iAmostra];
                }
                //--------------------------------------------------------------
                // Escreve no buffer
                wavFile.writeFrames(buffer, toWrite);
                //--------------------------------------------------------------
            }
            //------------------------------------------------------------------
            // Fecha o arquivo
            wavFile.close();
            //------------------------------------------------------------------
        }catch (IOException | WavFileException e){
            System.err.println(e);
            return false;
        }
        return true;
    }
    */
    public static double energy(double[] buffer) {
        double energy = 0;

        for (int i = 0; i < buffer.length; i++) {
            energy += buffer[i] * buffer[i];
        }

        return energy;
    }

    public static double[] convolution(double[] x, double[] h) {
        double[] y = new double[x.length];
        int limit;

	int M = h.length;

        for (int n = 0; n < x.length; n += 1) {
	    y[n] = 0.0;

	    limit = Math.min(n, M - 1);
            for (int k = 0; k <= limit; k += 1) {
                y[n] += h[k] * x[n - k];
            }
        }
        return y;
    }

    public static void convolution(double[] y, double[] x, double[] h) {
        int limit;

	int M = h.length;

        for (int n = 0; n < x.length; n += 1) {
	    y[n] = 0.0;

	    limit = Math.min(n, M - 1);
            for (int k = 0; k <= limit; k += 1) {
                y[n] += h[k] * x[n - k];
            }
        }
    }

    /*
     * @args frameLength in samples
     * @args frameOverlap limit in [0,100];
     */
    public static ArrayList<Frame> convertArrayDoubleToFrames(double[] array,
                                                              int frameLength, 
                                                              double frameOverlap) throws Exception {
        // ------------------------------------------------------------------
        if (array == null) {
            throw new Exception("Array is null.");
        }
        if (array.length < 1) {
            throw new Exception("Array unfilled.");
        }
        // ------------------------------------------------------------------
        ArrayList<Frame> frames = new ArrayList<Frame>();

        double[] frame;
        double[] amplitudes = array;
        int size = amplitudes.length;

        int iOverlap = (int) (frameLength * frameOverlap / 100);
        int jump = frameLength - iOverlap;
        int iInitial = -jump;
        int iFinal;
        // ------------------------------------------------------------------
        frames.clear();
        // ------------------------------------------------------------------
        while (true) {
            iInitial += jump;
            iFinal = iInitial + frameLength;

            if (iFinal < size) {
                frame = Arrays.copyOfRange(amplitudes, iInitial, iFinal);
                frames.add(new Frame(frame));
            } else {
                break;
            }
        }
        // ------------------------------------------------------------------
        return frames;
        // ------------------------------------------------------------------
    }
}