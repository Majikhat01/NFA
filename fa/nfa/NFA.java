package fa.nfa;

import fa.State;

import java.util.Set;

public class NFA implements NFAInterface {

    //Private methods


    //Public methods
    @Override
    public boolean addState(String name) {
        return false;
    }

    @Override
    public boolean setFinal(String name) {
        return false;
    }

    @Override
    public boolean setStart(String name) {
        return false;
    }

    @Override
    public void addSigma(char symbol) {

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


        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return null;
    }

    @Override
    public State getState(String name) {
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return false;
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

        return null;
    }

    @Override
    public int maxCopies(String s) {

        /*
        does similar simulation as accepts,
        only it returns the maximum number of copies a trace can generate. In the case of
        the above example, the method returns 2 since the maximum number of copies in the
        trace is {a, b} and |{a, b}| = 2.
         */

        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        return false;
    }

    @Override
    public boolean isDFA() {

        /*
        determines if an NFA is actually a DFA, i.e., all of its transitions obey the rule’s of DFA transitions.
         */

        return false;
    }
}
