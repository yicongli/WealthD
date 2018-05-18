/**
 * Each patch location in Patch Map:
 * 	- Grow grain
 * 	- Distribute grain to occupied persons
 *
 * @author Yicong Li Student ID:923764 2018-05-18
 */
import java.util.ArrayList;

public class Patch {
	public double grainHere;					// the current amount of grain on this patch
	public double maxGrainHere; 				// the maximum amount of grain this patch can hold
	public ArrayList<Person> occupiedPersons;	// the persons in current patch
	public LocationCoordinate location;			// patch location
	
	public Patch(int x, int y) {
		occupiedPersons = new ArrayList<Person>();
		location = new LocationCoordinate(x, y);
	}
	
	/*
	 * grow grain in current patch
	 */
	public void growGrain () {
		if (grainHere < maxGrainHere) {
			grainHere += PatchMap.numGrainGrown;
			// grain here could not be more than max
			if (grainHere > maxGrainHere) {
				grainHere = maxGrainHere;
			}
		}
	}
	
	/*
	 * if have person on current patch, then split the grain and 
	 * allocate it to each person 
	 */
	public void harvest () {
		if (!occupiedPersons.isEmpty()) {
			double grain = grainHere / occupiedPersons.size();
			for (Person person : occupiedPersons) {
				person.gainWealth(grain);
			}
			
			// remove all grain in patch and occupied person
			grainHere = 0;
			removeOccupiedPerson();
		}
	}
	
	/*
	 * add new occupied person
	 */
	public void AddPerson (Person newPerson) {
		occupiedPersons.add(newPerson);
	}
	
	/*
	 * remove all occupied person
	 */
	public void removeOccupiedPerson () {
		occupiedPersons.clear();
	}
}
