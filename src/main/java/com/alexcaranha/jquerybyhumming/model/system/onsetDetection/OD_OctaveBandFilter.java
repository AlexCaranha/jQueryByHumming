package com.alexcaranha.jquerybyhumming.model.system.onsetDetection;

import com.alexcaranha.jquerybyhumming.App;
import com.alexcaranha.jquerybyhumming.model.Figure;
import com.alexcaranha.jquerybyhumming.model.SignalXY;
import com.alexcaranha.jquerybyhumming.model.Util;
import com.alexcaranha.jquerybyhumming.model.dsp.Filter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexcaranha
 */
public class OD_OctaveBandFilter {

    private String pathXMLFile = "configuration/octaveBandFilter_OnsetDetection.xml";

    private class ItemFilter {
        private double f1, f2;
        private String a, b;

        public double[] getA() {
            if (a == null || a.isEmpty()) {
                return null;
            }

            String[] tokens = a.split(", ");
            double[] result = new double[tokens.length];

            for (int index = 0; index < tokens.length; index += 1) {
                result[index] = Double.valueOf(tokens[index]);
            }

            return result;
        }

        public double[] getB() {
            if (b == null || b.isEmpty()) {
                return null;
            }

            String[] tokens = b.split(", ");
            double[] result = new double[tokens.length];

            for (int index = 0; index < tokens.length; index += 1) {
                result[index] = Double.valueOf(tokens[index]);
            }

            return result;
        }
    }

    private List<ItemFilter> loadFromXML() throws IOException {
        List<ItemFilter> filters;

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("filter", ItemFilter.class);
        xstream.alias("octaveBandFilter", List.class);

        filters = (List<ItemFilter>) xstream.fromXML(App.getContext().getResource(this.pathXMLFile).getInputStream());

        return filters;
    }

    public List<SignalXY> process(double[] tempo, double[] amplitudes) throws IOException {

        boolean             debug = false;
        List<ItemFilter>    filters = loadFromXML();
        List<SignalXY>      signals = new ArrayList<SignalXY>();
        double[]            filtered;

        for(ItemFilter filter : filters) {
            double[] a = filter.getA();
            double[] b = filter.getB();

            filtered = Filter.filter(b, a, amplitudes);
            SignalXY signal = new SignalXY(tempo, filtered);

            if (debug) {
                Figure.save("", "tempo (s)", "amplitude",
                            false,
                            Util.createArray(SignalXY.convertToXYSeries("signal", signal)),
                            Util.createArray(Color.black),
                            null,
                            null, null,
                            new Figure(500, 300),
                            Util.getDirExecution("frame.pdf"),
                            Util.getDirExecution("frame.png"));
            }

            signals.add(signal);
        }

        return signals;
    }
}
