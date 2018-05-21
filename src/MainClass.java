/**
 * @author Yicong Li Student ID:923764 2018-05-18
 */
import java.util.ArrayList;

/**
 * Data item of each tick
 */
class wealthRatio {
	int rich;	// Rich person number in current tick
	int poor;	// Poor person number in current tick
	int middle; // Middle person number in current tick
	Person [] curPersonData;	// All Person data in current tick
	
	/*
	 * initiate wealthRatio
	 */
	public wealthRatio(int r, int m, int p, Person[] data) {
		rich = r;
		middle = m;
		poor = p;
		curPersonData = data;
	}
}

/*
 * Main class for running the model simulation
 */
public class MainClass {
	public static int tickNum = 1000;						// the total tick number
	public static ArrayList<wealthRatio> wealthDistributionData;	// Data Array for saving tick data
	
	/*
	 * Run model simulation
	 */
	public static void main(String[] args) {
		setParameter(args);					// set the parameters of the model
		initialData();						// generate the model data
		Graph.generateCharts();				// create charts 
		Graph.refresh();					// refresh data in each tick
		FileOperator.extractDataToFile();	// extract data to the file
	}
	
	/*
	 * Set model parameter
	 */
	public static void setParameter (String[] args) {
		String paramType = null;
		// if receive 8 parameters, then set the params, 
		// otherwise use default values.
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
	
	/*
	 * initial Patch Map and run model simulation
	 */
	public static void initialData () {
		// initial patch map
		PatchMap pMap = new PatchMap();
		wealthDistributionData = new ArrayList<wealthRatio>();
		
		// update map state and save tick data to DataArray
		for (int i = 0; i < tickNum; i++) {
			wealthRatio data = pMap.updateMapState(i);
			wealthDistributionData.add(data);
		}
	}
}
