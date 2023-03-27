package fa.nfa;

import java.util.*;

public class NFA implements NFAInterface {

    //Private variables
    private Set<NFAState> states = new LinkedHashSet<>();
    private Set<Character> sigma = new LinkedHashSet<>();
    private Set<NFAState> finalStates = new LinkedHashSet<>();

    //Constructor
    public NFA() {}

    @Override
    public boolean addState(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                return false;
            }
        }

        states.add(new NFAState(name));
        return true;
    }

    //--Public Methods--
    @Override
    public boolean setFinal(String name) {
        boolean added = false;

        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                finalStates.add(state);
                added = true;
            }
        }

        return added;
    }

    @Override
    public boolean setStart(String name) {
        boolean startSet = false;

        if (states.contains(getState(name))) {

            for (NFAState state : states) {
                if (state.isStart) {
                    state.setStartState(false);
                }
            }

            for (NFAState state : states) {
                if (state.getName().equals(name)) {
                    state.setStartState(true);
                    startSet = true;
                }
            }
        }

        return startSet;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {

        //Initializing copies and queue and accept boolean
        boolean doesAccept = false;
        Set<NFAState> stateQueue = new LinkedHashSet<>();
        Set<NFAState> stateCopies = new LinkedHashSet<>();

        //Push start state into queue
        for (NFAState state : states) {
            if (state.isStart) {
                stateCopies.add(state);
                break;
            }
        }

        //If no start state, exit
        if (stateCopies.size() == 0) {
            return false;
        }

        //check start state for eClosure, add them to current states
        stateCopies.addAll(eClosure(stateCopies.iterator().next()));

        //Traversing the string using BFS
        for (int i = 0; i < s.length(); i++) {

            Set<NFAState> tempSet = new LinkedHashSet<>();

            //Check current states for transitions on 's.charAt(i)' and add to stateQueue
            for (NFAState state : stateCopies) {
                if (state.getTransition(s.charAt(i)) != null) {
                    stateQueue.addAll(state.getTransition(s.charAt(i)));
                }
            }

            //Check all 'states to check next' for eClosure and add to tempSet
            for (NFAState state : stateQueue) {
                if (state.getTransition('e') != null) {
                    tempSet.addAll((eClosure(state)));
                }
            }

            stateCopies.clear(); //Clear all visited states
            stateCopies.addAll(stateQueue); //transfer all 'states to check next' into current states
            stateQueue.clear(); //Clear the queue
            stateCopies.addAll(tempSet); //Transfer all eClosure states into current states
        }

        //after iterating through entire string, check if all current states contain a final state
        for (NFAState state : stateCopies) {
            if (finalStates.contains(state)) {
                doesAccept = true;
                break;
            }
        }

        return doesAccept;
    }

    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<>(sigma);
    }

    @Override
    public NFAState getState(String name) {
        for (NFAState state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }

        return null;
    }

    @Override
    public boolean isFinal(String name) {
        boolean isAFinal = false;

        for (NFAState state : finalStates) {
            if (state.getName().equals(name)) {
                isAFinal = true;
                break;
            }
        }

        return isAFinal;
    }

    @Override
    public boolean isStart(String name) {
        boolean isTheStart = false;

        for (NFAState state: states) {
            if (state.getStartState() && state.getName().equals(name)) {
                isTheStart = true;
                break;
            }
        }

        return isTheStart;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {

        //create set, stack, and tracker state
        Set<NFAState> eTransitions = new LinkedHashSet<>();
        Stack<NFAState> statesToVisit = new Stack<>();
        NFAState currentState;

        eTransitions.add(s); //add the first state to the set of states to return
        statesToVisit.push(s); //add the first state to the stack

        //reset all visited flags
        for (NFAState state : states) {
            state.visited = false;
        }

        while (!statesToVisit.isEmpty()) {
            currentState = statesToVisit.pop(); //pop the first state to look at

            if (!currentState.visited) { //check if already visited
                if (currentState.getTransition('e') != null) { //check for an 'e' transition
                    eTransitions.addAll(currentState.getTransition('e')); //add the states to the set to be returned
                    statesToVisit.addAll(currentState.getTransition('e')); //add the states to the stack to be checked next
                }

                currentState.visited = true; //flag the state as visited
            }
        }

        return eTransitions;
    }

    @Override
    public int maxCopies(String s) {

        //Initializing copies, queue, and maxCopies
        int maxCopies;
        Set<NFAState> stateQueue = new LinkedHashSet<>();
        Set<NFAState> stateCopies = new LinkedHashSet<>();

        //Push start state into queue
        for (NFAState state : states) {
            if (state.isStart) {
                stateCopies.add(state);
                break;
            }
        }

        //If no start state, exit
        if (stateCopies.size() == 0) {
            return -1;
        }

        //check start state for eClosure, add them to current states
        stateCopies.addAll(eClosure(stateCopies.iterator().next()));
        maxCopies = stateCopies.size();

        //Traversing the string using BFS
        for (int i = 0; i < s.length(); i++) {

            Set<NFAState> tempSet = new LinkedHashSet<>();

            //Check current states for transitions on 's.charAt(i)' and add to stateQueue
            for (NFAState state : stateCopies) {
                if (state.getTransition(s.charAt(i)) != null) {
                    stateQueue.addAll(state.getTransition(s.charAt(i)));
                }
            }

            //Check all 'states to check next' for eClosure and add to tempSet
            for (NFAState state : stateQueue) {
                if (state.getTransition('e') != null) {
                    tempSet.addAll((eClosure(state)));
                }
            }

            stateCopies.clear(); //Clear all visited states
            stateCopies.addAll(stateQueue); //transfer all 'states to check next' into current states
            stateQueue.clear(); //Clear the queue
            stateCopies.addAll(tempSet);//Transfer all eClosure states into current states

            //record max number of copies
            if (maxCopies < stateCopies.size()) {
                maxCopies = stateCopies.size();
            }
        }

        return maxCopies;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {

        //create function variables
        boolean transitionAdded = false;
        NFAState tempFromState = this.getState(fromState);
        Set<NFAState> tempToStates = new LinkedHashSet<>();

        for (String name : toStates) {
            if (states.contains(getState(name))) { //check the set if each name exists as a state
                tempToStates.add(this.getState(name)); //add it to the set
            } else {
                return false; //if it doesn't exist, exit
            }
        }

        if (tempFromState == null) { //check if fromState exists in the NFA
            return false;
        } else if (tempToStates.isEmpty()) { //check if it's trying to transition to nothing
            return false;
        } else if (!sigma.contains(onSymb) && onSymb != 'e') { //check if onSymb is in the sigma and isn't 'e'
            return false;
        } else {
            for (NFAState state : states) {
                if (state.getName().equals(fromState)) { //find the fromState and create the transition
                    state.setTransition(onSymb, tempToStates);
                    transitionAdded = true; //flag that it was added
                }
            }
        }

        return transitionAdded;
    }

    //determines if an NFA is actually a DFA, i.e., all of its transitions obey the rule’s of DFA transitions.
    @Override
    public boolean isDFA() {

        boolean isDFA = true;

        for (NFAState state: states) { //check each state's transitions for multiple states or 'e' transitions
            for (Character key: sigma) {
                if (state.getTransition(key) != null && state.getTransition(key).size() > 1) {
                    return false;
                }
            }

            if (state.getTransition('e') != null) {
                return false;
            }
        }

        return isDFA;
    }
}
