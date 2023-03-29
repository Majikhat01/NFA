package test.nfa;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import fa.nfa.NFA;

public class NFATest {

	private NFA nfa1() {
		long elapsedTime1;
		long elapsedTime2;
		long elapsedTime3;
		long elapsedTime4;
		long elapsedTime5;
		long elapsedTime6;
		long elapsedTime7;

		long startTime = System.nanoTime();
		NFA nfa = new NFA();
		elapsedTime1 = System.nanoTime() - startTime;


		nfa.addSigma('0');
		nfa.addSigma('1');
		elapsedTime2 = System.nanoTime() - startTime;


		assertTrue(nfa.addState("a"));
		assertTrue(nfa.setStart("a"));
		elapsedTime3 = System.nanoTime() - startTime;


		assertTrue(nfa.addState("b"));
		assertTrue(nfa.setFinal("b"));
		elapsedTime4 = System.nanoTime() - startTime;


		assertFalse(nfa.addState("a"));
		assertFalse(nfa.setStart("c"));
		assertFalse(nfa.setFinal("d"));
		elapsedTime5 = System.nanoTime() - startTime;



		assertTrue(nfa.addTransition("a", Set.of("a"), '0'));
		assertTrue(nfa.addTransition("a", Set.of("b"), '1'));
		assertTrue(nfa.addTransition("b", Set.of("a"), 'e'));
		elapsedTime6 = System.nanoTime() - startTime;

		assertFalse(nfa.addTransition("c", Set.of("a"), '0'));
		assertFalse(nfa.addTransition("a", Set.of("b"), '3'));
		assertFalse(nfa.addTransition("b", Set.of("d","c"), 'e'));
		elapsedTime7 = System.nanoTime() - startTime;

		elapsedTime7 = elapsedTime7 - elapsedTime6;
		elapsedTime6 = elapsedTime6 - elapsedTime5;
		elapsedTime5 = elapsedTime5 - elapsedTime4;
		elapsedTime4 = elapsedTime4 - elapsedTime3;
		elapsedTime3 = elapsedTime3 - elapsedTime2;
		elapsedTime2 = elapsedTime2 - elapsedTime1;

		System.out.println(elapsedTime1);
		System.out.println(elapsedTime2);
		System.out.println(elapsedTime3);
		System.out.println(elapsedTime4);
		System.out.println(elapsedTime5);
		System.out.println(elapsedTime6);
		System.out.println(elapsedTime7);

		return nfa;

	}

	@Test
	public void test1_1() {
		NFA nfa = nfa1();
		System.out.println("nfa1 instantiation done");
	}

	@Test
	public void test1_2() {
		NFA nfa = nfa1();
		assertNotNull(nfa.getState("a"));
		assertEquals(nfa.getState("a").getName(), "a");
		//ensures the same object
		assertEquals(nfa.getState("a"), nfa.getState("a"));
		assertTrue(nfa.isStart("a"));
		assertTrue(nfa.isFinal("b"));


		System.out.println("nfa1 correctness done");
	}

	@Test
	public void test1_3() {
		NFA nfa = nfa1();
		assertFalse(nfa.isDFA());
		System.out.println("nfa1 isDFA done");
	}

	@Test
	public void test1_4() {
		NFA nfa = nfa1();
		assertEquals(nfa.eClosure(nfa.getState("a")), Set.of(nfa.getState("a")));
		assertEquals(nfa.eClosure(nfa.getState("b")), Set.of(nfa.getState("a"), nfa.getState("b")));
		System.out.println("nfa1 eClosure done");
	}

	@Test
	public void test1_5() {
		NFA nfa = nfa1();
		assertFalse(nfa.accepts("0"));
		assertTrue(nfa.accepts("1"));
		assertFalse(nfa.accepts("00"));
		assertTrue(nfa.accepts("101"));
		assertFalse(nfa.accepts("e"));
		System.out.println("nfa1 accepts done");
	}

	@Test
	public void test1_6() {
		NFA nfa = nfa1();
		assertEquals(nfa.maxCopies("0"), 1);
		assertEquals(nfa.maxCopies("1"), 2);
		assertEquals(nfa.maxCopies("00"), 1);
		assertEquals(nfa.maxCopies("101"), 2);
		assertEquals(nfa.maxCopies("e"), 1);
		assertEquals(nfa.maxCopies("2"), 1);
		System.out.println("nfa1 maxCopies done");
	}

