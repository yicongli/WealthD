/**
 * The class for storing person/patch location
 *
 * @author Yicong Li Student ID:923764 2018-05-18
 */

class LocationCoordinate {
	public int colCoord;	// column coordinate
	public int rowCoord;	// row coordinate
	
	/*
	 * Because there is no limitation in Patch map, when the next coordinate out of the boundary,
	 * It should be converted to the other side of Patch map.
	 */
	public static int getNextCoord(int originalCoord, int step, int boundaryNum) {
		return ((originalCoord + step) + boundaryNum) % boundaryNum;
	}
	
	/*
	 * Initiate LocationCoordinate
	 */
	public LocationCoordinate(int x, int y) {
		colCoord = x;
		rowCoord = y;
	}
	
	/*
	 * Return the location on the left
	 */
	public LocationCoordinate moveLeft(PatchMap pMap, int step) {
		int nextCol = LocationCoordinate.getNextCoord(colCoord, -step, pMap.columnNum);
		return new LocationCoordinate(nextCol, rowCoord); 
	}
	
	/*
	 * Return the location on the right
	 */
	public LocationCoordinate moveRight(PatchMap pMap, int step) {
		int nextCol = LocationCoordinate.getNextCoord(colCoord, step, pMap.columnNum);
		return new LocationCoordinate(nextCol, rowCoord); 
	}
	
	/*
	 * Return the location on the top
	 */
	public LocationCoordinate moveUp(PatchMap pMap, int step) {
		int nextRow = LocationCoordinate.getNextCoord(rowCoord, -step, pMap.rowNum);
		return new LocationCoordinate(colCoord, nextRow); 
	}
	
	/*
	 * Return the location on the bottom
	 */
	public LocationCoordinate moveDown(PatchMap pMap, int step) {
		int nextRow = LocationCoordinate.getNextCoord(rowCoord, step, pMap.rowNum);
		return new LocationCoordinate(colCoord, nextRow); 
	}
}
