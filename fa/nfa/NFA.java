package fa.nfa;

import fa.State;

import java.util.*;

public class NFA implements NFAInterface {

    //Private methods
    private Set<NFAState> states = new LinkedHashSet<>();
    private Set<Character> sigma = new LinkedHashSet<>();
    private Set<NFAState> finalStates = new LinkedHashSet<>();

    //Public methods
    public NFA() {
    }

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
        /*
        -Traverse a graph using BFS, to keep track of all the NFA's copies created while following symbols in input.
        -For example, the accepts method of the above NFA for the input string “011” needs
        to simulate the following multi-copy configuration trace:
        ({a}, 011) |-- ({a}, 11) |-- ({a, b}, 1) |-- ({a, b}, ε)

        Where the copies are inside {} follow along the read symbols from one state to another.
        In the above example, at the beginning, there is only one copy in the start state a,
        after reading 0, there is only one copy in the same state 0. Next, after reading the first
        1, NFA creates two copies: one in state b (resulting from transitioning from a on 1),
        and another in state a (result of spanning a copy from b on ε transition). Copies that
        end up in the same state after a transition or closure are considered to be identical and
        merged into one copy.
        The method returns false when after reading all the symbols, none of the copies are
        in any accepting state (that includes when no alive copies remain).
         */

        //Step 1: Initially queue and visited arrays are empty
        //Initializing NFA states and accept boolean
        boolean doesAccept = false;
        NFAState startState = null;
        NFAState currentState = new NFAState("current");
        LinkedList<NFAState> stateQueue = new LinkedList<>();

        //Step 2: Push node 0 into queue and mark it visited
        for (NFAState state : states) {
            if (state.isStart) {
                startState = state;
                stateQueue.add(state);
                state.visited = true;
                break;
            } else {
                state.visited = false;
            }
        }

        LinkedHashSet<NFAState> currentStates = new LinkedHashSet<>();
        currentStates.add(startState);
        currentState = stateQueue.remove();

        //traversing the BFS
        for (int i = 0; i < s.length(); i++) {


            //Step 3: Remove node from the front of queue and visit the unvisited neighbours and push them into queue
            if (currentState.getTransition(s.charAt(i)) != null) {
                stateQueue.addAll(currentState.getTransition(s.charAt(i)));
            }
            if (currentState.getTransition('e') != null) {
                stateQueue.addAll(currentState.getTransition('e'));
            }
            currentState.visited = true;

            if (!stateQueue.isEmpty()) {
                currentState = stateQueue.remove();
            }
        }

        //After queue becomes empty, so, terminate these process of iteration
        if (finalStates.contains(currentState)) {
            doesAccept = true;
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

        /*
        i.e., the epsilon closure
        function, computes the set of NFA states that can be reached from the argument state
        s by following only along ε transitions, including s itself. You must implement it using
        the depth-first search algorithm (DFS) using a stack in a loop, i.e., eClosure’s
        loop should push children of the current node on the stack, but only those children
        that are reachable through an ε transition, for which NFA uses reserved character ‘e’.

        ***Note: We strongly encourage you to implement eClosure first and then implement accepts
        method that calls eClosure.
         */

        Set<NFAState> eTransitions = new LinkedHashSet<>();
        Stack<NFAState> statesToVisit = new Stack<>();
        eTransitions.add(s);
        statesToVisit.push(s);
        NFAState currentState = new NFAState("start");

        for (NFAState state : states) {
            state.visited = false;
        }

        while (!statesToVisit.isEmpty()) {
            currentState = statesToVisit.pop();
            if (!currentState.visited) {
                if (currentState.getTransition('e') != null) {
                    eTransitions.addAll(currentState.getTransition('e'));
                    statesToVisit.addAll(currentState.getTransition('e'));
                }
                currentState.visited = true;
            }
        }

        return eTransitions;
    }

    @Override
    public int maxCopies(String s) {

        /*
        does similar simulation as accepts,
        only it returns the maximum number of copies a trace can generate. In the case of
        the above example, the method returns 2 since the maximum number of copies in the
        trace is {a, b} and |{a, b}| = 2.
         */

        boolean doesAccept = false;
        NFAState startState = null;
        NFAState currentState = new NFAState("current");
        LinkedList<NFAState> stateQueue = new LinkedList<>();
        int maxNumCopies = 0;

        //Step 2: Push node 0 into queue and mark it visited
        for (NFAState state : states) {
            if (state.isStart) {
                startState = state;
                stateQueue.add(state);
                state.visited = true;
            } else {
                state.visited = false;
            }
        }

        LinkedHashSet<NFAState> currentStates = new LinkedHashSet<>();
        currentStates.add(startState);
        maxNumCopies = currentStates.size();
        currentState = stateQueue.remove();

        //traversing the BFS
        for (int i = 0; i < s.length(); i++) {


            //Step 3: Remove node from the front of queue and visit the unvisited neighbours and push them into queue
            if (currentState.getTransition(s.charAt(i)) != null) {
                stateQueue.addAll(currentState.getTransition(s.charAt(i)));
            }
            if (currentState.getTransition('e') != null) {
                stateQueue.addAll(currentState.getTransition('e'));
            }

            currentState.visited = true;

            if (stateQueue.contains(currentState)) {
                currentStates.addAll(stateQueue);
            }

            if (!stateQueue.isEmpty()) {
                currentStates.remove(currentState);
                currentState = stateQueue.remove();
                currentStates.add(currentState);
                currentStates.addAll(eClosure(currentState));
                if (currentStates.size() > maxNumCopies) {
                    maxNumCopies = currentStates.size();
                }
            }
        }

        //After queue becomes empty, so, terminate these process of iteration
        if (finalStates.contains(currentState)) {
            doesAccept = true;
        }

        return maxNumCopies;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        boolean transitionAdded = false;

        NFAState tempFromState = this.getState(fromState);
        Set<NFAState> tempToStates = new LinkedHashSet<>();
        for (String name : toStates) {
            if (states.contains(getState(name))) {
                tempToStates.add(this.getState(name));
            }
        }



        if (tempFromState == null) {
            transitionAdded = false;
        } else if (tempToStates == null || tempToStates.isEmpty()) {
            transitionAdded = false;
        } else if (!sigma.contains(onSymb) && onSymb != 'e') {
            transitionAdded = false;
        } else {
            for (NFAState state : states) {
                if (state.getName().equals(fromState)) {
                    state.setTransition(onSymb, tempToStates);
                    transitionAdded = true;
                }
            }
        }
        return transitionAdded;
    }

    @Override
    public boolean isDFA() {

        /*
        determines if an NFA is actually a DFA, i.e., all of its transitions obey the rule’s of DFA transitions.
         */

        return false;
    }
}
