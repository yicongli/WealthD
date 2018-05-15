/*
 * person settings
 */

public class PersonSettings {
	// person global attributes
	
    public static int lifeExpectancyMax = 100;    // Maximum of person expectancy
    public static int lifeExpectancyMin = 1;      // Minimum of person expectancy
    public static int MetabolismMax 	= 10;     // Maximum of Metabolism
    public static int MetabolismMin 	= 1;      // Minimum of Metabolism
    public static int visionMax		 	= 5;      // Max vision of person
    public static int visionMin		 	= 1;      // Min vision of person
    public static int wealthMax			= 50;	  // Maximum of person wealth
    
    // person global functions
    
    public static int randomValue(int minValue, int maxValue) {
    	int range = maxValue - minValue;
        return (int)(Math.random() * (range + 1) + minValue);
    }
    
    public static int randomMetabolism() {
		return randomValue(MetabolismMin, MetabolismMax);
	}
    
    public static int randomLifeExpectancy() {
		return randomValue(lifeExpectancyMin, lifeExpectancyMax);
	}
    
    public static int randomVision() {
		return randomValue(visionMin, visionMax);
	}
    
    public static int randomWealth(int metabolism) {
		return randomValue(metabolism, wealthMax);
	}
    
    public static void updatePersonType(Person[] personArray) {
    	// get max wealth value
    	double maxWealth = 0;
    	for (Person person : personArray) {
    		if (person.wealth > maxWealth) {
				maxWealth = person.wealth;
			}
		}
    	
    	for (Person person : personArray) {
    		// Poor: less than 1/3 max wealth
			if (person.wealth <= maxWealth / 3) {
				person.personType = PersonType.Poor;
			// Middle: less than 2/3 max wealth and larger than 1/3 max wealth
			} else if (person.wealth <= maxWealth * 2 / 3) {
				person.personType = PersonType.Middle;
			// Rich: larger than 2/3
			} else {
				person.personType = PersonType.Rich;
			}
		}
    }
}
