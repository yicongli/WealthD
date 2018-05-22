/**
 * The class for showing the wealth distribution graph
 *
 * It is responsible for:
 *  - Initiate graphs which is used to show the model trend
 *  - Update the graphs according to the cache data(wealthRatioData)
 *
 * @author Yicong   Li      ID:923764 2018-05-18
 * @author Savan    Kanabar ID:965371 2018-05-18
 * @author Emmanuel Mogbeyi ID:854106 2018-05-18
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph {
	public static PiePlot piePlot;			// Showing the pie chart of distribution ratio
	public static XYSeries seriesRich;		// Store the rich person number in each tick
	public static XYSeries seriesMid;		// Store the middle person number in each tick
	public static XYSeries seriesPoor;		// Store the poor person number in each tick
	public static Timer timer;				// timer for refresh all charts
	public static int ticks = 0;			// The tick for model
	
	/*
	 * Generate chartFrame and show the graph
	 */
	public static void generateCharts () {
		JFrame chartFrame = new JFrame("Wealth distribution"); 
		chartFrame.setLayout(new GridLayout(1,2,0,0));  
	    chartFrame.setBounds(0, 0, 1300, 500);  
	    
	    // add subGraph to the chart frame
        chartFrame.add(createPieGraph());
        chartFrame.add(createLineGraph());
        
        chartFrame.setVisible(true);
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*
	 * Create pie graph to show the ratio of wealth distribution
	 */
	public static JPanel createPieGraph() {
		JFreeChart chart = ChartFactory.createPieChart(
				"Distribution PieChart",
				createPieDataset(),
				true,
				true,
				false); 
		
        piePlot = (PiePlot) chart.getPlot();
        piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1} {2}"));
        return new ChartPanel (chart,true);
	}
	
	/*
	 * Create pie chart data in each tick
	 */
	public static DefaultPieDataset createPieDataset () {
		DefaultPieDataset dpd = new DefaultPieDataset();
		wealthRatio data = MainClass.wealthDistributionData.get(ticks);
        dpd.setValue("Rich:", data.rich);
        dpd.setValue("Middle:", data.middle);
        dpd.setValue("Poor:", data.poor);
        return dpd;
	}

	/*
	 * Create line graph to show the trend of wealth distribution
	 */
	public static JPanel createLineGraph() {
        seriesRich = new XYSeries("Rich");
        seriesMid  = new XYSeries("Middle");
        seriesPoor = new XYSeries("Poor");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesRich);
        dataset.addSeries(seriesMid);
        dataset.addSeries(seriesPoor);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Class Plot",
                "Time",
                "Turtles",
                dataset, 
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        return (JPanel) new JPanel().add(chartPanel);
    }
	
	/*
	 * Refresh all data of graphs in each tick
	 */
	public static void refresh(){
        int delay = 100; // seconds
        ActionListener taskTimer = new ActionListener() {
        	// updating method
            public void actionPerformed(ActionEvent evt) {
            	// update pie chart
                piePlot.setDataset(createPieDataset());
                
                // update class plot chart
                wealthRatio data = MainClass.wealthDistributionData.get(ticks);
                seriesRich.add(ticks, data.rich);
                seriesMid.add(ticks, data.middle);
                seriesPoor.add(ticks, data.poor);
                
                // if tick reach the end of whole simulate process
                ticks ++;
                if (ticks == MainClass.tickNum - 1) {
        			timer.stop();
        		}
            }
        };

        // set timer for updating graphs
        timer = new Timer(delay,taskTimer);
        timer.start();
    }
}
