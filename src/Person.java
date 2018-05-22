/**
 * Persons on the Patch Map
 * 	- gain wealth and consume by metabolism
 * 	- move around on the map seeking more grain.
 *
 * @author Yicong   Li      ID:923764 2018-05-18
 * @author Savan    Kanabar ID:965371 2018-05-18
 * @author Emmanuel Mogbeyi ID:854106 2018-05-18
 */

/*
 * the wealth type of person
 */
enum PersonType {
    Poor, 
    Middle, 
    Rich 
}

public class Person {
	public int age;					// how old a turtle is
	public double wealth;			// the amount of grain a turtle has
	public int lifeExpectancy;		// maximum age that a turtle can reach
	public int metabolism;			// how much grain a turtle eats each time
	public int vision;				// how many patches ahead a turtle can see
	public PersonType personType;	// poor, middle, rich
	public LocationCoordinate location;	// the location on patch
	
	/*
	 * initiate person
	 */
	public Person(LocationCoordinate loc) {
		initialParams(); 
		age = PersonSettings.randomValue(0, lifeExpectancy); // modeling start with random age
		location = loc;
	}
	
	/*
	 * initial parameters of current person
	 */
	public void initialParams() {
		age 			= 0;
		metabolism		= PersonSettings.randomMetabolism(); 		// metabolism with random value
		lifeExpectancy	= PersonSettings.randomLifeExpectancy();	// life expectancy with random value
		vision			= PersonSettings.randomVision();			// vision with random value
		wealth			= PersonSettings.randomWealth(metabolism);  // wealth between max value and current metablosim
	}
	
	/*
	 * update state of current person
	 */
	public void updatePersonState() {
		wealth -= metabolism;
		age	   += 1;
		
		// if wealth less than 0 or age larger than lifeExpectancy
		// then treat current person as death and generate a offspring
		if (wealth < 0 || age >= lifeExpectancy) {
			initialParams();
		}
	}
	
	/*
	 * gain wealth from patch
	 */
	public void gainWealth(double grain) {
		wealth += grain;
	}
	
	/*
	 * update the location of current person
	 */
	public void updateLocation(PatchMap mPatchMap) {
        int grainInRightVision = 0;
        int grainInLeftVision = 0;
        int grainInUpVision = 0;
        int grainInDownVision = 0;
        
        int col = location.colCoord;
        int row = location.rowCoord;
        int colNum = mPatchMap.columnNum;
        int rowNum = mPatchMap.rowNum;

        // calculate sum grain in each vision
        for (int i = 1; i <= vision; i++) {
        	grainInRightVision += mPatchMap.allPatchs[LocationCoordinate.getNextCoord(col,i,colNum)][row].grainHere;
        	grainInLeftVision  += mPatchMap.allPatchs[LocationCoordinate.getNextCoord(col,-i,colNum)][row].grainHere;
        	grainInUpVision    += mPatchMap.allPatchs[col][LocationCoordinate.getNextCoord(row,-i,rowNum)].grainHere;
        	grainInDownVision  += mPatchMap.allPatchs[col][LocationCoordinate.getNextCoord(row,i,rowNum)].grainHere;
        }

        // get max grain among 4 directions
        int maxGrain = Math.max(grainInUpVision, 
        						Math.max(grainInDownVision, 
        								Math.max(grainInLeftVision, grainInRightVision)));
        
        // current person move to the direction with max grain and occupy the new patch
        if (maxGrain == grainInUpVision) {
        	location = location.moveUp(mPatchMap, 1);
        	mPatchMap.allPatchs[col][LocationCoordinate.getNextCoord(row,-1,rowNum)].AddPerson(this);
		} else if (maxGrain == grainInDownVision) {
			location = location.moveDown(mPatchMap, 1);
			mPatchMap.allPatchs[col][LocationCoordinate.getNextCoord(row,1,rowNum)].AddPerson(this);
		} else if (maxGrain == grainInLeftVision) {
			location = location.moveLeft(mPatchMap, 1);
			mPatchMap.allPatchs[LocationCoordinate.getNextCoord(col,-1,colNum)][row].AddPerson(this);
		} else {
			location = location.moveRight(mPatchMap, 1);
			mPatchMap.allPatchs[LocationCoordinate.getNextCoord(col,1,colNum)][row].AddPerson(this);
		}
    }
}

