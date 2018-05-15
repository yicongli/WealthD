/*
 * the map contain patches to grow grain 
 */

public class PatchMap {
    // patch global attributes
    
    public static int grainGrowthInterval = 1;
    public static int numGrainGrown		  = 4;
    public static int grainMax			  = 50;
    
	public static Patch[][] allPatch = null;
	public static int columnNum  	 = 50;			// column of the map
	public static int rowNum	     = 50;			// row of the map
	public static double diffusePercent = 25;	// use to diffuse grains
}
