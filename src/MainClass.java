
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

class wealthRatio {
	int rich;
	int poor;
	int middle;
	public wealthRatio(int r, int m, int p) {
		rich = r;
		middle = m;
		poor = p;
	}
}

public class MainClass {
	public static int tickNum = 1000;
	public static ArrayList<wealthRatio> wealthRatioData;
	public static PiePlot plot;
	public static Timer timer;
	
	public static void main(String[] args) {
		setParameter(args);
		initialData();
		generateCharts();
        refresh();
	}
	
	public static void setParameter (String[] args) {
		String paramType = null;
		if (args.length == 8) {
			PatchMap.personNum 		 	 = Integer.parseInt(args[0]);
			PersonSettings.visionMax 	 = Integer.parseInt(args[1]);
			PersonSettings.metabolismMax = Integer.parseInt(args[2]);
			PersonSettings.lifeExpectancyMin = Integer.parseInt(args[3]);
			PersonSettings.lifeExpectancyMax = Integer.parseInt(args[4]);
			PatchMap.percentBestLand	 = Integer.parseInt(args[5]);
			PatchMap.grainGrowthInterval = Integer.parseInt(args[6]);
			PatchMap.numGrainGrown		 = Integer.parseInt(args[7]);
			
			paramType = "New Parameter";
		} else {
			paramType = "Default Parameter";
		}

		System.out.println(paramType + "\nPersonNum: " + PatchMap.personNum
				+ "\nVisionMax: " + PersonSettings.visionMax
				+ "\nMetabolismMax: " + PersonSettings.metabolismMax
				+ "\nLifeExpectancyMin: " + PersonSettings.lifeExpectancyMin
				+ "\nLifeExpectancyMax: " + PersonSettings.lifeExpectancyMax
				+ "\nPercentBestLand: " + PatchMap.percentBestLand
				+ "\nGrainGrowthInterval: " + PatchMap.grainGrowthInterval
				+ "\nNumGrainGrown: " + PatchMap.numGrainGrown);
	}
	
	public static void initialData () {
		PatchMap pMap = new PatchMap();
		wealthRatioData = new ArrayList<wealthRatio>();
		
		for (int i = 0; i < tickNum; i++) {
			wealthRatio data = pMap.updateMapState(i);
			wealthRatioData.add(data);
		}
	}
	
	public static void generateCharts () {
		JFrame chartFrame = new JFrame("Wealth distribution"); 
		chartFrame.setLayout(new GridLayout(1,2,10,10));  
	    chartFrame.setBounds(50, 50, 1200, 500);  
		
		JFreeChart chart = ChartFactory.createPieChart("Distribution PieChart",createDataset(),true,true,false); 
        plot = (PiePlot) chart.getPlot();
        ChartPanel frame1=new ChartPanel (chart,true); 
        chartFrame.add(frame1);
        
        JPanel panel = createGraph();
        chartFrame.add(panel);
        //chartFrame.pack(); 
        chartFrame.setVisible(true);
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void refresh(){
        int delay = 100; // seconds
        ActionListener taskTimer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(plot!=null){
                	plot.setDataset(createDataset());
                }
            }
        };

        timer = new Timer(delay,taskTimer);
        timer.start();
    }
	
	static int ticks = 0;
	public static DefaultPieDataset createDataset (){
		DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
		wealthRatio data = wealthRatioData.get(ticks);
        dpd.setValue("Rich", data.rich);
        dpd.setValue("Middle", data.middle);
        dpd.setValue("Poor", data.poor);
        
        ticks ++;
        if (ticks == tickNum - 1) {
			timer.stop();
		}
        
        return dpd;
	}
	
	public static JPanel createGraph() {

        JPanel panel = new JPanel();
        XYSeries series = new XYSeries("MyGraph");
        series.add(0, 1);
        series.add(1, 2);
        series.add(2, 5);
        series.add(7, 8);
        series.add(9, 10);


        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Chart",
                "x-axis",
                "y-axis",
                dataset, 
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );
        ChartPanel chartPanel = new ChartPanel(chart);


        panel.add(chartPanel);

        return panel;
    }
}
