import java.util.ArrayList;

/**
 * This Class is the controller class for the project. It will create game, and call methods to simulate them.
 * @author zk44
 *
 */
public class GameManager extends Thread {
	ArrayList<Game> gameList;
	int currentGameIndex;
	int fps;
	public GameManager(){
		gameList = new ArrayList<Game>();
		currentGameIndex = 0;
	}
	
	public void setFPS(int i){
		fps = i;
	}
	
	public void addGame(Game game){
		gameList.add(game);
	}
	

	public void runNextGame(){
		Monitor monitor = new Monitor();
		Game currentGame = gameList.get(currentGameIndex);
		Simulator simulator = new Simulator(currentGame, monitor);
		simulator.start();
		synchronized(monitor){
			for(int i = 0; i < 20; i++){
				monitor.notify();
				System.out.println("gm"+i);
				//repaint GUI
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//create a thread for the simulator to run on
		//after each iteration of simulator
	}

	
	public void getStats(){
		//return stats object?
	}
	
	public void createAnts(Game game){
		int antID = 0;
		World world = game.theWorld;
		//loop through the world, loop across then step down
		for(int x = 0; x < world.xSize; x++){
			//get each line
			for(int y = 0; y < world.ySize; y++){
				//traverse line
				//if ant hill, + create red ant, - create black ant
				if(world.theMap[x][y].antHill){
					if(world.theMap[x][y].species.equals("+")){
						//create red ant
						game.antList1.add(new Ant("red", x, y, antID));
						game.maxAntID = antID;
						antID++;
					} else if (world.theMap[x][y].species.equals("-")){
						//create black ant
						game.antList2.add(new Ant("black", x, y, antID));
						game.maxAntID = antID;
						antID++;
					}
				}
			}
		}
		System.out.println("red: "+ game.antList1.size() + " black: "+ game.antList2.size() + " total ids: " + antID);
		System.out.println(game.maxAntID);
	}
	
	public static void main(String args[]){
		GameManager gm = new GameManager();
		World randWorld = new World(50, 50);
		randWorld.populateMap();
		randWorld.printMap();
		BrainParser parser = new BrainParser();
		FileIO reader = new FileIO();
		String fName = "N:/Documents/Software Engineering/sample.txt";
		ArrayList<String> lines = reader.readFile(fName);
		parser.parse(lines);
		Brain brain1 = parser.getBrain();
		Brain brain2 = parser.getBrain();
		Game game = new Game(randWorld, brain1, brain2);
		gm.addGame(game);
		gm.createAnts(game);
		Simulator sim = new Simulator(gm.gameList.get(0), new Monitor());
		sim.run();
		//gm.runNextGame();
		//gm.createAnts(new Game(randWorld, new Brain(), new Brain()));
	}
	
}
