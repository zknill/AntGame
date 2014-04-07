import java.util.ArrayList;


public class WorldParser {
	World theWorld;
	ArrayList<String> lines;
	public WorldParser(){
		lines = new ArrayList<String>();
	}
	public boolean parse(ArrayList<String>lines){
		boolean passed = true;
		int xSize = 0;
		int ySize = 0;
		int hill1 = 0;
		int hill2 = 0;
		int foodLine = 0;
		this.lines = lines;
		String[] thisLine;
		//get the size of the map from the first two lines, and parse into Integers
		xSize = Integer.parseInt(lines.get(0));
		ySize = Integer.parseInt(lines.get(1));
		
		//create the new world
		theWorld = new World(xSize, ySize);
		
		//Testing:
		System.out.println("x: " + xSize + " y: " + ySize);
		
		//iterate over the lines, ignoring the first two lines
		for(int y = 2; y < lines.size(); y++){
			lines.get(y).replaceAll("\\s","");
			thisLine = lines.get(y).split("(?!^)");
			System.out.println(lines.get(y));
			//System.out.println(thisLine.length+"");
			//iterate over each character in the line
			//thisLine.length should = xSize
			
			//lineOffset is to compensate for the space between cell data
			int lineOffset=0;
			for(int x =0; x < thisLine.length; x++){
				//System.out.println("the character: |"+thisLine[x]+"|");
				
				//reset foodLine
				foodLine = 0;
				try{
					foodLine = Integer.parseInt(thisLine[x]);
				} catch (Exception e){
					//ignore
				}
				if(thisLine[x].trim().equals(".")){
					//if character is an empty cell
					//add empty cell
					//System.out.println(x-lineOffset+ ","+ y);
					//System.out.println("added:" + thisLine[x]);
					theWorld.addCell(new Cell(false, 0,  false), x-lineOffset, y-2);
				} else if(thisLine[x].equals("#")){
					//if the cell is a rock
					//add a rock cell
					//System.out.println(x-lineOffset+ ","+ y);
					//System.out.println("added:" + thisLine[x]);
					theWorld.addCell(new Cell(true, 0,  false), x-lineOffset, y-2);
				} else if(thisLine[x].equals("+")){
					//if the cell is a + antHill
					//add an antHill cell
					//System.out.println(x-lineOffset+ ","+ y);
					//System.out.println("added:" + thisLine[x]);
					hill1++;
					theWorld.addCell(new Cell(false, 0, true, "+"), x-lineOffset, y-2);
				} else if(thisLine[x].equals("-")){
					//if the cell is a - antHill
					//add an antHill cell
					//System.out.println(x-lineOffset+ ","+ y);
					//System.out.println("added:" + thisLine[x]);
					hill2++;
					theWorld.addCell(new Cell(false, 0, true, "-"), x-lineOffset, y-2);
				} else if(foodLine > 0){
					theWorld.addCell(new Cell(false, foodLine, false), x-lineOffset, y-2);
				} else if(thisLine[x].trim().equals("")){
					//compensate for the space between cell data
					lineOffset++;
					//System.out.println("line offset:" +lineOffset);
				} else {
					System.out.println("Parser Failed at ("+x +","+(y-2)+") with the character: "+thisLine[x]);
					passed = false;
				}
			}
		}
		if(hill1 != hill2){
			System.out.println("Hills are not the same size");
			return false;
		}
		return passed;

	}
	public static void main(String args[]){
		String f = "N:/Documents/Software Engineering/a.world";
		WorldParser parser = new WorldParser();
		FileIO reader = new FileIO();
		ArrayList<String> theLines = reader.readFile(f);
		parser.parse(theLines);
	}
}

