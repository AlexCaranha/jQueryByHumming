package com.alexcaranha.jquerybyhumming.model;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author alexcaranha
 */
public class Signal extends Observable {

    public static enum BasicStates {
        FREE, LOCKED;
    };

    protected String    _name;
    protected SignalXY  _signalXY;
    protected BasicStates     _state;

    public Signal(String name) {
        this._name = name;
        this._state = BasicStates.FREE;
        this._signalXY = null;
    }

    public Signal(String name, Observer observer) {
        this(name);
        this.addObserver(observer);
    }

    public String getName() {
        return this._name;
    }

    public SignalXY getSignalXY() {
        return _signalXY;
    }
    /*
    public void loadFromCSVFileWithoutException(String path) {
        try {
            loadFromCSVFile(path);
        } catch (Exception ex) {
            Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    /**
     * Load signal from csv file.
     *
     * @param path Path of file.
     * @throws Exception
     */
    /*
    public void loadFromCSVFile(String path) throws Exception {
        ArrayList<Double> tempo;
        ArrayList<Double> amplitude;

        tempo = new ArrayList<>();
        amplitude = new ArrayList<>();

        int cont = 0;

        _state = BasicStates.LOCKED;
        try {
            CsvReader data = new CsvReader(path);

            if (data.getHeaderCount() > 0) {
                data.readHeaders();
            }

            while (data.readRecord()) {
                cont += 1;
                tempo.add(Double.valueOf(data.get(0)));
                amplitude.add(Double.valueOf(data.get(1)));
            }
            data.close();

        } finally {
            _state = BasicStates.FREE;
        }

        this._signalXY = new SignalXY(cont);
        this._signalXY.setX(Doubles.toArray(tempo));
        this._signalXY.setY(Doubles.toArray(amplitude));
    }
    */
    public XYSeries convertToXYSeries() {
        XYSeries signal = new XYSeries(this._name);
        double[] tempo = this._signalXY.getX();
        double[] amplitude = this._signalXY.getY();

        for (int i = 0; i < this._signalXY.getLength(); i++) {
            signal.add(tempo[i], amplitude[i]);
        }

        return signal;
    }

    public DefaultXYDataset convertToXYDataSet() {
        DefaultXYDataset dataSet = new DefaultXYDataset();
        XYSeries signal = this.convertToXYSeries();

        dataSet.addSeries(signal.getKey(), signal.toArray());

        return dataSet;
    }

    public static XYSeries convertToXYSeries(String name, double[] amplitude) {
        XYSeries signal = new XYSeries(name);

        for (int i = 0; i < amplitude.length; i++) {
            signal.add(i, amplitude[i]);
        }

        return signal;
    }

    public static XYSeries convertToXYSeries(String name, double[] tempos, double[] amplitudes) {
        XYSeries signal = new XYSeries(name);

        for (int i = 0; i < amplitudes.length; i++) {
            signal.add(tempos[i], amplitudes[i]);
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

    public static DefaultXYDataset convertToXYDataSet(XYSeries ... series) {
        DefaultXYDataset dataSet = new DefaultXYDataset();
        for (XYSeries serie : series) {
            dataSet.addSeries(serie.getKey(), serie.toArray());
        }
        return dataSet;
    }

    public static DefaultXYDataset convertToXYDataSet(double[] ... array) {
        DefaultXYDataset dataSet = new DefaultXYDataset();

        XYSeries[] series = convertToXYSeries(array);
        for (XYSeries serie : series) {
            dataSet.addSeries(serie.getKey(), serie.toArray());
        }

        return dataSet;
    }

    public static XYSeries[] convertToXYSeries(double[] ... series) {
        XYSeries[] listSeries = new XYSeries[series.length];
        for (int index = 0; index < series.length; index += 1) {
            double[] serie = series[index];
            XYSeries xySerie = convertToXYSeries(String.format("serie_%d", index), serie);
            listSeries[index] = xySerie;
        }
        return listSeries;
    }
    /*
    public static DefaultXYDataset convertToXYDataSet(double[] ... array) {
        DefaultXYDataset dataSet = new DefaultXYDataset();

        XYSeries[] series = convertToXYSeries(array);
        for (XYSeries serie : series) {
            dataSet.addSeries(serie.getKey(), serie.toArray());
        }

        return dataSet;
    }
    */
    public static DefaultXYDataset convertToXYDataSet(List<XYSeries> series) {
        DefaultXYDataset dataSet = new DefaultXYDataset();
        for (XYSeries serie : series) {
            dataSet.addSeries(serie.getKey(), serie.toArray());
        }
        return dataSet;
    }
}
