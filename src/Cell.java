
public class Cell {
	public boolean rock;
	public String species;
	public String hormone;
	public boolean antHill;
	public int food;
	public Cell(boolean r, int f,  boolean antHill){
		species = "";
		this.antHill = antHill;
		rock = r;
		food = f;
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
	public void setHormone(String h){
		hormone = h;
	}
}
