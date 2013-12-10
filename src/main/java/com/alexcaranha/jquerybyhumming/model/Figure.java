package com.alexcaranha.jquerybyhumming.model;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author alexcaranha
 */
public class Figure {

    int _width, _height;

    public Figure(int width, int height) {
        this._width = width;
        this._height = height;
    }

    /**
    * Saves a chart to a PDF file.
    *
    * @param file the file.
    * @param chart the chart.
    * @param width the chart width.
    * @param height the chart height.
    */
    public void saveChartAsPDF(File file,
                               JFreeChart chart,
                               FontMapper mapper) throws IOException {

        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        writeChartAsPDF(out, chart, mapper);
        out.close();
    }

    /**
     * Writes a chart to an output stream in PDF format.
     *
     * @param out the output stream.
     * @param chart the chart.
     * @param width the chart width.
     * @param height the chart height.
     *
    */
    public void writeChartAsPDF(OutputStream out,
                                JFreeChart chart,
                                FontMapper mapper) throws IOException{

        Rectangle pagesize = new Rectangle(_width, _height);
        Document document = new Document(pagesize, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.addAuthor("JFreeChart");
            document.addSubject("Demonstration");
            document.open();

            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate tp = cb.createTemplate(_width, _height);
            Graphics2D g2 = tp.createGraphics(_width, _height, mapper);
            Rectangle2D r2D = new Rectangle2D.Double(0, 0, _width, _height);

            chart.draw(g2, r2D);

            g2.dispose();
            cb.addTemplate(tp, 0, 0);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }

    public boolean createFigure(JFreeChart graph, String fullNamePNG, String fullNamePDF) {
        boolean result = true;

        try {
            if (fullNamePDF != null && !fullNamePDF.isEmpty()) {
                saveChartAsPDF(new File(fullNamePDF), graph, new DefaultFontMapper());
            }

            if (fullNamePNG != null && !fullNamePNG.isEmpty()) {
                ChartUtilities.saveChartAsPNG(new File(fullNamePNG), graph, this._width, this._height);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            result = false;
        }

        return result;
    }

    private static JFreeChart createChartXY(String title,
                                            String titleX,
                                            String titleY,
                                            boolean plotLegend,
                                            XYDataset dataset) {

        JFreeChart c = ChartFactory.createXYLineChart(
                            title, titleX, titleY,
                            dataset,
                            PlotOrientation.VERTICAL,
                            plotLegend,
                            true,
                            false
                        );

        c.setBackgroundPaint(Color.white);

        XYPlot plot = c.getXYPlot();
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setLegendLine(new Rectangle2D.Double(-4.0, -3.0, 10.0, 6.0));
        renderer.setSeriesPaint(0, Color.black);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);

        return c;
    }

    public static void save(String title, String titleX, String titleY,
                            Boolean plotLegend,
                            XYSeries[] series,
                            Color[] colors,
                            List<Marker> markers,
                            KeyValue<Double, Double> limitX, KeyValue<Double, Double> limitY,
                            Figure figure,
                            String fullNamePDF, String fullNamePNG) {
        //----------------------------------------------------------------------
        DefaultXYDataset dataset = Util.convertToXYDataSet(series);
        JFreeChart graph = createChartXY(title, titleX, titleY, plotLegend, dataset);
        //----------------------------------------------------------------------
        XYPlot xyPlot = (XYPlot) graph.getPlot();

        if (limitX != null || limitY != null) {
            if (limitX != null) {
                NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
                domain.setRange((double) limitX.getKey(), (double) limitX.getValue());
            }

            if (limitY != null) {
                NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
                range.setRange((double) limitY.getKey(), (double) limitY.getValue());
            }
        }

        if (markers != null) {
            for (Marker marker : markers) {
                xyPlot.addDomainMarker(marker);
            }
        }
        //----------------------------------------------------------------------
        if (colors == null){
            configureColorsRenderer((XYLineAndShapeRenderer) graph.getXYPlot().getRenderer(), dataset.getSeriesCount());
        } else {
            configureColorsRenderer((XYLineAndShapeRenderer) graph.getXYPlot().getRenderer(), colors);
        }
        //----------------------------------------------------------------------
        figure.createFigure(graph, fullNamePNG, fullNamePDF);
        //----------------------------------------------------------------------
    }
    
    private static JFreeChart createBarChart(String title,
                                             String titleX,
                                             String titleY,
                                             boolean plotLegend,
                                             DefaultCategoryDataset dataset) {

        JFreeChart c = ChartFactory.createBarChart(
                            title, titleX, titleY,
                            dataset,
                            PlotOrientation.VERTICAL,
                            plotLegend,
                            true,
                            false
                        );

        c.setBackgroundPaint(Color.white);

        CategoryPlot plot = c.getCategoryPlot();
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setUpperMargin(0.0);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        //renderer.setLegendLine(new Rectangle2D.Double(-4.0, -3.0, 10.0, 6.0));
        renderer.setSeriesPaint(0, Color.black);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);

        return c;
    }
    
    public static void saveBarChart(String title, String titleX, String titleY,
                                    Boolean plotLegend,
                                    DefaultCategoryDataset dataset,
                                    Color[] colors,
                                    List<Marker> markers,
                                    KeyValue<Double, Double> limitX, KeyValue<Double, Double> limitY,
                                    Figure figure,
                                    String fullNamePDF, String fullNamePNG) {
        //----------------------------------------------------------------------
        JFreeChart graph = createBarChart(title, titleX, titleY, plotLegend, dataset);
        //----------------------------------------------------------------------
        CategoryPlot plot = (CategoryPlot) graph.getPlot();
        /*
        if (limitX != null || limitY != null) {
            if (limitX != null) {
                CategoryAxis domain = (CategoryAxis) plot.getDomainAxis();
                domain.set
                domain.setRange((double) limitX.getKey(), (double) limitX.getValue());
            }

            if (limitY != null) {
                NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
                range.setRange((double) limitY.getKey(), (double) limitY.getValue());
            }
        }
        */
        /*
        if (markers != null) {
            for (Marker marker : markers) {
                xyPlot.addDomainMarker(marker);
            }
        }
        */
        //----------------------------------------------------------------------
        if (colors == null){
            configureColorsRenderer((BarRenderer) graph.getCategoryPlot().getRenderer(), dataset.getRowCount());
        } else {
            configureColorsRenderer((BarRenderer) graph.getCategoryPlot().getRenderer(), colors);
        }
        //----------------------------------------------------------------------
        figure.createFigure(graph, fullNamePNG, fullNamePDF);
        //----------------------------------------------------------------------
    }

    public static void configureColorsRenderer(BarRenderer renderer, int qtd) {
        for (int i = 0; i < qtd; i++) {
            renderer.setSeriesPaint(i, Color.BLACK);
        }
    }
    
    public static void configureColorsRenderer(BarRenderer renderer, Color[] colors) {
        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, colors[i]);
        }
    }
    
    public static void configureColorsRenderer(XYLineAndShapeRenderer renderer, int qtd) {
        for (int i = 0; i < qtd; i++) {
            renderer.setSeriesPaint(i, Color.BLACK);
        }
    }

    public static void configureColorsRenderer(XYLineAndShapeRenderer renderer, Color[] colors) {
        for (int i = 0; i < colors.length; i++) {
            renderer.setSeriesPaint(i, colors[i]);
        }
    }
}
