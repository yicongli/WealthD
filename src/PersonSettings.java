/**
 * Global Parameters / functions for Person 
 *
 * @author Yicong Li Student ID:923764 2018-05-18
 */

public class PersonSettings {

    public static int lifeExpectancyMax = 100;    // Maximum of person expectancy
    public static int lifeExpectancyMin = 1;      // Minimum of person expectancy
    public static int metabolismMax 	= 10;     // Maximum of Metabolism
    public static int metabolismMin 	= 1;      // Minimum of Metabolism
    public static int visionMax		 	= 5;      // Max vision of person
    public static int visionMin		 	= 1;      // Min vision of person
    public static int wealthMax			= 50;	  // Maximum of person wealth
    
    // random value between minValue and maxValue
    public static int randomValue(int minValue, int maxValue) {
    	int range = maxValue - minValue;
        return (int)(Math.random() * (range + 1) + minValue);
    }
    
    // random metabolism between metabolismMin and metabolismMax
    public static int randomMetabolism() {
		return randomValue(metabolismMin, metabolismMax);
	}
    
    // random life expectancy between lifeExpectancyMin and lifeExpectancyMax
    public static int randomLifeExpectancy() {
		return randomValue(lifeExpectancyMin, lifeExpectancyMax);
	}
    
    // random vision between visionMin and visionMax
    public static int randomVision() {
		return randomValue(visionMin, visionMax);
	}
    
    // random wealth between metabolism and wealthMax
    public static int randomWealth(int metabolism) {
		return randomValue(metabolism, wealthMax);
	}
    
    // update each person's wealth type according to current wealth distribution
    public static wealthRatio updatePersonType(Person[] personArray) {
    	// get max wealth value
    	double maxWealth = 0;
    	for (Person person : personArray) {
    		if (person.wealth > maxWealth) {
				maxWealth = person.wealth;
			}
		}
    	
		int rich = 0;
		int poor = 0;
		int middle = 0;
    	for (Person person : personArray) {
    		// Poor: less than 1/3 max wealth
			if (person.wealth <= maxWealth / 3) {
				person.personType = PersonType.Poor;
				poor ++;
			// Middle: less than 2/3 max wealth and larger than 1/3 max wealth
			} else if (person.wealth <= maxWealth * 2 / 3) {
				person.personType = PersonType.Middle;
				middle ++;
			// Rich: larger than 2/3
			} else {
				person.personType = PersonType.Rich;
				rich ++;
			}
		}
    	
    	// return wealth ratio data of current tick
    	return new wealthRatio(rich, middle, poor, personArray.clone());
    }
}
