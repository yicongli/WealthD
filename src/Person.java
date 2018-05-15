/*
 * Person Class
 */

/*
 * the wealth type of person
 */
enum PersonType {
    Poor, 
    Middle, 
    Rich 
}

/*
 * location of person
 */
class PersonLocation {
	public int xCoord;
	public int yCoord;
	
	public PersonLocation(int x, int y) {
		xCoord = x;
		yCoord = y;
	}
}

public class Person {
	public int age;					// how old a turtle is
	public double wealth;			// the amount of grain a turtle has
	public int lifeExpectancy;		// maximum age that a turtle can reach
	public int metabolism;			// how much grain a turtle eats each time
	public int vision;				// how many patches ahead a turtle can see
	public PersonType personType;	// poor, middle, rich
	public PersonLocation location;	// the location on patch
	
	public Person(PersonLocation loc) {
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
	public void updateLocation () {
		
	}
}

