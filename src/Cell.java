import java.util.ArrayList;


public class Cell {
	public boolean rock;
	public String species;
	public ArrayList<String> redHormone;
	public ArrayList<String> blackHormone;
	public boolean antHill;
	public int food;
	public Cell(boolean r, int f,  boolean antHill){
		species = "";
		this.antHill = antHill;
		rock = r;
		food = f;
		redHormone = new ArrayList<String>();
		blackHormone = new ArrayList<String>();
	}
	public Cell(boolean r, int f,  boolean antHill, String species){
		this.species = species;
		this.antHill = antHill;
		rock = r;
		food = f;
	}
	public void pickFood(){
		food = food -1;
	}
	public void setRedHormone(String h){
		redHormone.add(h);
	}
	public void removeRedHormone(String h){
		redHormone.remove(h);
	}
	public void setBlackHormone(String h){
		blackHormone.add(h);
	}
	public void removeBlackHormone(String h){
		blackHormone.remove(h);
	}
}
