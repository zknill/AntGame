import java.util.ArrayList;
import java.util.regex.*;
/**
 * Checks if the brain file that has been uploaded is syntactically correct
 * @author zk44
 * @version 1
 * 
 * TODO: finish regex for mark, count lines, return error message with line number and command that was wrong
 * 
 */

public class BrainParser {
	public ArrayList<String> lines;
	Brain brain;
	public BrainParser(){
		lines = new ArrayList<String>();
	}
	public boolean parse(ArrayList<String> inLines){
		lines = inLines;
		boolean passed = true;
		Pattern pattern;
		int index = 0;
		Matcher matcher;
		brain = new Brain();
		ArrayList<String> args = new ArrayList<String>();
		for(String line: lines){
			index++;
			//create a new matcher to get the args from the line
			pattern = Pattern.compile("\\w+");
			matcher = pattern.matcher(line.toLowerCase().trim());
			
			//clear the list that stores the args
			args.clear();
			
			//regex for flip
			if(line.toLowerCase().trim().matches("flip(\\s+\\d+){3}")){
				System.out.println("Match flip");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
						
				brain.addState(new State(args.get(0), args.get(1), args.get(2), args.get(3)));
				
				for(String s:args){
					System.out.println(s);
				}
			} else if(line.toLowerCase().trim().matches("turn\\s+(right|left)\\s+\\d*")){
				//regex for turn
				System.out.println("Match turn");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				
				brain.addState(new State(args.get(0), args.get(1), args.get(2)));
				
				for(String s:args){
					System.out.println(s);
				}
				
			} else if(line.toLowerCase().trim().matches("move(\\s+\\d+){2}")){
				//regex for move
				System.out.println("Match move");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				
				brain.addState(new State(args.get(0), args.get(1), args.get(2)));
				
				for(String s:args){
					System.out.println(s);
				}
				
				
			} else if(line.toLowerCase().trim().matches("pickup(\\s+\\d+){2}")){
				//regex for pickup
				System.out.println("Match pickup");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				
				brain.addState(new State(args.get(0), args.get(1), args.get(2)));
				
				for(String s:args){
					System.out.println(s);
				}
				
				
			} else if(line.toLowerCase().trim().matches("drop(\\s+\\d+)")){
				//regex for drop
				System.out.println("Match drop");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				
				brain.addState(new State(args.get(0), args.get(1)));
				
				for(String s:args){
					System.out.println(s);
				}
				
				
			} else if(line.toLowerCase().trim().matches("mark(\\s+\\d+){2}\\s+;.+")){
				//regex for mark - unfinished?
				System.out.println("Match mark");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				
				brain.addState(new State(args.get(0), args.get(1), args.get(2), args.get(3)));
				
				for(String s:args){
					System.out.println(s);
				}
				
				
				
			} else if(line.toLowerCase().trim().matches("unmark(\\s+\\d+){2}")){
				//regex for unmark
				System.out.println("Match unmark");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				
				brain.addState(new State(args.get(0), args.get(1), args.get(2)));
				
				for(String s:args){
					System.out.println(s);
				}
				
				
			} else if(line.trim().toLowerCase().matches("sense\\s+(here|ahead|leftahead|rightahead)(\\s+\\d+){2}\\s+\\w+((\\s+)(\\d)|\\s+;\\s+\\w+)?")){
				//regex for sense
				//sense\\s+(here|ahead|leftahead|rightahead)(\\s+\\d+){2}\\s+(\\w+)\\s+(\\;\\s)?\\w+
				System.out.println("Match sense");
				
				while(matcher.find()){
					args.add(matcher.group());
				}
				if(args.size()<6){
					for(int i = args.size(); i < 6; i++){
						args.add("");
					}
				}
				
				brain.addState(new State(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5)));
				
				for(String s:args){
					System.out.println(s);
				}
				
				
			} else {
				passed = false;
				System.out.println("Failed on line: "+ index+ ", " +"'"+ line+ "'");
				break;
			}
			
		}
		return passed;
	}
	public Brain getBrain(){
		return brain;
	}
	
	public static void main(String args[]){
		BrainParser parser = new BrainParser();
		FileIO reader = new FileIO();
		String fName = "N:/Documents/Software Engineering/sample.txt";
		ArrayList<String> lines = reader.readFile(fName);
		//lines.add("Sense Ahead 1 3 Food");
		parser.parse(lines);
	}
}
