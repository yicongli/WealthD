
/*
 * location of person
 */
class LocationCoordinate {
	public int colCoord;
	public int rowCoord;
	
	public static int getNextCoord(int originalCoord, int step, int boundaryNum) {
		return ((originalCoord + step) + boundaryNum) % boundaryNum;
	}
	
	public LocationCoordinate(int x, int y) {
		colCoord = x;
		rowCoord = y;
	}
	
	public LocationCoordinate moveLeft(PatchMap pMap, int step) {
		int nextCol = LocationCoordinate.getNextCoord(colCoord, -step, pMap.columnNum);
		return new LocationCoordinate(nextCol, rowCoord); 
	}
	
	public LocationCoordinate moveRight(PatchMap pMap, int step) {
		int nextCol = LocationCoordinate.getNextCoord(colCoord, step, pMap.columnNum);
		return new LocationCoordinate(nextCol, rowCoord); 
	}
	
	public LocationCoordinate moveUp(PatchMap pMap, int step) {
		int nextRow = LocationCoordinate.getNextCoord(rowCoord, -step, pMap.rowNum);
		return new LocationCoordinate(colCoord, nextRow); 
	}
	
	public LocationCoordinate moveDown(PatchMap pMap, int step) {
		int nextRow = LocationCoordinate.getNextCoord(rowCoord, step, pMap.rowNum);
		return new LocationCoordinate(colCoord, nextRow); 
	}
}
