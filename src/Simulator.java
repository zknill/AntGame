
/**
 * @author zk44
 *	This class runs the backend of the game, it is where the ants and brains will be process
 *	This class should run on its own thread and therefore need to extend thread or implement runnable. 
 */
//TODO run() method
public class Simulator extends Thread{
	
	public enum colour{
		red, black
	}
	public enum left_right{
		left, right
	}
	public enum senseDir{
		here, ahead, leftahead, rightahead
	}
	public enum condition{
		friend, foe, friendwithfood, foewithfood, food, rock, marker, foemarker, home,foehome 
	}
	public enum command{
		sense, mark, unmark, pickup, drop, turn, move, flip
	}
	
	public Game game;
	public Monitor monitor;
	public Simulator(Game current, Monitor monitor){
		game = current;
		this.monitor = monitor;
	}

	public void run(){
		
//		synchronized(monitor){
//			for(int i = 0; i < 20; i++){
//				monitor.notify();
//				System.out.println("sim"+i);
//				//run1 round.
//				try {
//					monitor.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		
			int antState = 0;
			String command = "";
			//for each round
			for(int i = 0; i < 30; i++){
				//for each antID step();
				System.out.println("round: "+ i);
				for(int j = 0; j <= game.maxAntID; j++){
					step(j);
				}
			}
	}
	
	public synchronized void repaintGUI(){
		int i = 0;
		System.out.println("repaint"+ i);
		i++;
	}
	
	public synchronized void simul(){
		int i = 0;
		System.out.println("sumilate"+i);
		i++;
	}
/**
 * Returns the position of the adjacent cell in a given direction
 * @param x	xposition of the cell
 * @param y yposition of the cell
 * @param d direction of facing
 * @return int[2] array of x and y coord of the adjacent cell
 */
	public int[] adjacentCell(int x, int y, int d){
		int[] returnPos = new int[2];
		switch(d){
		case 0: 
			returnPos[0] = x+1;
			returnPos[1] = y;
		case 1:
			if(y%2 == 0){
				returnPos[0] = x;
				returnPos[1] = y+1;
			} else {
				returnPos[0] = x+1;
				returnPos[1] = y+1;
			}
		case 2:
			if(y%2==0){
				returnPos[0] = x-1;
				returnPos[1] = y+1;
			} else {
				returnPos[0] = x;
				returnPos[1] = y+1;
			}
		case 3:
			returnPos[0] = x-1;
			returnPos[1] = y;
		case 4:
			if(y%2 == 0){
				returnPos[0] = x-1;
				returnPos[1] = y-1;
			} else {
				returnPos[0] = x;
				returnPos[1] = y-1;
			}
		case 5:
			if(y%2== 0){
				returnPos[0] = x;
				returnPos[1] = y-1;
			} else {
				returnPos[0] = x+1;
				returnPos[1] = y-1;
			}
		}
		return returnPos;
	}
	
/**
 * calculates the new direction given a direction to turn and a direction of facing
 * @param lr left or right, direction to turn
 * @param dir direction of facing
 * @return new direction calculated
 */
	public int turn(left_right lr, int dir){
		int newDirection = 0;
		switch(lr){
			case left: newDirection = (dir+5) % 6;
			case right: newDirection = (dir+1) % 6;
		}
		return newDirection;
	}
	
	
/**
 * calculates the cell to be sensed according to the direction and sense command
 * @param x xcoord of current cell
 * @param y ycoord of current cell
 * @param d current direction of facing
 * @param senseDir the sense command, here, ahead, rightahead or leftahead
 * @return the x and y coords of the calculated position in int[]
 */
	public int[] sensedCell(int x, int y, int d, senseDir senseDirection){
		int[] newPosition = new int[2];
		switch(senseDirection){
		case here:
			newPosition[0] = x;
			newPosition[1] = y;
		case ahead:
			newPosition = adjacentCell(x, y, d);
		case leftahead:
			newPosition = adjacentCell(x, y, turn(left_right.left, d));
		case rightahead:
			newPosition = adjacentCell(x, y, turn(left_right.right, d));
		}		
		return newPosition;
	}
	
	
	/**
	 * returns the opposite species colour
	 * @param c
	 * @return opposite colour
	 */
	public colour otherColour(colour c){
		switch(c){
			case red: return colour.red;
			case black: return colour.black;
		}
		return null;
	}
	
	
	/**
	 * true if there is an ant in the cell at position p
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean someAntIsAt(int x, int y){
		for(Ant a:game.antList1){
			if(a.position[0] == x && a.position[1] == y){
				return true;
			}
		}
		for(Ant a:game.antList2){
			if(a.position[0] == x && a.position[1] == y){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * return the ant in the cell at position x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public Ant antAt(int x, int y){
		for(Ant a:game.antList1){
			if(a.position[0] == x && a.position[1] == y){
				return a;
			}
		}
		for(Ant a:game.antList2){
			if(a.position[0] == x && a.position[1] == y){
				return a;
			}
		}
		return null;
	}
	
	
	
	/**
	 * record the fact that the given ant is at position p
	 * @param x
	 * @param y
	 * @param a
	 */
	public void setAntAt(int x, int y, Ant a){
		a.setPosition(x, y);
	}
	
