package com.alexcaranha.jquerybyhumming.model.dsp;

import com.alexcaranha.jquerybyhumming.model.fftpack.Complex1D;
import com.alexcaranha.jquerybyhumming.model.fftpack.ComplexDoubleFFT;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 *
 * @author alexcaranha
 */
public final class Frame {
    private Complex1D samples;

    public Frame(int length) {
        this.samples = new Complex1D(length);
    }

    public void clear() {
        this.samples.clear();
    }

    public Frame(double[] x) {
        this.samples = new Complex1D(x);
    }

    public Frame(Complex1D samples) {
        this.samples = samples;
    }

    public int length() throws Exception {
        return this.samples.length();
    }

    public void setSample(int index, double x, double y) {
        this.samples.setCoordinate(index, x, y);
    }

    public void setSample(int index, Complex coordinate) {
        this.samples.setCoordinate(index, coordinate.getReal(), coordinate.getImaginary());
    }

    public Complex getSample(int index) {
        return new Complex(this.samples.x[index], this.samples.y[index]);
    }

    public double getSampleReal(int index) {
        return this.samples.x[index];
    }

    public double getSampleImaginary(int index) {
        return this.samples.y[index];
    }

    public Complex1D getSamples() {
        return this.samples;
    }

    public double[] getRealSamples() {
        return this.samples.x;
    }

    public double[] getImaginarySamples() {
        return this.samples.y;
    }

    public double[] getModule() {
        return this.samples.getModule();
    }

    public double[] getPhase() {
        return this.samples.getPhase();
    }

    public double[] getModuloSamples() {
        return this.samples.getModule();
    }

    @Override
    public Frame clone() {
        return new Frame(this.samples.clone());
    }

    public static Complex[] fft(double[] samples) {
        FastFourierTransformer fourier = new FastFourierTransformer(DftNormalization.STANDARD);
        return fourier.transform(samples, TransformType.FORWARD);
    }

    public Frame fftInline(ComplexDoubleFFT fft) {
        fft.ft(this.samples);
        return this;
    }

    public Complex1D fftOffline(ComplexDoubleFFT fft) {
        Complex1D samplesAux = this.samples.clone();
        fft.ft(samplesAux);
        return samplesAux;
    }

    public void ifftInline(ComplexDoubleFFT fft) {
        fft.btNormalized(this.samples, 14);
    }

    public Complex1D ifftOffline(ComplexDoubleFFT fft) {
        Complex1D samplesAux = this.samples.clone();
        fft.btNormalized(samplesAux, 14);
        return samplesAux;
    }

    public Frame multiplyInline(Frame frame) throws Exception {
        if (this.length() != frame.length()) {
            throw new Exception("Different sizes.");
        }
        int lengthSamples = this.samples.length();
        for (int index = 0; index < lengthSamples; index += 1) {
            // z1 = a + bi
            // z2 = c + di
            // z1 . z2 = (ac + adi + bci + bdi^2) = (ac - bd) + (ad + bc)i
            // z1 . z2 = (ac - bd) + (ad + bc)i.
            double a = this.samples.x[index]; // real;
            double b = this.samples.y[index]; // imaginary;

            double c = frame.samples.x[index];// real;
            double d = frame.samples.y[index];// imaginary;

            double valueX = (a*c - b*d);
            double valueY = (a*d + b*c);

            this.samples.setCoordinate(index, valueX, valueY);
        }
        return this;
    }

    public double energy() {
        return DSPFunctions.energy(this.getRealSamples());
    }
}