	private NFA nfa2() {
		NFA nfa = new NFA();

		nfa.addSigma('0');
		nfa.addSigma('1');

		assertTrue(nfa.addState("q0"));
		assertTrue(nfa.setStart("q0"));
		assertTrue(nfa.addState("q1"));
		assertTrue(nfa.addState("q2"));
		assertTrue(nfa.addState("q3"));
		assertTrue(nfa.addState("q4"));
		assertTrue(nfa.setFinal("q3"));

		assertFalse(nfa.addState("q3"));
		assertFalse(nfa.setStart("q5"));
		assertFalse(nfa.setFinal("q6"));


		assertTrue(nfa.addTransition("q0", Set.of("q0"), '0'));
		assertTrue(nfa.addTransition("q0", Set.of("q0"), '1'));
		assertTrue(nfa.addTransition("q0", Set.of("q1"), '1'));
		assertTrue(nfa.addTransition("q1", Set.of("q2"), 'e'));
		assertTrue(nfa.addTransition("q2", Set.of("q4"), '0'));
		assertTrue(nfa.addTransition("q2", Set.of("q2","q3"), '1'));
		assertTrue(nfa.addTransition("q4", Set.of("q1"), '0'));

		assertFalse(nfa.addTransition("q0", Set.of("q5"), '0'));
		assertFalse(nfa.addTransition("q0", Set.of("q3"), '3'));
		assertFalse(nfa.addTransition("q5", Set.of("q0","q2"), 'e'));



		return nfa;

	}

	@Test
	public void test2_1() {
		NFA nfa = nfa2();
		System.out.println("nfa1 instantiation done");
	}

	@Test
	public void test2_2() {
		NFA nfa = nfa2();
		assertNotNull(nfa.getState("q0"));
		assertEquals(nfa.getState("q3").getName(), "q3");
		assertNull(nfa.getState("q5"));
		//ensures the same object
		assertEquals(nfa.getState("q2"), nfa.getState("q2"));
		assertTrue(nfa.isStart("q0"));
		assertFalse(nfa.isStart("q3"));
		assertTrue(nfa.isFinal("q3"));
		assertFalse(nfa.isFinal("q6"));

		System.out.println("nfa1 correctness done");
	}

	@Test
	public void test2_3() {
		NFA nfa = nfa2();
		assertFalse(nfa.isDFA());
		System.out.println("nfa1 isDFA done");
	}

	@Test
	public void test2_4() {
		NFA nfa = nfa2();
		assertEquals(nfa.eClosure(nfa.getState("q0")), Set.of(nfa.getState("q0")));
		assertEquals(nfa.eClosure(nfa.getState("q1")), Set.of(nfa.getState("q1"),nfa.getState("q2")));
		assertEquals(nfa.eClosure(nfa.getState("q3")), Set.of(nfa.getState("q3")));
		assertEquals(nfa.eClosure(nfa.getState("q4")), Set.of(nfa.getState("q4")));

		System.out.println("nfa1 eClosure done");
	}

	@Test
	public void test2_5() {
		NFA nfa = nfa2();
		assertTrue(nfa.accepts("1111"));
		assertFalse(nfa.accepts("e"));
		assertFalse(nfa.accepts("0001100"));
		assertTrue(nfa.accepts("010011"));
		assertFalse(nfa.accepts("0101"));
		System.out.println("nfa1 accepts done");
	}

	@Test
	public void test2_6() {
		NFA nfa = nfa2();
		assertEquals(nfa.maxCopies("1111"), 4);
		assertEquals(nfa.maxCopies("e"), 1);
		assertEquals(nfa.maxCopies("0001100"), 4);
		assertEquals(nfa.maxCopies("010011"), 4);
		assertEquals(nfa.maxCopies("0101"), 3);

		System.out.println("nfa1 maxCopies done");
	}

