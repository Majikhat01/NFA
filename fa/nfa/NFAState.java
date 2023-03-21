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

    public NFAState(String name) {
        super(name);
    }

    //handle transition
    public void setTransition() {

    }

    public NFAState getTransition() {
        return null;
    }
}
