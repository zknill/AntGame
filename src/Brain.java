import java.util.ArrayList;

/**
 * 	Class to represent the Brain of a single species of ant
 *   @author zk44
 *   @version 1 Date: 24th March
 *
 */
public class Brain {
	ArrayList<State> stateList;
	public Brain(){
		stateList = new ArrayList<State>();
	}
	public void addState(State s){
		stateList.add(s);
	}
}
