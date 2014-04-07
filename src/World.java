
public class World {
	public Cell[][] theMap;
	public World(int x, int y){
		theMap = new Cell[x][y];
	}
	public boolean isRock(int x, int y){
		return theMap[x][y].rock;
	}
	public String getHormone(int x, int y){
		return theMap[x][y].hormone;
	}
	public boolean isFood(int x, int y){
		//is there food in the cell
		if(theMap[x][y].food > 0){
			//YES: decrement food counter and return true
			theMap[x][y].pickFood();
			return true;
		} else {
			//NO: return false
			return false;
		}
	}
	public void addCell(Cell c, int x, int y){
		theMap[x][y] = c;
	}
}
