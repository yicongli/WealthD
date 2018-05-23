/**
 * The patch map of growing grain and contain persons
 *
 * @author Yicong   Li      ID:923764 2018-05-18
 * @author Savan    Kanabar ID:965371 2018-05-18
 * @author Emmanuel Mogbeyi ID:854106 2018-05-18
 */

import java.util.function.Consumer;

public class PatchMap {
    public static int grainGrowthInterval = 1;	// tick interval between growing grain on patch
    public static int numGrainGrown		  = 4;	// the number of grain grown in each tick
    public static int grainMax			  = 50;	// max grain number in each patch
    
    public static int personNum		   	  = 250;// person number in current patch map
    public static double percentBestLand  = 10; // the ratio of best land
    
	public int columnNum  	 = 500;			// column of the map
	public int rowNum	     = 500;			// row of the map
	public double diffusePercent = 25;		// percentage for diffusing grains
	
	public Person[]  allPersons = null;		// person array in current map
	public Patch[][] allPatchs  = null;		// patch array in map
	
	/*
	 * Initiate patch map
	 */
	public PatchMap() {
		initialPatchs();
		initialPersons();
	}
	
	/*
	 * Add persons into patch map
	 */
	public void initialPersons () {
		allPersons = new Person[personNum];
		for (int i = 0; i < allPersons.length; i++) {
			// put each person in a random place on map 
			int col = PersonSettings.randomValue(0, columnNum-1);
			int row = PersonSettings.randomValue(0, rowNum-1);
			LocationCoordinate loc = new LocationCoordinate(col, row);
			
			allPersons[i] = new Person(loc);
		}
	}
	
	/*
	 * Add patches into map and set up patches
	 * set up the initial amounts of grain each patch has
	 */
	public void initialPatchs () {
		allPatchs = new Patch[columnNum][rowNum];
		for (int i = 0 ; i < columnNum; i++) {
			for (int j = 0 ; j < rowNum; j++) {
				allPatchs[i][j] = new Patch(i,j);
			}
		}
		
		// give some patches the highest amount of grain possible --
		// these patches are the "best land"
		Consumer<Patch> setBestLand = (mPatch) -> {
			if (PersonSettings.randomValue(0,99) <= PatchMap.percentBestLand) {
				mPatch.maxGrainHere = PatchMap.grainMax;
				mPatch.grainHere = PatchMap.grainMax;
			}
		};
		ergodicPatches(setBestLand);
		
		// spread that grain around the window a little and put a little back
		// into the patches that are the "best land" found above
		Consumer<Patch> initialDeffuseGrain = (mPatch) -> {
			if (mPatch.maxGrainHere != 0) {
				mPatch.grainHere = mPatch.maxGrainHere;
				diffuseGrainToNeighbors(mPatch.location);
			}
		};
		for (int i = 0; i < 5; i++) {
			ergodicPatches(initialDeffuseGrain);
		}
		
		// spread the grain around some more
		Consumer<Patch> deffuseGrain = (mPatch) -> diffuseGrainToNeighbors(mPatch.location);
		for (int i = 0; i < 10; i++) {
			ergodicPatches(deffuseGrain);
		}
		
		// round grain levels to whole numbers
		// initial grain level is also maximum
		Consumer<Patch> roundGrainNum = (mPatch) -> {
			mPatch.maxGrainHere = (int)mPatch.maxGrainHere;
			mPatch.grainHere = (int)mPatch.grainHere;
		};
		ergodicPatches(roundGrainNum);
	}
	
	/*
	 * Do operation 'handleFunction' for each patch
	 */
	public void ergodicPatches (Consumer<Patch> handleFunction) {
		for (int i = 0 ; i < columnNum ; i++) {
			for (int j = 0 ; j < rowNum ; j++) {
				handleFunction.accept(allPatchs[i][j]);
			}
		}
	}
	
	/*
	 * diffuse the grain of current patch to the around patches
	 */
	public void diffuseGrainToNeighbors (LocationCoordinate centralLocation) {
		int col = centralLocation.colCoord;
		int row = centralLocation.rowCoord;
		
		// get the grain after diffusing
		double AllDiffusedGrain = allPatchs[col][row].grainHere * (diffusePercent / 100);
		allPatchs[col][row].grainHere -= AllDiffusedGrain;
		
		// diffuse grain equally to the around patches
		double diffusedGrain = AllDiffusedGrain / 8;
        int rightCol = LocationCoordinate.getNextCoord(col,1,columnNum);
        int leftCol  = LocationCoordinate.getNextCoord(col,-1,columnNum);
        int upRow	 = LocationCoordinate.getNextCoord(row, -1, rowNum);
        int downRow  = LocationCoordinate.getNextCoord(row, 1, rowNum);
        
        allPatchs[leftCol][upRow].grainHere    += diffusedGrain;
        allPatchs[col][upRow].grainHere		   += diffusedGrain;
        allPatchs[rightCol][upRow].grainHere   += diffusedGrain;
        allPatchs[leftCol][row].grainHere	   += diffusedGrain;
        allPatchs[rightCol][row].grainHere	   += diffusedGrain;
        allPatchs[leftCol][downRow].grainHere  += diffusedGrain;
        allPatchs[col][downRow].grainHere	   += diffusedGrain;
        allPatchs[rightCol][downRow].grainHere += diffusedGrain;
	}
	
	/*
	 * update the map state of each tick
	 */
	public wealthRatio updateMapState (int ticks) {
		// choose direction holding most grain within the turtle's vision
		for (Person person : allPersons) {
			person.updateLocation(this);
		}
		
		// each patch checks if need to allocate grain to occupied persons
		Consumer<Patch> harvest = (mPatch) -> mPatch.harvest();
		ergodicPatches(harvest);
		
		// update wealth state of each person
		for (Person person : allPersons) {
			person.updatePersonState();
		}
		
		// get current tick wealth data
		wealthRatio ratioData = PersonSettings.updatePersonType(allPersons);
		
		// Grow grain in each interval
		if (ticks % PatchMap.grainGrowthInterval == 0) {
			Consumer<Patch> growGrain = (mPatch) -> mPatch.growGrain();
			ergodicPatches(growGrain);
		}
		
		return ratioData;
	}
}
