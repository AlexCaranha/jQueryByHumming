package com.alexcaranha.jquerybyhumming.model.dsp;

/**
 *
 * @author alexcaranha
 */
public class Distances {
    public static double euclidian(double x[], double []y) {
        double sizeX = x.length;
        double sizeY = y.length;

        if (sizeX != sizeY) return -1;

        double sum = 0;
        double distance;
        for (int i = 0; i < sizeX; i++){
            sum += Math.pow(x[i] - y[i], 2.0);
        }

        distance = Math.sqrt(sum);

        return distance;
    }
}
