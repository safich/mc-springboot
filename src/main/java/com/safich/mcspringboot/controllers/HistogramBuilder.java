package com.safich.mcspringboot.controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.io.File;
import java.io.IOException;

public class HistogramBuilder {
    public void build(double[] array) throws IOException {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Values", array, 45);
        String plotTitle = "Distribution";
        String xAxis = "Value";
        String yAxis = "Frequency";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        int width = 1300;
        int height = 900;
        JFreeChart chart = ChartFactory.createHistogram(plotTitle, xAxis, yAxis, dataset, orientation, false, false, false);
        String path = new File("src/main/resources/static/").getAbsolutePath();
        File file = new File(path, "histogram.png");
        ChartUtils.saveChartAsPNG(file, chart, width, height);
    }
}
