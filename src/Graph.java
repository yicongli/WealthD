import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph {
	public static PiePlot plot;
	public static XYSeries seriesRich;
	public static XYSeries seriesMid;
	public static XYSeries seriesPoor;
	public static Timer timer;
	
	public static void generateCharts () {
		JFrame chartFrame = new JFrame("Wealth distribution"); 
		chartFrame.setLayout(new GridLayout(1,2,0,0));  
	    chartFrame.setBounds(0, 0, 1300, 500);  
		
		JFreeChart chart = ChartFactory.createPieChart("Distribution PieChart",createPieDataset(),true,true,false); 
        plot = (PiePlot) chart.getPlot();
        chartFrame.add(new ChartPanel (chart,true));
        
        chartFrame.add(createLinewGraph());
        chartFrame.setVisible(true);
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	static int ticks = 0;
	public static DefaultPieDataset createPieDataset () {
		DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
		wealthRatio data = MainClass.wealthRatioData.get(ticks);
        dpd.setValue("Rich", data.rich);
        dpd.setValue("Middle", data.middle);
        dpd.setValue("Poor", data.poor);
        
        return dpd;
	}

	public static JPanel createLinewGraph() {
        seriesRich = new XYSeries("Rich");
        seriesMid = new XYSeries("Middle");
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
                false
                );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        return (JPanel) new JPanel().add(chartPanel);
    }
	
	public static void refresh(){
        int delay = 100; // seconds
        ActionListener taskTimer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                plot.setDataset(createPieDataset());
                
                wealthRatio data = MainClass.wealthRatioData.get(ticks);
                seriesRich.add(ticks, data.rich);
                seriesMid.add(ticks, data.middle);
                seriesPoor.add(ticks, data.poor);
                
                ticks ++;
                if (ticks == MainClass.tickNum - 1) {
        			timer.stop();
        		}
            }
        };

        timer = new Timer(delay,taskTimer);
        timer.start();
    }
}
