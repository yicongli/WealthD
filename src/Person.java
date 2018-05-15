/*
 * 
 */

enum PersonType {
    Poor, 
    Middle, 
    Rich 
}

class PersonLocation {
	public int xCoord;
	public int yCoord;
	
	public PersonLocation(int x, int y) {
		xCoord = x;
		yCoord = y;
	}
}

public class Person {
	
	// person attributes 
	
	public int age;					// how old a turtle is
	public int wealth;				// the amount of grain a turtle has
	public int lifeExpectancy;		// maximum age that a turtle can reach
	public int metabolism;			// how much grain a turtle eats each time
	public int vision;				// how many patches ahead a turtle can see
	public PersonType personType;		// poor, middle, rich
	public PersonLocation location;	// the location on patch
	
	public Person() {
		initialParams(); 
		age = GlobalSettings.randomValue(0, lifeExpectancy); // model start with random age
	}
	
	/*
	 * initial parameters of current person
	 */
	public void initialParams() {
		age 			= 0;
		metabolism		= GlobalSettings.randomMetabolism(); 		// metabolism with random value
		lifeExpectancy	= GlobalSettings.randomLifeExpectancy();	// life expectancy with random value
		vision			= GlobalSettings.randomVision();			// vision with random value
		wealth			= GlobalSettings.randomWealth(metabolism);  // wealth between max value and current metablosim
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
	
}

