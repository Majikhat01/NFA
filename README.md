**************************************************
* Project 2 / Nondeterministic Finite Automata	
* CS361 - Section 2
* 03/27/23
* @Authors: Calvin Hatfield, Jaden Sims
**************************************************


OVERVIEW/HOW THE PROGRAM WORKS:*************************************************************************


We first start out by creating some of the required variables such as states, sigma, and 
the final states. Following this we use a constructor to create the NFA that will be used
throughout the program in order to simulate the NFA and/or DFA tests. Each state is then
checked to see if it exists, and if not, the program will simply do nothing. However,
if an automata does exist, the program will then add the initial start state and begin to
iterate through the states and their transitions while also checking whether any of the 
of the transitions have eclosures. When checking for eclosures, we create a new set and a 
stack with a tracker state. It will then add the first state to the set of states while also
adding it to the stack to keep track of the state. We then reset the flags and pop the 
first state off of the stack, and make sure that all the states were visited. The accepts 
method will check whether the nfa is set up correctly and is in a final state when the nfa
is completed. MaxCopies is essentially the same as the accepts method except it checks whether
the max amount of copies created when simulating the nfa is correct otherwise it will fail. Lastly,
our isDFA method checks to see if the amount of transitions from one state to the next is not greater
than 1 while also checking to make sure that there are no 'e' transitions, otherwise it returns isDFA
as false. All other funtions such as the add and set methods were either provided to us via the project 
files and/or reused from the previous Project 1.


INCLUDED FILES:******************************************************************************************


* NFA.java
* NFATest.java
* README


COMPILING AND RUNNING:***********************************************************************************


 Run the test class with the command:
 $ java NFATest


EXPECTED BEHAVIOR:***************************************************************************************


 When the NFATest.java file is run using our edited NFA.java class, all tests are expected
to be passing with no errors thrown. Each of the different tests are expected to pass within
~5 ms for tests 1_2 through 6_1. Test 1_1 is expected to run in ~8 ms with
all test finishing around ~30ms. Each DFA and NFA should be iterated through correctly with 
each state simulated and tested efficiently.


 
 