	//clear ant at position p????
//	function clear_ant_at(p:pos) =
//		  <record the fact that no ant is at position p>

	
	/**
	 * true if an ant with the given id exists somewhere in the world
	 */
	public boolean antIsAlive(int id){
		for(Ant a:game.antList1){
			if(a.antID == id){
				return !(a.isDead);
			}
		}
		for(Ant a:game.antList2){
			if(a.antID == id){
				return !(a.isDead);
			}
		}
		return false;
	}
	
	/**
	 * return current position of the ant with the given id
	 * @param id
	 * @return
	 */
	public int[] findAnt(int id){
		for(Ant a:game.antList1){
			if(a.antID == id){
				return a.position;
			}
		}
		for(Ant a:game.antList2){
			if(a.antID == id){
				return a.position;
			}
		}
		return null;
	}
	/**
	 * set the ant at given position to dead
	 * @param x
	 * @param y
	 */
	public void killAntAt(int x, int y){
		for(Ant a:game.antList1){
			if(a.position[0] == x && a.position[1] == y){
				a.isDead = true;
			}
		}
		for(Ant a:game.antList2){
			if(a.position[0] == x && a.position[1] == y){
				a.isDead = true;
			}
		}
	}
	
	/**
	 * return the amount of food in the cell at position x,y
	 * @param x
	 * @param y
	 * @return
	 */
	public int foodAt(int x, int y){
		return game.theWorld.theMap[x][y].food;
	}
	/**
	 * record the fact that a given amount of food, f, is at position x, y
	 * @param x
	 * @param y
	 * @param f
	 */
	public void setFoodAt(int x, int y, int f){
		game.theWorld.theMap[x][y].food = f;
	}
	
	/**
	 * true if the cell at position x,y is in the anthill of color c
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public boolean antHillAt(int x, int y, colour c){
		if(game.theWorld.theMap[x][y].antHill == true){
			if(game.theWorld.theMap[x][y].species.equals("+") && c == colour.red){
				return true;
			}
			if(game.theWorld.theMap[x][y].species.equals("-") && c == colour.black){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * set marker i of color c in cell x,y
	 * @param x
	 * @param y
	 * @param i
	 */
	public void setMarkerAt(int x, int y, colour c, String i){
		switch(c){
			case red: game.theWorld.theMap[x][y].setRedHormone(i);
			case black: game.theWorld.theMap[x][y].setBlackHormone(i);
		}
	}
	/**
	 * clear marker i of color c in cell x,y
	 * @param x
	 * @param y
	 * @param i
	 */
	public void clearMarkerAt(int x, int y, colour c,  String i){
		switch(c){
		case red: game.theWorld.theMap[x][y].removeRedHormone(i);
		case black: game.theWorld.theMap[x][y].removeBlackHormone(i);
		}
	}
	
	/**
	 * true if marker i of color c is set in cell x,y
	 * @param x
	 * @param y
	 * @param c
	 * @param i
	 * @return
	 */
	public boolean checkMarkerAt(int x, int y, colour c, String i){
		switch(c){
			case red:
				for(String s:game.theWorld.theMap[x][y].redHormone){
					if(i.toLowerCase().trim().equals(s)){
						return true;
					}
				}
			case black:
				for(String s:game.theWorld.theMap[x][y].blackHormone){
					if(i.toLowerCase().trim().equals(s)){
						return true;
					}
				}
		}
		return false;
	}
	
