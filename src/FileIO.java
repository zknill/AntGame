import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * Reads a file and returns an ArrayList<String> Collection
 * @author zk44
 * @version 1
 */
public class FileIO {
	public FileIO(){
		
	}
	/**
	 * Reads the file
	 * @param fName	filename/location of the file to be read
	 * @return ArrayList<String> collection of lines in the file.
	 */
	public ArrayList<String> readFile(String fName){
		ArrayList<String> fileLines = new ArrayList<String>();
		//the filename is set
		File filename = new File(fName);
		//String variable to hold each line
		String line = "";
		try{
			//Create the file reader
			FileReader inputFile = new FileReader(filename);
			
			//Create the line reader
			BufferedReader lineReader = new BufferedReader(inputFile);
			
			//Read file line by line and add to fileLines
			while ((line = lineReader.readLine()) != null){
				fileLines.add(line);
			}
			
			//Close the BufferedReader (File)
			lineReader.close();
			
		} catch (Exception e){
			System.out.println("Error while reading file line by line: " + e.getMessage());
		}
		//return the collection of lines. 
		return fileLines;
	}
}
