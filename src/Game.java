import java.util.ArrayList;


public class Game {
	public World theWorld;
	public Brain brain1;
	public Brain brain2;
	int maxAntID;
	//red ant list
	public ArrayList<Ant> antList1;
	//black ant list
	public ArrayList<Ant> antList2;
	public Game(World w, Brain b1, Brain b2){
		antList1 = new ArrayList<Ant>();
		antList2 = new ArrayList<Ant>();
		theWorld = w;
		brain1 = b1;
		brain2 = b2;
	}
	//Setter methods
	public void setAntList1(ArrayList<Ant> a){
		antList1 = a;
	}
	public void setAntList2(ArrayList<Ant> a){
		antList2 = a;
	}
	
	//Getter methods
	public Brain getBrain1(){
		return brain1;
	}
	public Brain getBrain2(){
		return brain2;
	}
	public World getWorld(){
		return theWorld;
	}
	public ArrayList<Ant> getAntList1(){
		return antList1;
	}
	public ArrayList<Ant> getAntList2(){
		return antList2;
	}
}
