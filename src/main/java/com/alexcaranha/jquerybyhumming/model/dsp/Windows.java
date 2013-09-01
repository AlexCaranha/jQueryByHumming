package com.alexcaranha.jquerybyhumming.model.dsp;

/**
 *
 * @author alexcaranha
 */
public class Windows {

    public static Frame hamming(int length) {
        int N = length - 1;
        double[] windows = new double[length];

        for (int n = 0; n < length; n++){
            windows[n] = 0.54 - 0.46 * Math.cos(2 * Math.PI * n / N) ;
        }
        return new Frame(windows);
    }

    public static Frame hanning(int length) {
        int N = length - 1;
        double[] windows = new double[length];

        for (int n = 0; n < length; n++){
            windows[n] = 0.5 * (1 - Math.cos(2 * Math.PI * n / N));
        }
        return new Frame(windows);
    }
}
