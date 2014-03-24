/**
 * Class that holds a single state in the brain of a single species of ant
 * @author zk44
 * @version 1 Date: 24th March
 * 
 * TODO: fix javadoc
 * 
 */

public class State {
	public String command;
	public String arg1;
	public String arg2;
	public String arg3;
	public String arg4;
	public String arg5;
	
	
	public State(String c, String state1){
		command = c;
		arg1 = state1;
	}
	/**
	 * Creates a new state with a command and two arguments
	 * @param c 		the command for the state
	 * @param state1	the state if command holds
	 * @param state2	the state if command doesn't hold
	 */
	public State(String c, String state1, String state2){
		command = c;
		arg1 = state1;
		arg2 = state2; 
	}
	/**
	 * Creates a new state for the flip command or the mark command
	 * @param c			the command for the state
	 * @param p			the first argument
	 * @param state1	the state if flip 0
	 * @param state2	the state if flip > 0
	 */
	public State(String c, String a1, String a2, String a3){
		command = c;
		arg1 = a1;
		arg2 = a2;
		arg3 = a3;
	}
	/**
	 * Creates a new state for the Sense Command
	 * @param sense		the command 'sense'
	 * @param dir		the direction to sense in
	 * @param state1	the state if cond holds
	 * @param state2	the state if cond doesn't hold
	 * @param marker	the marker to set
	 * @param cond		the cond; food, home, ??
	 */
	public State(String sense, String dir, String state1, String state2, String a4, String a5){
		command = sense;
		arg1 = dir;
		arg2 = state1;
		arg3 = state2;
		arg4 = a4;
		arg5 = a5;
	}
}
