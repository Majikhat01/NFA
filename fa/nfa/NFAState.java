package fa.nfa;
import fa.State;
import java.util.*;

public class NFAState extends State {

    private Map<Character, Set<NFAState>> transitions;
    boolean isStart;
    boolean visited;

    //Constructor
    public NFAState(String name) {

        super(name);
        transitions = new HashMap<>();
        isStart = false;
        visited = false;
    }

    //handle transition
    public void setTransition(Character onSymb, Set<NFAState> toState) {
        if (transitions.containsKey(onSymb)) {
            transitions.get(onSymb).addAll(toState);
        } else {
            this.transitions.put(onSymb, toState);
        }
    }

    public Set<NFAState> getTransition(Character key) {
        if (transitions.get(key) != null) {
            return transitions.get(key);
        }
        return null;
    }

    //Handle start state
    public void setStartState(boolean makeStart) {
        this.isStart = makeStart;
    }

    public boolean getStartState() {
        return this.isStart;
    }

    //This method was used in NFATest.java, but was told to comment out the test by the instructor via piazza
    public Set<NFAState> toStates(char c) {
        Set<NFAState> returnStates = transitions.get(c);
        return returnStates;
    }
}
