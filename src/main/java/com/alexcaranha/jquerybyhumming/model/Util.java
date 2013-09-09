package com.alexcaranha.jquerybyhumming.model;

import Jama.Matrix;
import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.dsp.DSPFunctions;
import com.alexcaranha.jquerybyhumming.model.wave.WavFile;
import com.alexcaranha.jquerybyhumming.model.wave.WavFileException;
import com.alexcaranha.jquerybyhumming.screen.main.Main_Presenter;
import com.google.common.primitives.Doubles;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author alexcaranha
 */
public class Util {

    public static String getDirExecution(String fileName) {
        CodeSource codeSource = App.class.getProtectionDomain().getCodeSource();
        File jarFile;
        String jarDir;

        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
            jarDir = jarFile.getParentFile().getPath();
        } catch (URISyntaxException ex) {
            jarDir = "";
        }

        if (fileName != null && !fileName.isEmpty()) {
            return jarDir + File.separator + fileName;
        }

        return jarDir;
    }

    public static InputStream getInputStreamFromPath(String path) {
        InputStream inputStream = Util.class.getResourceAsStream(path);
        return inputStream;
    }

    public static ImageIcon getImageIcon(Image img){
        if (img == null) return null;
        return new ImageIcon(img);
    }

    public static ImageIcon getImageIcon(Image img, int side){
        if (img == null) return null;
        if (side <= 0) return null;

        Image newImage = img.getScaledInstance(side, side, java.awt.Image.SCALE_SMOOTH) ;

        return new ImageIcon(newImage);
    }

    public static ImageIcon getImageIcon(Image img, int width, int height){
        if (img == null) return null;
        if (width <= 0 || height <= 0) return null;

        Image newImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH) ;

        return new ImageIcon(newImage);
    }

    public static void centering(Frame frame) {
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        int componentWidth = frame.getWidth();
        int componentHeight = frame.getHeight();

        frame.setBounds((screenSize.width - componentWidth) / 2, (screenSize.height - componentHeight) / 2,
                        componentWidth, componentHeight);
    }

    public static int showMessage(String    message,
                                  String    title,
                                  int       optionType,
                                  int       messageType,
                                  Icon      icon,
                                  Object[]  options,
                                  Object    optionDefault){

        Main_Presenter presenter = (Main_Presenter) App.getObject("main");
        Frame frame = (Frame) presenter.getView();
        
        return JOptionPane.showOptionDialog(frame,
            message,
            title,
            optionType,
            messageType,
            icon,     //do not use a custom Icon
            options,  //the titles of buttons
            optionDefault); //default button title
    }

    public static <T> T[] createArray(T... array) {
        return Arrays.copyOf(array, array.length);
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void deleteFile(String pathFileSerialized) {
        File file = new File(pathFileSerialized);
        file.delete();
    }

    public static boolean fileExist(String pathFile) {
        File file = new File(pathFile);
        return file.exists();
    }

    public static boolean valueIsOnItervalInclusive(double value, double start, double end) {
        return (value >= start && value <= end);
    }

    public static boolean valueIsOnItervalExclusive(double value, double start, double end) {
        return (value > start && value < end);
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

    public static double[] abs(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.abs(array[i]);
        }
        return array;
    }

    public static void divide(double[] buffer, double[] array, double scalar) {
        int size = array.length;

        for (int i = 0; i < size; i++) {
            buffer[i] /= scalar;
        }
    }

    public static double[] addict(double[] buffer, double[] ... arrays) {
        double[] array;

        for (int iArray = 0; iArray < arrays.length; iArray++) {
            array = arrays[iArray];

            for (int iSample = 0; iSample < array.length; iSample++) {
                buffer[iSample] += array[iSample];
            }
        }
        return buffer;
    }

    public static double[] multiply(double[] array, double scalar) {
        int size = array.length;
        double[] buffer = new double[size];

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

    public static double[] divide(double[] array, double scalar) {
        int size = array.length;

        for (int i = 0; i < size; i++) {
            array[i] /= scalar;
        }

        return array;
    }

    public static double mean(double[] array) {
        double sum = 0;

        if (array.length == 0) return 0.0;

        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum / array.length;
    }
    
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

    public static void limitPhases(double[] buffer) {
        for (int iSample = 0; iSample < buffer.length; iSample += 1) {
            while(buffer[iSample] < -Math.PI) {
                buffer[iSample] += 2 * Math.PI;
            }
            while(buffer[iSample] >= Math.PI) {
                buffer[iSample] -= 2 * Math.PI;
            }
        }
    }

    public static void createWavFile(double[] signal, double fs, String pathFile) throws IOException, WavFileException{
        //----------------------------------------------------------------------
        long        qtdFrames = (long) signal.length;
        WavFile     wavFile = WavFile.newWavFile(new File(pathFile), 1, qtdFrames, 16, (long) fs);
        double[]    buffer = new double[100];
        long        frameCounter = 0;
        int         iAmostra = -1;
        //----------------------------------------------------------------------
        // Enquanto todos os frames não forem escritos no arquivo...
        while (frameCounter < qtdFrames){
            //------------------------------------------------------------------
            // Determina quantos quadros faltam para escrever, up to a maximum of the buffer size
            long remaining = wavFile.getFramesRemaining();
            int  toWrite   = (remaining > 100) ? 100 : (int) remaining;
            //------------------------------------------------------------------
            // Preenche o buffer
            for (int s = 0; s < toWrite; s++, frameCounter++) {
                buffer[s] = signal[++iAmostra];
            }
            //------------------------------------------------------------------
            // Escreve no buffer
            wavFile.writeFrames(buffer, toWrite);
            //------------------------------------------------------------------
        }
        //----------------------------------------------------------------------
        wavFile.close();
        //----------------------------------------------------------------------
    }
    /*
    public static void createWavFileFromSynthesis(SignalXY signalXY, double fs) throws IOException, WavFileException {
        createWavFile(DSPFunctions.synthesis(signalXY, fs), fs, Constants.PATH_TMP_WAVE_FILE);
    }
    */
    public static void createWavFileFromSynthesis(SignalXY signalXY, double fs, String pathFile) throws IOException, WavFileException {
        createWavFile(DSPFunctions.synthesis(signalXY, fs), fs, pathFile);
    }
    
    public static double minDouble(List<Double> values) {
        int size = values.size();
        double valueMin = values.get(0);
        
        for (int i = 1; i < size; i++) {
            if (values.get(i) < valueMin) {
                valueMin = values.get(i);
            }
        }
        return valueMin;
    }
    
    public static double minDouble(Double ... values) {
        int size = values.length;
        double valueMin = values.length;
        
        for (int i = 1; i < size; i++) {
            if (values[i] < valueMin) {
                valueMin = values[i];
            }
        }
        return valueMin;
    }
    
    public static int minInteger(List<Integer> values) {
        int size = values.size();
        int valueMin = values.get(0);
        
        for (int i = 1; i < size; i++) {
            if (values.get(i) < valueMin) {
                valueMin = values.get(i);
            }
        }
        return valueMin;
    }
    
    public static int minInteger(int ... values) {
        int size = values.length;
        int valueMin = values[0];
        
        for (int i = 1; i < size; i++) {
            if (values[i] < valueMin) {
                valueMin = values[i];
            }
        }
        return valueMin;
    }
    
    public static Number min(String type, Number ... array) {
        int size = array.length;
        Number valueMin = array[0];

        if (type.trim().toLowerCase().equalsIgnoreCase("double")) {
            for (int i = 1; i < size; i++) {
                if (array[i].doubleValue() < valueMin.doubleValue()) {
                    valueMin = array[i];
                }
            }
        } else if (type.trim().toLowerCase().equalsIgnoreCase("int")) {
            for (int i = 1; i < size; i++) {
                if (array[i].intValue() < valueMin.intValue()) {
                    valueMin = array[i];
                }
            }
        }
        return valueMin;
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

    public static double max(double[] array) {
        int size = array.length;
        double valueMax = array[0];

        for (int i = 1; i < size; i++) {
            if (array[i] > valueMax) {
                valueMax = array[i];
            }
        }

        return valueMax;
    }

    public static KeyValue<Integer, Double> max(double[] array, int indexInitial, int indexFinal) {
        KeyValue<Integer, Double> out = null;
        for (int i = 0; i < array.length; i++) {
            if (indexInitial <= i && i <= indexFinal ) {
                if (out == null) {
                    out = new KeyValue<Integer, Double>(i, array[i]);
                } else
                if (array[i] > (double) out.getValue()) {
                    out.setKey(i);
                    out.setValue((double) array[i]);
                }
            }
        }
        return out;
    }

    public static KeyValue<Integer, Double> max(double[] array,
                                           int indexInitial, int indexFinal,
                                           int ... exceptIndices) {
        KeyValue<Integer, Double> out = null;
        for (int i = 0; i < array.length; i++) {
            //------------------------------------------------------------------
            boolean equal = false;
            for (int j = 0; j < exceptIndices.length; j += 1) {
                if (exceptIndices[j] == i) {
                    equal = true;
                    break;
                }
            }
            if (equal) continue;
            //------------------------------------------------------------------
            if (indexInitial <= i && i <= indexFinal ) {
                if (out == null) {
                    out = new KeyValue<Integer, Double>(i, array[i]);
                } else
                if (array[i] > (double) out.getValue()) {
                    out.setKey(i);
                    out.setValue((double) array[i]);
                }
            }
        }
        return out;
    }

    public static double[] derivative(double[] signal) {
        double[] buffer = new double[signal.length];
        for (int i = 0 ; i < signal.length - 1; i += 1) {
            buffer[i] = signal[i + 1] - signal[i];
        }
        return buffer;
    }

    public static double[] relativeDerivative(double[] signal) {
        double[] buffer = new double[signal.length];
        for (int i = 0 ; i < signal.length - 1; i += 1) {
            if (signal[i + 1] == 0) {
                buffer[i] = 0.0;
            }else{
                buffer[i] = (signal[i + 1] - signal[i]) / signal[i + 1];
            }
        }
        return buffer;
    }

    public static void derivativeInset(double[] signal) {
        for (int i = 0 ; i < signal.length - 1; i += 1) {
            signal[i] = signal[i + 1] - signal[i];
        }
    }

    public static KeyValue<Double, Double> estimateVertexOfParabola_Up(int index,
                                                                       double[] buffer,
                                                                       int qtdSamplesPerSide) throws Exception {

        int indexInitial = index - qtdSamplesPerSide;
        int indexFinal = index + qtdSamplesPerSide;

        if (indexInitial < 0 || indexFinal > buffer.length) {
            return null;
        }

        double[] bufferApproximationX = createArithmeticProgressionSequence((double) indexInitial, (double) +1.0, (double) indexFinal);
        double[] bufferApproximationY = Arrays.copyOfRange(buffer, indexInitial, indexFinal + 1);

        if (!IsVertexUp(bufferApproximationY)) {
            return null;
        }

        return estimateVertexOfParabola(bufferApproximationX, bufferApproximationY);
    }

    public static KeyValue estimateVertexOfParabola_Down(double[] buffer,
                                                    int indexInitial,
                                                    int indexFinal) throws Exception {

        double[] bufferApproximationX = createArithmeticProgressionSequence((double) indexInitial, (double) +1.0, (double) indexFinal);
        double[] bufferApproximationY = Arrays.copyOfRange(buffer, indexInitial, indexFinal + 1);

        if (!IsVertexDown(bufferApproximationY)) {
            return null;
        }

        return estimateVertexOfParabola(bufferApproximationX, bufferApproximationY);
    }

    public static KeyValue estimateVertexOfParabola(double[] arrayX,
                                               double[] arrayY) throws Exception {
        //----------------------------------------------------------------------
        if (arrayX == null || arrayY == null) {
            throw new Exception("ArrayX and/or ArrayY are null");
        }
        if (arrayX.length != arrayY.length) {
            throw new Exception("ArrayX and/or ArrayY contain different length");
        }
        //----------------------------------------------------------------------
        int qtd = arrayX.length;
        int linha, coluna;

        double[][] A = new double[qtd][3];
        double[][] Y = new double[qtd][1];
        double x, y;

        Matrix matA, matY, matX;
        Matrix matAt, matAtA, matAtAi, matAtY;
        double c, b, a, delta;
        //----------------------------------------------------------------------
        for (linha = 0; linha < qtd; linha++) {
            Y[linha][0] = arrayY[linha];
        }
        //----------------------------------------------------------------------
        for (coluna = 0; coluna < 3; coluna++) {
            if (coluna == 0) {
                for (linha = 0; linha < qtd; linha++) {
                    A[linha][coluna] = 1;
                }
            } else {
                for (linha = 0; linha < qtd; linha++) {
                    A[linha][coluna] = Math.pow(arrayX[linha], (double) coluna);
                }
            }
        }
        //----------------------------------------------------------------------
        matA = new Matrix(A);
        matY = new Matrix(Y);

        matAt = matA.transpose();
        matAtA = matAt.times(matA);
        matAtY = matAt.times(matY);

        matAtAi = matAtA.inverse();
        matX = matAtAi.times(matAtY);
        //----------------------------------------------------------------------
        c = matX.get(0, 0);
        b = matX.get(1, 0);
        a = matX.get(2, 0);
        delta = b * b - 4 * a * c;

        x = -b / (2 * a);
        y = -delta / (4 * a);
        //----------------------------------------------------------------------
        return new KeyValue<Double, Double>(x, y);
        //----------------------------------------------------------------------
    }

    public static boolean IsVertexDown(double[] array) {
        int size = array.length;

        int indexCentral = (size - 1) / 2;
        int samplesPerSide = indexCentral;

        for (int i = 1; i <= samplesPerSide; i++) {
            if (!(array[indexCentral - i] > array[indexCentral] &&
                  array[indexCentral] < array[indexCentral + i])) {

                return false;
            }
        }

        return true;
    }

    public static boolean IsVertexUp(double[] array) {
        int size = array.length;

        int indexCentral = (size - 1) / 2;
        int samplesPerSide = indexCentral;

        for (int i = 1; i <= samplesPerSide; i++) {
            if (!(array[indexCentral - i] < array[indexCentral]
                    && array[indexCentral] > array[indexCentral + i])) {

                return false;
            }
        }

        return true;
    }

    public static DefaultXYDataset convertToXYDataSet(XYSeries... series) {
        DefaultXYDataset dataSet = new DefaultXYDataset();
        for (XYSeries serie : series) {
            dataSet.addSeries(serie.getKey(), serie.toArray());
        }
        return dataSet;
    }

    public static Map<String, Object> createMap(KeyValue<String, Object> ... values) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (KeyValue<String, Object> value : values) {
            result.put(value.getKey(), value.getValue());
        }
        return result;
    }

    public static void findVertices_Up(double[] buffer,
                                       int indexInitial, int indexFinal,
                                       List<KeyValue> listVertex) {
        double[] values = new double[3];

        listVertex.clear();
        for(int index = 1; index < buffer.length - 1; index += 1) {
            if (indexInitial <= index && index <= indexFinal ) {
                values[0] = buffer[index - 1];
                values[1] = buffer[index];
                values[2] = buffer[index + 1];

                if (IsVertexUp(values)) {
                    listVertex.add(new KeyValue<Integer, Double>(index, values[1]));
                }
            }
        }
    }

    /* Restrição: n precisa ser de valor ímpar. */
    public static double[] movingAverage(double[] array, int n, double dc) {
        double[] arrayResult = new double[array.length];

        int indexLeft, indexRight;
        int samplesPerSide = (n - 1) / 2;

        for (int index = 0; index < array.length; index += 1) {
            indexLeft  = index - samplesPerSide;
            indexRight = index + samplesPerSide;

            indexLeft  = (indexLeft < 0) ? 0 : indexLeft;
            indexRight = (indexRight >= array.length) ? (array.length - 1) : indexRight;

            arrayResult[index] = Util.average(array, indexLeft, indexRight + 1) + dc;
        }

        return arrayResult;
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

    public static void normalizeByMaximum(double[] array) {
        double maximum = max(array);
        divide(array, maximum);
    }

    public static void thresholding(double[] buffer, double threshold, double value) {
        for(int i = 0; i < buffer.length; i += 1) {
            buffer[i] = (buffer[i] < threshold) ? value : buffer[i];
        }
    }

    public static void unwrap_phases(double[][] phases, int linhas, int colunas) {
        for(int col = 0; col < colunas; col += 1) {
            for(int lin = 1; lin < phases.length; lin += 1) {
                if (phases[lin][col] - phases[lin-1][col] >= Math.PI) {
                    phases[lin][col] -= 2 * Math.PI;
                    continue;
                }
                if (phases[lin][col] - phases[lin-1][col] <= Math.PI) {
                    phases[lin][col] += 2 * Math.PI;
                }
            }
        }
    }

    public static void maximizing(double[] array, double maximum) {
        for (int index = 0; index < array.length; index += 1) {
            array[index] = (array[index] > maximum) ? maximum : array[index];
        }
    }

    public static double[] sortByValueDesc(double[] array, int n) {
        int      lastIndex  = array.length - 1;
        double[] arrayLocal = array.clone();

        Arrays.sort(arrayLocal);

        double[] arrayResult = new double[n];

        for (int index = 0; index < n; index += 1) {
            arrayResult[index] = arrayLocal[lastIndex - index];
        }

        return arrayResult;
    }

    public static boolean existFile(String path) {
        File file = new File(path);
        return file.exists();
    }
    
    public static Object getItemByToString(Object[] array, String selected) {
        for(Object item : array) {
            if (item.toString().equalsIgnoreCase(selected)) {
                return item;
            }
        }
        return null;
    }
}