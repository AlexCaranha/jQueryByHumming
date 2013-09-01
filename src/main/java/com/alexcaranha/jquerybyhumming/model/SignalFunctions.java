package com.alexcaranha.jquerybyhumming.model;

import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class SignalFunctions {

    public static double median(double[] array) {
        int size = array.length;
        int i, j;

        Arrays.sort(array);

        if (size % 2 == 0) {
            j = size / 2;
            i = j - 1;
        } else {
            i = (size - 1) / 2;
            j = i;
        }

        return (array[i] + array[j]) / 2;
    }

    public static double average(double[] array) {
        int size = array.length;
        double sum = 0;

        for (int i = 0; i < size; i++) {
            sum += array[i];
        }

        return sum / size;
    }

    private static double average(double[] array, int indexLeft, int indexRight) {
        int    qtd = 0;
        double sum = 0.0;

        for (int i = indexLeft; i < indexRight; i += 1) {
            qtd += 1;
            sum += array[i];
        }

        return sum / qtd;
    }

    public static double[] createArithmeticProgressionSequence(double valueInitial,
                                                               double fator,
                                                               double valueFinal) {

        ArrayList<Double> sequence = new ArrayList<Double>();

        for (double i = valueInitial; i <= valueFinal; i += fator) {
            sequence.add(i);
        }

        return Doubles.toArray(sequence);
    }

    public static double[] createArithmeticProgressionSequence(double valueInitial,
                                                               int N,
                                                               double valueFinal) {

        // valueFinal = valueInitial + fator * (N-1);
        // valueFinal - valueInitial = fator * (N-1);
        // fator = (valueFinal - valueInitial) / (N-1);

        ArrayList<Double> sequence = new ArrayList<Double>();
        double value;
        double fator;

        fator = (valueFinal - valueInitial) / (N - 1);

        for (int n = 0; n < N; n += 1) {
            value = valueInitial + fator * n;
            sequence.add(value);
        }

        return Doubles.toArray(sequence);
    }

    public static double min(double[] array) {
        int size = array.length;
        double valueMin = array[0];

        for (int i = 1; i < size; i++) {
            if (array[i] < valueMin) {
                valueMin = array[i];
            }
        }

        return valueMin;
    }

    public static void min(double[] array, KeyValue result) {
        int size = array.length;
        double valueMin = array[0];
        int    indexMin = 0;

        for (int i = 1; i < size; i++) {
            if (array[i] < valueMin) {
                indexMin = i;
                valueMin = array[i];
            }
        }

        result.setKey(indexMin);
        result.setValue(valueMin);
    }

    public static KeyValue minValue_Double(List<KeyValue> list) {

        if (list.size() == 0) return null;

        int size = list.size();
        KeyValue itemMin = list.get(0);

        for (KeyValue item : list) {
            if (Convert.toDouble(item.getValue()) < Convert.toDouble(itemMin.getValue())) {
                itemMin = item;
            }
        }

        return itemMin;
    }

    public static double[] multiply(double[] array, double scalar) {
        int size = array.length;
        double[] buffer = new double[size];

        for (int i = 0; i < size; i++) {
            buffer[i] = array[i] * scalar;
        }
        return buffer;
    }

    public static double[] multiply(double[] buffer, double[] array, double scalar) {
        int size = array.length;

        for (int i = 0; i < size; i++) {
            buffer[i] = array[i] * scalar;
        }
        return buffer;
    }

    public static double[] multiply(double[] buffer, double[] array1, double[] array2) {

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = array1[i] * array2[i];
        }

        return buffer;
    }
}