	/**
	 * true if ANY marker of color c is set in cell x,y
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public boolean checkAnyMarkerAt(int x, int y, colour c){
		switch(c){
		case red:
			if(game.theWorld.theMap[x][y].redHormone.size()>0){
				return true;
			}
		case black:
			if(game.theWorld.theMap[x][y].blackHormone.size()>0){
				return true;
			}
		}
		return false;
	}
	
	public colour colour(Ant a){
		if(a.species.equals("red")){
			return colour.red;
		} else {
			return colour.black;
		}
	}
	
	public boolean hasFood(Ant a){
		return a.hasFood;
	}
	
	
	public boolean cellMatches(int x, int y, condition cond, colour col, String m){
		if(game.theWorld.theMap[x][y].rock){
			if(cond == condition.rock){
				return true;
			} else {
				return false;
			}
		} else {
			switch(cond){
			case friend:
				return (someAntIsAt(x, y) && colour(antAt(x, y)) == col);
			case foe:
				return (someAntIsAt(x,y) && colour(antAt(x,y)) != col);
			case friendwithfood:
				return (someAntIsAt(x,y) && colour(antAt(x, y)) == col && hasFood(antAt(x,y)));
			case foewithfood:
				return (someAntIsAt(x,y) && colour(antAt(x,y)) != col && hasFood(antAt(x,y)));
			case food:
				return (foodAt(x,y) > 0);
			case rock:
				return false;
			case marker:
				return checkMarkerAt(x, y, col, m);
			case foemarker:
				return checkAnyMarkerAt(x, y, otherColour(col));
			case home:
				return antHillAt(x, y, col);
			case foehome:
				return antHillAt(x, y, otherColour(col));
			}
		}
		return false;
		
	}
	
	public command getInstruction(colour c, int state){
		switch(c){
		case red: 
			if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("mark")){
				return command.mark;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("unmark")){
				return command.unmark;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("pickup")){
				return command.pickup;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("drop")){
				return command.drop;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("turn")){
				return command.turn;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("move")){
				return command.move;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("flip")){
				return command.flip;
			} else if(game.brain1.stateList.get(state).arg1.toLowerCase().equals("sense")){
				return command.sense;
			}
		case black:
			if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("mark")){
				return command.mark;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("unmark")){
				return command.unmark;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("pickup")){
				return command.pickup;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("drop")){
				return command.drop;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("turn")){
				return command.turn;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("move")){
				return command.move;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("flip")){
				return command.flip;
			} else if(game.brain2.stateList.get(state).arg1.toLowerCase().equals("sense")){
				return command.sense;
			}
		}
		return null;
	}
	
	public int adjacentAnts(int x, int y, colour c){
		int numAdjacentAnts = 0;
		int[] cell;
		for(int d = 0; d < 6; d++){
			cell = adjacentCell(x, y, d);
			if(someAntIsAt(cell[0], cell[1]) && colour(antAt(cell[0], cell[1]))== c){
				numAdjacentAnts++;
			}
		}
		return numAdjacentAnts;
	}
	
	public void checkForSurroundedAntAt(int x, int y){
		if(someAntIsAt(x, y)){
			Ant a = antAt(x, y);
			int additionFood;
			if(a.hasFood){
				additionFood = 1;
			} else {
				additionFood = 0;
			}
			if(adjacentAnts(x, y, otherColour(colour(a))) >= 5){
				killAntAt(x, y);
				setFoodAt(x, y, foodAt(x, y)+ 3 + additionFood);
			}
		}
	}
	
	public void checkForSurroundedAnts(int x, int y){
		checkForSurroundedAntAt(x, y);
		for(int d = 0; d < 6; d++){
			int[] b = adjacentCell(x, y, d);
			checkForSurroundedAntAt(b[0], b[1]);
		}
	}
	
	int[] xSeries;
	int[] sSeries;
	int xi = 0;
	public int randomInt(int n){
		xi++;
		int seed = 12345;
		sSeries = new int[99];
		xSeries = new int[99];
		sSeries[0] = seed;
		for(int i = 1; i < 99; i++){
			sSeries[i] = (sSeries[i-1] * 22695477) + 1;
		}
		for(int j = 0; j < 99-4; j++){
			xSeries[j] = (sSeries[j+4] / 65536)%16384;
			//System.out.println(xSeries[j]);
		}
		//System.out.println(xSeries[xi]%n);
		return (xSeries[xi]%n);
	}
	
	public senseDir getSenseDir(String s){
		if(s.trim().toLowerCase().equals("here")){
			return senseDir.here;
		} else if(s.trim().toLowerCase().equals("ahead")){
			return senseDir.ahead;
		} else if(s.trim().toLowerCase().equals("leftahead")){
			return senseDir.leftahead;
		} else if(s.trim().toLowerCase().equals("rightahead")){
			return senseDir.rightahead;
		}
		return null;
	}
	
	public condition getSenseCondition(String s){
		if(s.trim().toLowerCase().equals("friend")){
			return condition.friend;
		} else if(s.trim().toLowerCase().equals("foe")){
			return condition.foe;
		} else if(s.trim().toLowerCase().equals("friendwithfood")){
			return condition.friendwithfood;
		} else if(s.trim().toLowerCase().equals("foewithfood")){
			return condition.foewithfood;
		} else if(s.trim().toLowerCase().equals("food")){
			return condition.food;
		} else if(s.trim().toLowerCase().equals("rock")){
			return condition.rock;
		} else if(s.trim().toLowerCase().equals("marker")){
			return condition.marker;
		} else if(s.trim().toLowerCase().equals("foemarker")){
			return condition.foemarker;
		} else if(s.trim().toLowerCase().equals("home")){
			return condition.home;
		} else if(s.trim().toLowerCase().equals("foehome")){
			return condition.foehome;
		}
		return null;
	}
	
	public left_right getLR(String s){
		if(s.trim().toLowerCase().equals("left")){
			return left_right.left;
		} else if(s.trim().toLowerCase().equals("right")){
			return left_right.right;
		}
		return null;
	}
	
	public void step(int id){
		int p[];
		int p1[];
		Ant a;
		int state;
		int newState;
		Brain brain;
		String arg1;
		String arg2;
		String arg3;
		String arg4;
		String arg5;
		if(antIsAlive(id)){
			p = findAnt(id);
			a = antAt(p[0], p[1]);
			if(colour(a) == colour.red){
				brain = game.brain1;
			} else {
				brain = game.brain2;
			}
			state = a.theState;
			arg1 = brain.stateList.get(state).arg1;
			arg2 = brain.stateList.get(state).arg2;
			arg3 = brain.stateList.get(state).arg3;
			arg4 = brain.stateList.get(state).arg4;
			arg5 = brain.stateList.get(state).arg5;
			if(a.resting > 0){
				a.resting =  a.resting -1;
			} else {
				switch(getInstruction(colour(a), state)){
				case sense:
					p1 = sensedCell(p[0], p[1], a.direction, getSenseDir(arg1));
					if(cellMatches(p1[0], p1[1], getSenseCondition(arg5), colour(a), arg4)){
						newState = Integer.parseInt(arg2);
					} else {
						newState = Integer.parseInt(arg3);
					}
					a.setState(newState);
				case mark:
					setMarkerAt(p[0],p[1],colour(a), arg1);
					a.setState(Integer.parseInt(arg3));
				case unmark:
					clearMarkerAt(p[0], p[1], colour(a), arg1);
					a.setState(Integer.parseInt(arg3));
				case pickup:
					if(hasFood(a) || foodAt(p[0], p[1]) == 0){
						a.theState = Integer.parseInt(arg2);
					} else {
						setFoodAt(p[0], p[1], foodAt(p[0], p[1]) -1);
						a.hasFood = true;
						a.theState = Integer.parseInt(arg1);
					}
				case drop:
					if(hasFood(a)){
						setFoodAt(p[0], p[1], foodAt(p[0], p[1]) + 1);
						a.hasFood = false;
					}
					a.theState = Integer.parseInt(arg1);
					//calculate food collected stats
				case turn:
					a.direction = turn(getLR(arg1), a.direction);
					a.theState = Integer.parseInt(arg2);
				case move:
					p1 = adjacentCell(p[0], p[1], a.direction);
					if(game.theWorld.theMap[p1[0]][p1[1]].rock || someAntIsAt(p1[0], p1[1])){
						a.theState = Integer.parseInt(arg2);
					} else {
						//clearAntAt(p[0], p[1]);
						setAntAt(p1[0], p1[1], a);
						a.theState = Integer.parseInt(arg1);
						a.resting = 14;
						checkForSurroundedAnts(p1[0], p1[1]);
						//calculate kill stats
					}
				case flip:
					if(randomInt(Integer.parseInt(arg1)) == 0){
						newState = Integer.parseInt(arg2);
					} else {
						newState = Integer.parseInt(arg3);
					}
				}
			}
		}
	}
//	public static void main(String args[]){
//		World world = new World(70, 70);
//		world.populateMap();
//		Game game = new Game
//		Simulator s = new Simulator(new Game(world, new Brain(), new Brain()), new Monitor());
//		s.run();
//	}
}
