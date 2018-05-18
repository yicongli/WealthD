
import java.util.ArrayList;

class wealthRatio {
	int rich;
	int poor;
	int middle;
	Person [] curPersonData;
	public wealthRatio(int r, int m, int p, Person[] data) {
		rich = r;
		middle = m;
		poor = p;
		curPersonData = data;
	}
}

public class MainClass {
	public static int tickNum = 1000;
	public static ArrayList<wealthRatio> wealthRatioData;
	
	public static void main(String[] args) {
		setParameter(args);
		initialData();
		Graph.generateCharts();
		Graph.refresh();
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
			pMap.allPersons.clone();
			wealthRatioData.add(data);
		}
	}
}
