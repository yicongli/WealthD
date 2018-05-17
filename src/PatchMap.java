import java.util.function.Consumer;

/*
 * the map contain patches to grow grain 
 */

public class PatchMap {
    // patch global attributes
    
    public static int grainGrowthInterval = 1;
    public static int numGrainGrown		  = 4;
    public static int grainMax			  = 50;
    
    public static int personNum		   = 250;
    
    public double percentBestLand  = 10;
	public int columnNum  	 = 50;			// column of the map
	public int rowNum	     = 50;			// row of the map
	public double diffusePercent = 25;		// use to diffuse grains
	
	public Person[]  allPersons = null;
	public Patch[][] allPatchs  = null;
	
	public PatchMap() {
		initialPatchs();
		initialPersons();
	}
	
	public void initialPersons () {
		allPersons = new Person[personNum];
		for (int i = 0; i < allPersons.length; i++) {
			int col = PersonSettings.randomValue(0, columnNum-1);
			int row = PersonSettings.randomValue(0, rowNum-1);
			LocationCoordinate loc = new LocationCoordinate(col, row);
			
			allPersons[i] = new Person(loc);
		}
	}
	
	public void initialPatchs () {
		allPatchs = new Patch[columnNum][rowNum];
		for (int i = 0 ; i < columnNum; i++) {
			for (int j = 0 ; j < rowNum; j++) {
				allPatchs[i][j] = new Patch(i,j);
			}
		}
		
		Consumer<Patch> setBestLand = (mPatch) -> {
			if (PersonSettings.randomValue(0,99) <= percentBestLand) {
				mPatch.maxGrainHere = PatchMap.grainMax;
				mPatch.grainHere = PatchMap.grainMax;
			}
		};
		ergodicPatches(setBestLand);
		
		Consumer<Patch> initialDeffuseGrain = (mPatch) -> {
			if (mPatch.maxGrainHere != 0) {
				mPatch.grainHere = mPatch.maxGrainHere;
				diffuseGrainToNeighbors(mPatch.location);
			}
		};
		for (int i = 0; i < 5; i++) {
			ergodicPatches(initialDeffuseGrain);
		}
		
		Consumer<Patch> deffuseGrain = (mPatch) -> diffuseGrainToNeighbors(mPatch.location);
		for (int i = 0; i < 10; i++) {
			ergodicPatches(deffuseGrain);
		}
		
		Consumer<Patch> roundGrainNum = (mPatch) -> {
			mPatch.maxGrainHere = (int)mPatch.maxGrainHere;
			mPatch.grainHere = (int)mPatch.grainHere;
		};
		ergodicPatches(roundGrainNum);
	}
	
	public void ergodicPatches (Consumer<Patch> handleFunction) {
		for (int i = 0 ; i < columnNum ; i++) {
			for (int j = 0 ; j < rowNum ; j++) {
				handleFunction.accept(allPatchs[i][j]);
			}
		}
	}
	
	public void diffuseGrainToNeighbors (LocationCoordinate centralLocation) {
		int col = centralLocation.colCoord;
		int row = centralLocation.rowCoord;
		
		double AllDiffusedGrain = allPatchs[col][row].grainHere * (diffusePercent / 100);
		allPatchs[col][row].grainHere -= AllDiffusedGrain;
		
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
	
	public wealthRatio updateMapState (int ticks) {
		for (Person person : allPersons) {
			person.updateLocation(this);
		}
		
		Consumer<Patch> harvest = (mPatch) -> mPatch.harvest();
		ergodicPatches(harvest);
		
		for (Person person : allPersons) {
			person.updatePersonState();
		}
		
		wealthRatio ratioData = PersonSettings.updatePersonType(allPersons);
		
		if (ticks % PatchMap.grainGrowthInterval == 0) {
			Consumer<Patch> growGrain = (mPatch) -> mPatch.growGrain();
			ergodicPatches(growGrain);
		}
		
		return ratioData;
	}
}
