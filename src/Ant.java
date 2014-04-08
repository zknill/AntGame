
public class Ant{
	public String species;
	public int theState;
	public int antID;
	public boolean isDead;
	public boolean hasFood;
	public int direction;
	public int[] position;
	public int resting;
	public int totalFoodCollected;
	public int totalKills;
	public Ant(String spec, int x, int y, int z){
		species = spec;
		position = new int[2];
		position[0]= x;
		position[1]= y;
		antID = z;
		resting = 0;
		direction = 0; //facing east???
		totalFoodCollected = 0;
		totalKills = 0;
		hasFood = false;
		isDead = false;
		theState = 0;
	}
	
	//Setter Methods
	public void setState(int i){
		theState = i;
	}
	public void setResting(int i){
		resting = i;
	}
	public void setDirection(int i){
		direction = i;
	}
	public void setHasFood(boolean b){
		hasFood = b;
	}
	public void setTotalFood(int i){
		totalFoodCollected = totalFoodCollected +i;
	}
	public void setTotalKills(int i){
		totalKills = totalKills + i;
	}
	public void setPosition(int x, int y){
		position[0] = x;
		position[1] = y;
	}
	
	
	//Getter Methods
	public int[] getPos(){
		return position;
	}
	public boolean hasFood(){
		return hasFood;
	}
	public boolean isResting(){
		if(resting > 0){
			return true;
		} else {
			return false;
		}
	}
	public int getDirection(){
		return direction;
	}
	public int getTotalFood(){
		return totalFoodCollected;
	}
	public int getTotalKills(){
		return totalKills;
	}
	
	
}
