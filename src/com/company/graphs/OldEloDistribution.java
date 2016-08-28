package com.company.graphs;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class OldEloDistribution extends ApplicationFrame {
	private OldEloDistribution() {
		super("Rankings Data");
		JFreeChart barChart = ChartFactory.createBarChart(
				"League 2015 Elo Distribution",
				"Elo",
				"Number of Players",
				createDataset(),
				PlotOrientation.VERTICAL,
				true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 500));
		final CategoryPlot plot = barChart.getCategoryPlot();
		setContentPane(chartPanel);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setSeriesPaint(0, Color.RED);


		plot.setBackgroundPaint(Color.DARK_GRAY);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
	}

	private CategoryDataset createDataset() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		List<Double> eloList = Arrays.asList(1301.0, 1319.5, 1326.4, 1339.9, 1345.3, 1371.7, 1381.7,
				1383.7, 1385.9, 1394.0, 1394.5, 1400.6, 1404.3, 1409.0, 1416.5, 1417.0, 1418.6, 1420.9,
				1422.4, 1427.4, 1438.9, 1439.8, 1444.7, 1447.4, 1450.6, 1452.4, 1455.3, 1460.9, 1462.0,
				1465.0, 1471.1, 1474.6, 1476.7, 1480.2, 1486.4, 1496.3, 1507.1, 1514.4, 1514.6, 1516.1,
				1523.2, 1523.9, 1525.5, 1530.7, 1533.0, 1543.7, 1549.0, 1563.1, 1572.9, 1580.1, 1595.4,
				1596.5, 1597.9, 1604.1, 1616.6, 1636.5, 1663.1, 1668.2, 1684.2, 1690.9, 1692.9, 1708.1, 1800.0);

		int lowestElo = (int) (eloList.get(0) - (eloList.get(0) % 100));
		int highestElo = (int) (eloList.get(eloList.size() - 1) + 100 - (eloList.get(eloList.size() - 1) % 100));

		for(int i = lowestElo; i < highestElo; i += 100) {
			int binSize = 0;
			for(int j = 0; j < eloList.size(); j++) {
				if(eloList.get(j) >= i) {
					if(eloList.get(j) < i + 100) {
						binSize++;
					} else {
						j = eloList.size();
					}
				}
			}
			dataset.addValue(binSize, "Old System", i + "'s");
		}

		return dataset;
	}

	public static void graph() {
		OldEloDistribution graph = new OldEloDistribution();
		graph.pack();
		RefineryUtilities.positionFrameOnScreen(graph, 0.0, 0.0);
		graph.setVisible(true);
	}
}
