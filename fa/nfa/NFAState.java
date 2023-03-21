package fa.nfa;

import fa.State;

import java.util.*;


/*
In fa.nfa package NFAState class must extend fa.State abstract class. We recommend
that you add an instance variable to model the state’s transitions with the following map
Map<Character, Set<NFAState>>. If your implementation requires it, you can add addi-
tional instance variables and methods to your NFAState class.
 */

public class NFAState extends State {

    private Map<Character, Set<NFAState>> transitions = new HashMap<>();

    boolean isStart = false;

    public NFAState(String name) {
        super(name);
    }

    //handle transition
    public void setTransition(Character onSymb, Set<NFAState> toState) {
        this.transitions.put(onSymb, toState);
    }

    public NFAState getTransition(NFAState key) {
        return (NFAState) transitions.get(key);
    }

    public void setStartState(boolean makeStart) {
        this.isStart = makeStart;
    }

    public boolean getStartState() {
        return this.isStart;
    }

    public Set<NFAState> toStates(char c) {
        return null;
    }
}
