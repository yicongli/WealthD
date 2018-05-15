/*
 * the map contain patches to grow grain 
 */

public class PatchMap {
    // patch global attributes
    
    public static int grainGrowthInterval = 1;
    public static int numGrainGrown		  = 4;
    public static int grainMax			  = 50;
    
    public static double percentBestLand  = 10;
	public static int columnNum  	 = 50;			// column of the map
	public static int rowNum	     = 50;			// row of the map
	public static double diffusePercent = 25;		// use to diffuse grains
	
	public static int personNum		   = 250;
	
	public static Person[]  allPersons = null;
	public static Patch[][] allPatchs  = null;
	
	public PatchMap() {
		initialPatchs();
		initialPersons();
	}
	
	public void initialPatchs () {
		allPatchs = new Patch[columnNum][rowNum];
		for (int i = 0 ; i < columnNum; i++) {
			for (int j = 0 ; j < rowNum; j++) {
				allPatchs[i][j] = new Patch();
			}
		}
		
		//TODO set grain
	}
	
	public void initialPersons () {
		allPersons = new Person[personNum];
		for (int i = 0; i < allPersons.length; i++) {
			int col = PersonSettings.randomValue(1, columnNum);
			int row = PersonSettings.randomValue(1, rowNum);
			PersonLocation loc = new PersonLocation(col, row);
			
			allPersons[i] = new Person(loc);
		}
	}
}
