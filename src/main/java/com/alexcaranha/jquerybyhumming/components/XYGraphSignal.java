package com.alexcaranha.jquerybyhumming.components;

import com.alexcaranha.jquerybyhumming.model.Convert;
import com.alexcaranha.jquerybyhumming.model.KeyValue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Alex Libório Caranha
 */
public final class XYGraphSignal extends JPanel implements Observer{

    private JFreeChart      chart;
    private XYPlot          plot;
    private ChartPanel      chartPanel;
    private String          alias;
    private boolean         visibleLegend;

    public XYGraphSignal(String title,
                         String titleX,
                         String titleY,
                         String alias,
                         boolean visibleLegend) {

        this(title, titleX, titleY, alias, new DefaultXYDataset(), visibleLegend);
    }
    
    public XYGraphSignal(String title,
                         String titleX,
                         String titleY,
                         String alias, 
                         DefaultXYDataset dataset,
                         boolean visibleLegend) {

        this.visibleLegend = visibleLegend;
        
        chart = createChart(title, titleX, titleY, dataset);
        chartPanel = new ChartPanel(chart);
        chartPanel.setMouseZoomable(true, false);

        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);

        this.setAlias(alias);
        this.setVisible(true);
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    private JFreeChart createChart(String title,
                                   String titleX,
                                   String titleY,
                                   DefaultXYDataset dataset) {

        JFreeChart c = ChartFactory.createXYLineChart(
            title, titleX, titleY,
            dataset,
            PlotOrientation.VERTICAL,
            this.visibleLegend,
            true,
            false
        );

        c.setBackgroundPaint(Color.white);

        plot = c.getXYPlot();
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setLegendLine(new Rectangle2D.Double(-4.0, -3.0, 10.0, 6.0));
        renderer.setSeriesPaint(0, Color.black);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));

        return c;
    }
    
    public void update(KeyValue[] keys) {
        for(KeyValue keyValue : keys) {
            String key = keyValue.getKey().toString();
            //System.out.println(String.format("key: %s", keyValue.getKey().toString()));
            if (key.equalsIgnoreCase("CLASS")) {
                Class classe = (Class) keyValue.getValue();
                if (classe != XYGraphSignal.class) break;
            }
            else if (key.equalsIgnoreCase("ALIAS")) {
                String aliasArgument = (String) keyValue.getValue();
                if (!aliasArgument.equalsIgnoreCase(this.alias)) break;
            }
            else if (key.equalsIgnoreCase("DATASET")) {
                KeyValue[] values = (KeyValue[]) keyValue.getValue();

                DefaultXYDataset dataSet = (DefaultXYDataset) values[0].getValue();
                String titleX = values[1].getValue().toString();
                String titleY = values[2].getValue().toString();

                chart = createChart(chart.getTitle().getText(), titleX, titleY, dataSet);
                chartPanel.setChart(chart);
            }
            else if (key.equalsIgnoreCase("SERIES-COLORS")) {
                Color[] colors = (Color[]) keyValue.getValue();
                XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();

                for (int i = 0; i < colors.length; i++) {
                    renderer.setSeriesPaint(i, colors[i]);
                }
            }
            else if (key.equalsIgnoreCase("LINE")) {
                KeyValue value = (KeyValue) keyValue.getValue();
                
                if (value.getKey().toString().equalsIgnoreCase("VALUE")) {
                    plot.setDomainCrosshairValue(Convert.toDouble(value.getValue()));
                }
                else if (value.getKey().toString().equalsIgnoreCase("VISIBLE")) {
                    plot.setDomainCrosshairVisible(Convert.toBoolean(value.getValue()));
                }
            }
            else if (key.equalsIgnoreCase("MARKERS")) {
                List<Marker> markers = (List<Marker>) keyValue.getValue();
                if (markers != null) {
                    for (Marker marker : markers) {
                        plot.addDomainMarker(marker);
                    }
                }
            }
            else if (key.equalsIgnoreCase("LIMIT-X")) {
                KeyValue<Double, Double> limit = (KeyValue<Double, Double>)keyValue.getValue();

                NumberAxis domain = (NumberAxis) plot.getDomainAxis();

                double start = limit.getKey() == null ? domain.getRange().getLowerBound() : limit.getKey().doubleValue();
                double end = limit.getValue() == null ? domain.getRange().getUpperBound(): limit.getValue().doubleValue();

                domain.setRange(start, end);
            }
            else if (key.equalsIgnoreCase("LIMIT-Y")) {
                KeyValue<Double, Double> limit = (KeyValue<Double, Double>)keyValue.getValue();
                NumberAxis range = (NumberAxis) plot.getRangeAxis();

                double start = limit.getKey() == null ? range.getRange().getLowerBound() : limit.getKey().doubleValue();
                double end = limit.getValue() == null ? range.getRange().getUpperBound(): limit.getValue().doubleValue();

                range.setRange(start, end);
            }
        }
    }

    @Override
    public void update(Observable subject, Object argument) {
        //----------------------------------------------------------------------
        if (subject == null) return;
        if (!(argument instanceof KeyValue[])) return;
        //----------------------------------------------------------------------
        update((KeyValue[]) argument);
        //----------------------------------------------------------------------
        this.repaint();
        //----------------------------------------------------------------------
    }
}