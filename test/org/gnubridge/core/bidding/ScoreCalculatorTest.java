package org.gnubridge.core.bidding;

import junit.framework.TestCase;
import static org.gnubridge.core.bidding.Bid.ONE_CLUBS;
import static org.gnubridge.core.bidding.Bid.ONE_SPADES;
import static org.gnubridge.core.deck.Trump.CLUBS;
import static org.gnubridge.core.deck.Trump.SPADES;
import static org.gnubridge.core.deck.Trump.HEARTS;
import static org.gnubridge.core.deck.Trump.NOTRUMP;

public class ScoreCalculatorTest extends TestCase {
	public void testOneMinorSuitContractMadeWithNoOvertricks() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(
				ONE_CLUBS, tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(20, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}
	
	public void testOneMajorSuitContractWithTwoOvertricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(
				ONE_SPADES, tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(90, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}
	
	public void testGrandSlam() {
		int tricksTakenByDeclarers = 13;
		ScoreCalculator calculator = new ScoreCalculator(
				new Bid(7, CLUBS), tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(1000 + 7 * 20, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}
	
	public void testSmallSlam() {
		int tricksTakenByDeclarers = 12;
		ScoreCalculator calculator = new ScoreCalculator(
				new Bid(6, SPADES), tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(500 + 6 * 30, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
		assertEquals(0, actualDefenderScore);
	}
	
	public void testNTWithOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(
				new Bid(2, NOTRUMP), tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		/* 3 * 30 + 10 makes a 100 point game, but the contract was only 70, so it is not a
		 * 	a "game" - should be 100 points */
		
		assertEquals(100, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}
	
	public void testNTWithoutOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(
				new Bid(3, NOTRUMP), tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
	
		/* Now the contract is worth 100, so the 300 game bonus kicks in */
		assertEquals(400, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}
	
	public void testUndertricksNT() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(
				new Bid(3, NOTRUMP), tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
	
		assertEquals(0, actualDeclarerScore);
		assertEquals(150, actualDefenderScore);
	}

	public void testUndertricksMajorSuit() {
		int tricksTakenByDeclarers = 2;
		ScoreCalculator calculator = new ScoreCalculator(
				new Bid(7, HEARTS), tricksTakenByDeclarers);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
	
		assertEquals(0, actualDeclarerScore);
		assertEquals(550, actualDefenderScore);
	}

}
