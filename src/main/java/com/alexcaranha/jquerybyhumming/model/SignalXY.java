package com.alexcaranha.jquerybyhumming.model;

import java.util.Arrays;
import java.util.List;
import org.jfree.data.xy.XYSeries;

public class SignalXY {

    private double[] x;
    private double[] y;
    private int length;

    public SignalXY(int length) {
        this.length = length;
        this.x = new double[length];
        this.y = new double[length];

        Arrays.fill(this.x, 0.0);
        Arrays.fill(this.y, 0.0);
    }

    public SignalXY(double[] x, double y) {
        this.length = x.length;
        this.x = x;
        this.y = new double[this.length];
        for (int i = 0; i < this.length; i += 1) {
            this.y[i] = y;
        }
    }

    public SignalXY(double[] y) {
        this.length = y.length;
        this.x = SignalFunctions.createArithmeticProgressionSequence(0, 1.0, length - 1);
        this.y = y;
    }

    public SignalXY(double[] x, double[] y) {

        if (x != null && y == null) {
            this.length = x.length;
            this.x = x;
            this.y = new double[length];
            Arrays.fill(this.y, 0.0);
        }
        else
        if (x.length != y.length) {
            this.x = null;
            this.y = null;
            this.length = 0;
        }
        else {
            this.x = x;
            this.y = y;
            this.length = this.x.length;
        }
    }

    public void setX(double[] x) throws Exception {
        if (x.length != length) {
            throw new Exception("Differents lengths.");
        }
        this.x = x;
    }

    public void setY(double[] y) throws Exception {
        if (y.length != length) {
            throw new Exception("Differents lengths.");
        }
        this.y = y;
    }
    /*
    public void setY(float[] y) throws Exception {
        if (y.length != length) {
            throw new Exception("Differents lengths.");
        }
        this.y = Utils.convertFloatsToDoubles(y);
    }
    */
    public void setY(int iStart, int iEnd, double value) {
        Arrays.fill(this.y, iStart, iEnd, value);
    }

    public int getLength() {
        return this.length;
    }

    public double[] getX() {
        return this.x;
    }

    public double[] getY() {
        return this.y;
    }

    public double getYFromTime(double time) {

        for (int i = 1; i < this.length - 1; i += 1) {
            if (x[i] == time) {
                return y[i];
            } else
            if (x[i] > time) {
                return y[i - 1];
            }
        }

        return 0.0;
    }

    public double getX(int index) {
        return this.x[index];
    }

    public double getY(int index) {
        return this.y[index];
    }

    public double getFirstX() {
        return this.x[0];
    }

    public double getFirstY() {
        return this.y[0];
    }

    public double getLastX() {
        return this.x[length - 1];
    }

    public double getLastY() {
        return this.y[length - 1];
    }

    public void changeValueX(int index, double value) {
        this.x[index] = value;
    }

    public void changeValueY(int index, double value) {
        this.y[index] = value;
    }

    @Override
    public SignalXY clone() {
        SignalXY result = new SignalXY(this.length);

        for (int i = 0; i < this.length; i += 1) {
            result.getX()[i] = this.x[i];
            result.getY()[i] = this.y[i];
        }

        return result;
    }

    public XYSeries convertToXYSeries(String name) {
        XYSeries signal = new XYSeries(name);
        double[] tempo = this.getX();
        double[] amplitude = this.getY();

        for (int i = 0; i < this.length; i++) {
            //System.out.println(String.format("tempo: %.2f, amplitude: %.2f", tempo[i], amplitude[i]));
            signal.add(tempo[i], amplitude[i]);
        }

        return signal;
    }

    public static XYSeries convertToXYSeries(String name, double[] amplitude) {
        XYSeries signal = new XYSeries(name);

        for (int i = 0; i < amplitude.length; i++) {
            signal.add(i, amplitude[i]);
        }

        return signal;
    }

    public static XYSeries convertToXYSeries(String name, double[] tempo, double[] amplitude) {
        XYSeries signal = new XYSeries(name);

        for (int i = 0; i < amplitude.length; i++) {
            signal.add(tempo[i], amplitude[i]);
        }

        return signal;
    }

    public static XYSeries convertToXYSeries(String name, SignalXY signalXY) {
        XYSeries signal = new XYSeries(name);
        double[] tempo = signalXY.getX();
        double[] amplitude = signalXY.getY();
        double   x, y;

        for (int i = 0; i < signalXY.getLength(); i++) {
            x = tempo[i];
            y = amplitude[i];

            signal.add(x, y);
        }

        return signal;
    }

    public void joinSignalsFromIndices(List<SignalXY> signals) {
        if (signals == null) return;
        int size = signals.get(0).getLength();

        for (int index = 0; index < size; index += 1) {
            double sample = 0.0;

            for (int signal = 0; signal < signals.size(); signal += 1) {
                sample += signals.get(signal).getY(index);
            }

            this.changeValueY(index, sample);
        }
    }
    
    public KeyValue<Double, Double> getXRange() {
        double min = Util.min(this.x);
        double max = Util.max(this.x);
        
        return new KeyValue<Double, Double>(min, max);
    }
    
    public KeyValue<Double, Double> getYRange() {
        double min = Util.min(this.y);
        double max = Util.max(this.y);
        
        return new KeyValue<Double, Double>(min, max);
    }
}
