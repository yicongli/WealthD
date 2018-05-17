import java.util.ArrayList;

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

public class wealthDistribution {
	public static int tickNum = 0;
	public static ArrayList<wealthRatio> wealthRatioData;
	
	public static void main(String[] args) {
		PatchMap pMap = new PatchMap();
		wealthRatioData = new ArrayList<wealthRatio>();
		
		for (int i = 0; i < tickNum; i++) {
			wealthRatio data = pMap.updateMapState(i);
			wealthRatioData.add(data);
		}
	}
}
