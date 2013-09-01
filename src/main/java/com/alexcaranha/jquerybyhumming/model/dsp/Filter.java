package com.alexcaranha.jquerybyhumming.model.dsp;

import com.alexcaranha.jquerybyhumming.model.SignalFunctions;

/**
 *
 * @author alexcaranha
 */
public class Filter {

    public static double[] filter(double[] b, double[] a, double[] x) {
        double[] y = new double[x.length];

        int N = a.length;
        int M = b.length;
        int limit;

        for (int n = 0; n < x.length; n++) {
            y[n] = 0.0;

            limit = Math.min(n, M - 1);
            for (int k = 0; k <= limit; k++) {
                y[n] += b[k] * x[n - k];
            }

            limit = Math.min(n, N - 1);
            for (int k = 1; k <= limit; k++) {
                y[n] -= a[k] * x[n - k];
            }
        }

        return y;
    }

    public static void median(double[] x, int n) throws Exception {

        int size = x.length;

        if (size <= 0) {
            throw new Exception("Frame not contains samples.");
        }
        if (n % 2 == 0) {
            throw new Exception("N need to be odd.");
        }

        //double[] y = x.clone();
        int samplesForSide = (n - 1) / 2;
        int index, i, count;
        double[] array = new double[n];

        for (int sample = 0; sample < size - n; sample += 1) {
            for (count = 0, i = sample - samplesForSide;
                 i <= sample + samplesForSide;
                 count += 1, i += 1) {

                index = (i >= 0 && i <= size - 1) ? i : sample;
                array[count] = x[index];
            }
            x[sample] = SignalFunctions.median(array);
        }
    }

    /*
    public static Frame average(Frame frame, int n) throws Exception {
        //----------------------------------------------------------------------
        if (frame.getLength() <= 0) {
            throw new Exception("Frame not contains samples.");
        }
        if (n % 2 == 0) {
            throw new Exception("N need to be odd.");
        }
        //----------------------------------------------------------------------
        int samplesForSide = (n - 1) / 2;
        int index, i, count;
        Frame array = new Frame(n);
        Frame result = new Frame(frame.getLength());

        for (int sample = 0; sample < frame.getLength() - n; sample += 1) {
            for (count = 0, i = sample - samplesForSide;
                 i <= sample + samplesForSide;
                 count += 1, i += 1) {

                index = (i >= 0 && i <= frame.getLength() - 1) ? i : sample;
                array.setSample(count, frame.getSample(index));
            }
            result.setSample(sample, SignalFunctions.average(array.getSamples()));
        }
        //----------------------------------------------------------------------
        return result;
        //----------------------------------------------------------------------
    }
    */
}
