import java.util.Random;


public class World {
	public Cell[][] theMap;
	int xSize;
	int ySize;
	public World(int x, int y){
		theMap = new Cell[x][y];
		xSize = x;
		ySize = y;
	}
	public boolean isRock(int x, int y){
		return theMap[x][y].rock;
	}
//	public String getHormone(int x, int y){
//		return theMap[x][y].hormone;
//	}
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
	
	public boolean populateMap(){
		try{
		//xSize is the width of the map
		//ySize is the height of the map
		
		
		//rocks
		//	x = x coord
		//	y = y coord
		for(int x = 0; x < xSize; x++){
			//System.out.println("");
			for(int y = 0; y < ySize; y++){
				//if x,y coord represents edge of the map, add rock
				if((x == 0 || x == xSize-1)||(y == 0 || y == ySize-1)){
					addCell(new Cell(true, 0, false), x, y);
					//System.out.print("# ");
				} else {
					//consider populating with empty cells here?
					//System.out.print(". ");
				}
			}
		}
		//create two hills
		Random r = new Random();
		int hill = 0;
		int hillTotalSize = 0;
		int hillHeight = 0;
		int hillWidth = 0;
		int c = 1;
		int d = 0;
		int lineWidth = 0;
		boolean hillSizeFound = false;
		int randX = 0;
		int randY = 0;
		boolean fair = false;
		boolean validPoint = false;
		//while(!fair){
			while(hill < 2){
				validPoint = false;
				while(!validPoint){
					//first point is ok, unless there is a point thats not ok
					validPoint = true;
					//generate a random point for the first antHill
					if(hill == 0){
						randX = r.nextInt(xSize/2);
						randY = r.nextInt(ySize);
					} else {
						randX = r.nextInt(xSize - ((xSize/2)+1))+ (xSize/2); //max - min + 1) + min
						randY = r.nextInt(ySize);
					}
					System.out.println(randX + "," + randY);
					if(theMap[randX][randY] == null){
						//calculate cells that are near
						hillTotalSize = (xSize/2) + 3;
						hillSizeFound = false;
						while(!hillSizeFound){
							//calculate hillWitdh
							if((2*(c-4)+2*(c-2)+c)==hillTotalSize){
								hillSizeFound = true;
								hillWidth = c;
								//calculate hillHeight
								hillHeight = hillWidth - (c/2);
								if((hillHeight-1)%2>0){
									hillHeight = hillHeight -1;
								}
								//compensate for the display
								hillHeight = hillHeight +2;
								//System.out.println();
								System.out.println("height: "+hillHeight);
								System.out.println("width: "+ hillWidth);
								//check surroundings
								if(randX < 2 ||(randX + hillWidth) >= xSize-1|| randX-hillWidth <= 0 || randY+(hillHeight)+1 >= ySize-1 || randY-(hillHeight)+1 >= ySize-1){
									validPoint = false;
									break;
								}
								//d is the distance from middle line to edge, hill height is always odd, making it symmetrical vertically
								d = (hillHeight-1)/2;
								System.out.println("d: "+ d);
								//check points to make the hill
								//&& validPoint == false
								for(int i =1; i <= d ; i++){
									try{
									//if cell is null, or it isn't (a rock & ant hill & food)
									if(theMap[randX+i][randY-i] == null || (theMap[randX+i][randY-i].rock == false && theMap[randX+i][randY-i].antHill == false && theMap[randX+i][randY-i].food<1)){
										//check lines
										lineWidth = hillWidth-(i*2);
										System.out.println("line width:"+ lineWidth);
										for(int j =0; j<lineWidth; j++){
											//check lines above the chosen point
											if(theMap[randX+i+j][randY-i] == null || (theMap[randX+i+j][randY-i].rock == false && theMap[randX+i+j][randY-i].antHill == false && theMap[randX+i+j][randY-i].food<1)){
												//line is OK
												//System.out.println("above: "+ i + " " +(randX+i+j) + "," + (randY-i) + "is ok");
											} else {
												System.out.println("not valid point");
												validPoint = false;
												break;
											}
											//check lines below the chosen point
											if(theMap[randX+i+j][randY+i] == null || (theMap[randX+i+j][randY+i].rock == false && theMap[randX+i+j][randY+i].antHill == false && theMap[randX+i+j][randY+i].food<1)){
												//System.out.println("above: "+ i + " " +(randX+i+j) + "," + (randY+i) + "is ok");
											} else {
												System.out.println("not valid point");
												validPoint = false;
												break;
											}
											
										}
									} else {
										System.out.println("not valid point");
										validPoint = false;
										break;
									}
									}catch (Exception e){
										validPoint = false;
										//break;
									}
								}//check hill's other points end
								
								
							} else {
								//increment hillIndex
								c++;
							}//if()hillWidth found end
						}//hillSizeFound end
						//TESTING ESCAPES
						//hill = 3;
						//fair = true;
						//depends on size of the world
					}//random point OK
				}//validPoint found end
				//add hill
//				try{
				System.out.println("add hill" + hill);
				if(hill == 0){
					//add + hill
					//loop from 0 to d
					//loop from 0 to hillwidth + offset for each line
					for(int i = 0; i <= d; i++){
						lineWidth = hillWidth -(i*2);
						for(int j = 0; j < lineWidth; j++){
							theMap[randX+((hillWidth-lineWidth)/2)][randY+j+i] = new Cell(false, 0, true, "+");
							theMap[randX-((hillWidth-lineWidth)/2)][randY+j+i] = new Cell(false, 0, true, "+");
						}
					}
				} else {
					//add - hill
					//start at point
					//loop over middle lines length
					//loop over d
					for(int i = 0; i <= d; i++){
						lineWidth = hillWidth -(i*2);
						for(int j = 0; j < lineWidth; j++){
							theMap[randX+((hillWidth-lineWidth)/2)][randY+j+i] = new Cell(false, 0, true, "-");
							theMap[randX-((hillWidth-lineWidth)/2)][randY+j+i] = new Cell(false, 0, true, "-");
						}
					}
//					for(int i = 0; i<= d; i++){
//						for(int j = 0; j < hillWidth-(2*i); j++){
//							theMap[randX+j][randY+i] = new Cell(false, 0, true, "-");
//							theMap[randX+j][randY-i] = new Cell(false, 0, true, "-");
//						}
//					}
				}
				hill = hill + 1;
//				} catch(Exception e){
//					validPoint = false;
//				}
				
			}//hill count end
		//}//fair end
		//add random rocks
		int i = 0;
		while(i < 11){
			randX = r.nextInt(xSize);
			randY = r.nextInt(ySize);
			if(theMap[randX][randY] == null || (theMap[randX][randY].food < 1 && theMap[randX][randY].antHill == false)){
				theMap[randX][randY] = new Cell(true, 0, false);
				i++;
				//System.out.println(i);
				System.out.println("rock added at:"+ randX + "," + randY);
			}
		}
		//add random food
		int numberOfFoodLocations = 10;
		int totalFood = 100;
		int currentFoodTotal;
		currentFoodTotal = 0;
		int foodSize;
		int j = 0;
		while(j < numberOfFoodLocations){
			randX = r.nextInt(xSize);
			randY = r.nextInt(ySize);
			foodSize = r.nextInt(10);
			if(theMap[randX][randY] == null || (theMap[randX][randY].rock == false && theMap[randX][randY].antHill==false)){
				try{
					theMap[randX][randY] = new Cell(false, theMap[randX][randY].food + foodSize, false);
				} catch (Exception e){
					theMap[randX][randY] = new Cell(false, foodSize, false);
				}
				j++;
				currentFoodTotal = currentFoodTotal + foodSize;
			}
		}
		//fill in the rest of the space with empty
		for(int x = 0; x < xSize; x++){
			for(int y = 0; y < ySize; y++){
				if(theMap[x][y] == null){
					theMap[x][y] = new Cell(false, 0, false);
				}
			}
		}
		return true;
		} catch (Exception e){
			return false;
		}
	}
	public void printMap(){
		for(int x = 0; x < xSize; x++){
			if(x < 10){
				System.out.print("0" + x);
			} else {
				System.out.print(x);
			}
			for(int y = 0; y < ySize; y++){
				if(theMap[x][y].rock == true){
					System.out.print("# ");
				} else if(theMap[x][y].food > 0){
					System.out.print(theMap[x][y].food + " ");
				} else if(theMap[x][y].antHill == true){
					System.out.print(theMap[x][y].species+ " ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
	}

	public static void main(String args[]){
		World world = new World(50, 50);
		boolean passed = false;
		while(!passed){
			passed = world.populateMap();
		}
		world.populateMap();
		world.printMap();
		//int d = -2;
		//System.out.println(d);
	}
	
}



//public void populateMap() {
//// rocks
//for (int a = 0; a < x; a++) {
//  for (int b = 0; b < y; b++) {
//      plane[a][b] = new Cell(a, b);
//      if ((a == 0 || a == x - 1) || (b == 0 || b == y - 1)) {
//          plane[a][b].setRock();
//      }
//  }
//}
//Random r = new Random();
//int Hill = 0;
//while (Hill < 2){
//  //Add ant hills fairly apart TODO
//}
//
//
////food    
//int food = 0;
//while (food < 11) {
//  boolean freeArea = true;
//  int w = r.nextInt(x - 6) + 1;
//  int v = r.nextInt(y - 6) + 1;
//  for (int a = w - 1; a < w + 6; a++) {
//      for (int b = v - 1; b < v + 6; b++) {
//          if (plane[a][b].isAntHill() || plane[a][b].isRock()) {
//              freeArea = false;
//          }
//      }
//  }
//  if (freeArea) {
//      for (int a = w; a < w + 5; a++) {
//          for (int b = v; b < v + 5; b++) {
//              plane[a][b].food();
//          }
//      }
//      foods++;
//  }
//}
//
//// scattered rocks.
//for (int i = 0; i < 14; i++) {
//  int a = r.nextInt(x);
//  int b = r.nextInt(y);
//  if ( !plane[a][b].isAntHill() || !plane[a][b].hasFood() ||  !plane[a][b].isRock()) { 
//      plane[a][b].setRock();
//  } else {
//      i--;
//  }
//}
//}
