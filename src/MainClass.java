
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

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
		PatchMap pMap = new PatchMap();
		wealthRatioData = new ArrayList<wealthRatio>();
		
		for (int i = 0; i < tickNum; i++) {
			wealthRatio data = pMap.updateMapState(i);
			wealthRatioData.add(data);
		}

		JFreeChart chart = ChartFactory.createPieChart("Distribution Ratio",createDataset(),true,true,false); 
        plot = (PiePlot) chart.getPlot();
        ChartFrame chartFrame=new ChartFrame("Wealth distribution",chart); 
        chartFrame.pack(); 
        chartFrame.setVisible(true);
        refresh();
	}
	
	static int ticks = 0;
	public static DefaultPieDataset createDataset (){
		DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
		wealthRatio data = wealthRatioData.get(ticks);
        dpd.setValue("Rich", data.rich);
        dpd.setValue("Middle", data.middle);
        dpd.setValue("Poor", data.poor);
        
        ticks ++;
        if (ticks == tickNum) {
			timer.stop();
		}
        return dpd;
	}
	
	public static void refresh(){
        // 获取刷新间隔时间
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
}
