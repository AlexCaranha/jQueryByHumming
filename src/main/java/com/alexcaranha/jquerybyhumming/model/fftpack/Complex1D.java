package com.alexcaranha.jquerybyhumming.model.fftpack;

import java.util.Arrays;

/**
  * Construct a 1-D complex data sequence.
*/
public final class Complex1D
{
    //--------------------------------------------------------------------------
    // X is the real part of <em>i</em>-th complex data.
    public double x[];

    // Y is the imaginary part of <em>i</em>-th complex data.
    public double y[];
    //--------------------------------------------------------------------------
    public Complex1D() {
        this.x = null;
        this.y = null;
    }

    public Complex1D(int n) {
        this.x = new double[n];
        this.y = new double[n];
        this.clear();
    }

    public Complex1D(double[] x) {
        this.x = x;
        this.y = new double[x.length];
        Arrays.fill(this.y, 0.0);
    }

    public Complex1D(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public void clear() {
        Arrays.fill(this.x, 0.0);
        Arrays.fill(this.y, 0.0);
    }

    public int length() throws Exception {
        if (this.x.length != this.y.length)
            throw new Exception("Size of array x and size of y are differents.");

        return this.x.length;
    }

    public void setCoordinate(int index, double valueX, double valueY) {
        this.x[index] = valueX;
        this.y[index] = valueY;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[] getModule() {
        double[] result = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            result[i] = Math.sqrt(this.x[i] * this.x[i] + this.y[i] * this.y[i]);
        }

        return result;
    }

    public double[] getPhase() {
        double[] result = new double[x.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = Math.atan2(y[i], x[i]);
        }

        return result;
    }

    public double[] calculateModuloWithExpoent(double expoent) {
        double[] array = new double[x.length];

        for (int i = 0; i < x.length; i++) {
            array[i] = (double) Math.pow(Math.sqrt(this.x[i] * this.x[i] + this.y[i] * this.y[i]), expoent);
        }
        return array;
    }

    public void changeToNewValuesOf(double[] module, double[] phase) {

        for (int i = 0; i < x.length; i++) {
            x[i] = (double) module[i] * Math.cos(phase[i]);
            y[i] = (double) module[i] * Math.sin(phase[i]);
        }
    }

    public Complex1D calculateLog(double eps) {
        Complex1D data = new Complex1D(x.length);

        for (int i = 0; i < x.length; i++) {
            data.x[i] = Math.log(this.x[i] + eps);
            data.y[i] = 0.0;
        }
        return data;
    }

    public Complex1D calculateLog() {
        Complex1D data = new Complex1D(x.length);

        for (int i = 0; i < x.length; i++) {
            data.x[i] = Math.log(this.x[i]);
            data.y[i] = 0.0;
        }
        return data;
    }

    public double distance(Complex1D point) {

        double real, imag, value = 0.0;

        for (int i = 0; i < x.length; i++) {
            real = this.x[i] - point.x[i];
            imag = this.y[i] - point.y[i];

            value += Math.sqrt(real * real + imag * imag);
        }

        return value;
    }

    public void distancesAbs(Complex1D point, double[] distances) {
        double real, imag;

        Arrays.fill(distances, 0.0);
        for (int i = 0; i < x.length; i++) {
            real = this.x[i] - point.x[i];
            imag = this.y[i] - point.y[i];

            distances[i] = Math.sqrt(real * real + imag * imag);
        }
    }

    @Override
    public Complex1D clone() {
        Complex1D data = new Complex1D(x.length);

        for (int i = 0; i < x.length; i++) {
            data.x[i] = this.x[i];
            data.y[i] = this.y[i];
        }
        return data;
    }

    public void changeTo(int index, double real, double imag) {
        this.x[index] = real;
        this.y[index] = imag;
    }
}