	private NFA nfa3() {
		NFA nfa = new NFA();

		nfa.addSigma('#');
		nfa.addSigma('0');
		nfa.addSigma('1');

		assertTrue(nfa.addState("W"));
		assertTrue(nfa.setStart("W"));
		assertTrue(nfa.addState("L"));
		assertTrue(nfa.addState("I"));
		assertTrue(nfa.addState("N"));
		assertTrue(nfa.setFinal("N"));

		assertFalse(nfa.addState("N"));
		assertFalse(nfa.setStart("Z"));
		assertFalse(nfa.setFinal("Y"));

		assertTrue(nfa.addTransition("W", Set.of("N"), '#'));
		assertTrue(nfa.addTransition("W", Set.of("L"), 'e'));

		assertTrue(nfa.addTransition("L", Set.of("L","N"), '0'));
		assertTrue(nfa.addTransition("L", Set.of("I"), 'e'));

		assertTrue(nfa.addTransition("I", Set.of("I"), '1'));
		assertTrue(nfa.addTransition("I", Set.of("N"), '1'));

		assertTrue(nfa.addTransition("N", Set.of("W"), '#'));

		assertFalse(nfa.addTransition("W", Set.of("K"), '0'));
		assertFalse(nfa.addTransition("W", Set.of("W"), '3'));
		assertFalse(nfa.addTransition("ZZ", Set.of("W","Z"), 'e'));


		return nfa;

	}

	@Test
	public void test3_1() {
		NFA nfa = nfa3();
		System.out.println("nfa1 instantiation done");
	}

	@Test
	public void test3_2() {
		NFA nfa = nfa3();
		assertNotNull(nfa.getState("W"));
		assertEquals(nfa.getState("N").getName(), "N");
		assertNull(nfa.getState("Z0"));
		//commented out test below per instructor's request in piazza
		//assertEquals(nfa.getState("I").toStates('1'), Set.of(nfa.getState("I"), nfa.getState("N")));
		assertTrue(nfa.isStart("W"));
		assertFalse(nfa.isStart("L"));
		assertTrue(nfa.isFinal("N"));
		assertFalse(nfa.isFinal("I"));
		System.out.println("nfa1 correctness done");
	}

	@Test
	public void test3_3() {
		NFA nfa = nfa3();
		assertFalse(nfa.isDFA());
		System.out.println("nfa1 isDFA done");
	}

	@Test
	public void test3_4() {
		NFA nfa = nfa3();
		assertEquals(nfa.eClosure(nfa.getState("W")), Set.of(nfa.getState("W"),nfa.getState("L"),nfa.getState("I")));
		assertEquals(nfa.eClosure(nfa.getState("N")), Set.of(nfa.getState("N")));
		assertEquals(nfa.eClosure(nfa.getState("L")), Set.of(nfa.getState("L"),nfa.getState("I")));
		assertEquals(nfa.eClosure(nfa.getState("I")), Set.of(nfa.getState("I")));

		System.out.println("nfa1 eClosure done");
	}

	@Test
	public void test3_5() {
		NFA nfa = nfa3();
		assertTrue(nfa.accepts("###"));
		assertTrue(nfa.accepts("111#00"));
		assertTrue(nfa.accepts("01#11##"));
		assertFalse(nfa.accepts("#01000###"));
		assertFalse(nfa.accepts("011#00010#"));
		System.out.println("nfa1 accepts done");
	}

	@Test
	public void test3_6() {
		NFA nfa = nfa3();
		assertEquals(nfa.maxCopies("###"), 3);
		assertEquals(nfa.maxCopies("e"), 3);
		assertEquals(nfa.maxCopies("011#00010#"), 3);
		assertEquals(nfa.maxCopies("23"), 3);
		assertEquals(nfa.maxCopies("011#00010#"), 3);
		System.out.println("nfa1 maxCopies done");
	}

