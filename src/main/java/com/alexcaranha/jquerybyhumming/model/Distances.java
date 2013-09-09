package com.alexcaranha.jquerybyhumming.model;

/**
 *
 * @author alexcaranha
 */
public class Distances {
    // reference: http://en.wikipedia.org/wiki/Distance
    
    public static double euclidian(double x[], double []y, int nDimension, int pNorm) {
        double sum = 0;
        double distance;
        
        for (int i = 0; i < nDimension; i++){
            double value = Math.pow(Math.abs(x[i] - y[i]), pNorm);
            sum += value;            
        }

        distance = Math.pow(sum, 1 / pNorm);

        return distance;
    }
    
    public static double euclidian(double x, double y) {
        return Math.abs(x - y);
    }
}