	public NFA nfa4() {
		NFA nfa = new NFA();

		nfa.addSigma('a');
		nfa.addSigma('b');
		nfa.addSigma('c');

		assertTrue(nfa.addState("q0"));
		assertTrue(nfa.setStart("q0"));
		assertTrue(nfa.addState("q1"));
		assertTrue(nfa.addState("q2"));
		assertTrue(nfa.addState("q3"));
		assertTrue(nfa.addState("q4"));
		assertTrue(nfa.setFinal("q4"));
		assertTrue(nfa.addState("q5"));
		assertTrue(nfa.setFinal("q5"));

		assertFalse(nfa.addState("q0"));
		assertFalse(nfa.setStart("q7"));
		assertFalse(nfa.setFinal("q8"));

		assertTrue(nfa.addTransition("q0", Set.of("q1"), 'e'));
		assertTrue(nfa.addTransition("q0", Set.of("q2"), 'a'));

		assertTrue(nfa.addTransition("q1", Set.of("q1"), 'c'));
		assertTrue(nfa.addTransition("q1", Set.of("q3"), 'a'));

		assertTrue(nfa.addTransition("q2", Set.of("q1"), 'c'));
		assertTrue(nfa.addTransition("q2", Set.of("q2"), 'b'));
		assertTrue(nfa.addTransition("q2", Set.of("q3"), 'e'));

		assertTrue(nfa.addTransition("q3", Set.of("q3"), 'a'));
		assertTrue(nfa.addTransition("q3", Set.of("q4"), 'b'));
		assertTrue(nfa.addTransition("q3", Set.of("q4"), 'c'));

		assertTrue(nfa.addTransition("q4", Set.of("q5"), 'b'));
		assertTrue(nfa.addTransition("q4", Set.of("q5"), 'c'));

		assertFalse(nfa.addTransition("q0", Set.of("q8"), 'a'));
		assertFalse(nfa.addTransition("q3", Set.of("q3"), 'd'));
		assertFalse(nfa.addTransition("ZZ", Set.of("W","Z"), 'e'));

		return nfa;
	}

	@Test
	public void test4_1() {
		NFA nfa = nfa4();
		assertTrue(nfa.accepts("caacb"));
		assertTrue(nfa.accepts("abbccaab"));
		assertTrue(nfa.accepts("ccaaacc"));
		assertFalse(nfa.accepts("caacbb"));
		assertFalse(nfa.accepts("accabcc"));
		System.out.println("nfa4 accepts done");
	}

	@Test
	public void test4_2() {
		NFA nfa = nfa4();
		assertFalse(nfa.isDFA());
		System.out.println("nfa4 isDFA done");
	}

	public NFA nfa5() {
		NFA nfa = new NFA();

		nfa.addSigma('a');
		nfa.addSigma('b');

		assertTrue(nfa.addState("q0"));
		assertTrue(nfa.setStart("q0"));
		assertTrue(nfa.addState("q1"));
		assertTrue(nfa.addState("q2"));
		assertTrue(nfa.setFinal("q2"));
		assertTrue(nfa.addState("q3"));
		assertTrue(nfa.addState("q4"));
		assertTrue(nfa.setFinal("q4"));

		assertTrue(nfa.addTransition("q0", Set.of("q1"), 'a'));
		assertTrue(nfa.addTransition("q1", Set.of("q2"), 'a'));
		assertTrue(nfa.addTransition("q2", Set.of("q2"), 'b'));

		assertTrue(nfa.addTransition("q1", Set.of("q3"), 'b'));
		assertTrue(nfa.addTransition("q3", Set.of("q4"), 'b'));
		assertTrue(nfa.addTransition("q4", Set.of("q4"), 'a'));

		return nfa;
	}
	@Test
	public void test5_1() {
	NFA nfa = nfa5();
	assertTrue(nfa.isDFA());
	System.out.println("nfa5 isDFA done");
	}

	public NFA nfa6() {
		NFA nfa = new NFA();

		nfa.addSigma('a');
		nfa.addSigma('b');

		assertTrue(nfa.addState("q0"));
		assertTrue(nfa.setStart("q0"));
		assertTrue(nfa.addState("q1"));
		assertTrue(nfa.addState("q2"));
		assertTrue(nfa.addState("q3"));
		assertTrue(nfa.addState("q4"));
		assertTrue(nfa.addState("q5"));
		assertTrue(nfa.setFinal("q5"));

		assertTrue(nfa.addTransition("q0", Set.of("q1"), 'a'));

		assertTrue(nfa.addTransition("q1", Set.of("q2"), 'a'));
		assertTrue(nfa.addTransition("q1", Set.of("q3"), 'b'));

		assertTrue(nfa.addTransition("q3", Set.of("q2"), 'a'));
		assertTrue(nfa.addTransition("q2", Set.of("q4"), 'b'));

		assertTrue(nfa.addTransition("q4", Set.of("q5"), 'a'));
		assertTrue(nfa.addTransition("q5", Set.of("q5"), 'b'));

		return nfa;
	}

	@Test
	public void test6_1() {
	NFA nfa = nfa6();
	assertTrue(nfa.isDFA());
	System.out.println("nfa6 isDFA done");
	}
}
