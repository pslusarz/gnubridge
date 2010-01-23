package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;
import static org.gnubridge.core.deck.Trump.*;
import junit.framework.TestCase;

public class ScoreCalculatorTest extends TestCase {
	public void testOneMinorSuitContractMadeWithNoOvertricks() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(ONE_CLUBS, tricksTakenByDeclarers, new Vulnerability(false,
				false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(70, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testOneMajorSuitContractWithTwoOvertricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(ONE_SPADES, tricksTakenByDeclarers, new Vulnerability(false,
				false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(140, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testGrandSlam() {
		int tricksTakenByDeclarers = 13;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, CLUBS), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(1000 + 7 * 20, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testSmallSlam() {
		int tricksTakenByDeclarers = 12;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(6, SPADES), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(500 + 6 * 30, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testNTWithOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(2, NOTRUMP), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		/* 3 * 30 + 10 makes a 100 point game, but the contract was only 70, so it is not a
		 * 	a "game" - should be 100 points + 50 for a partscore*/

		assertEquals(150, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testNTWithoutOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(3, NOTRUMP), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		/* Now the contract is worth 100, so the 300 game bonus kicks in */
		assertEquals(400, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testUndertricksNT() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(3, NOTRUMP), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(150, actualDefenderScore);
	}

	public void testUndertricksMajorSuit() {
		int tricksTakenByDeclarers = 2;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, HEARTS), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(550, actualDefenderScore);
	}

	public void testUndertricksMinorSuit() {
		int tricksTakenByDeclarers = 12;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, CLUBS), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(50, actualDefenderScore);
	}

	public void testVulnerability() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(2, HEARTS), tricksTakenByDeclarers, new Vulnerability(
				true, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(200, actualDefenderScore);
	}

	public void testVulnerabilityWinContract() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(1, HEARTS), tricksTakenByDeclarers, new Vulnerability(
				true, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(80, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testDoubledContractMet() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(new Double(1, HEARTS), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(110, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testDoubledContractOvertricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Double(1, HEARTS), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(110 + 100 * 2, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testDoubledContractUndertricks() {
		int tricksTakenByDeclarers = 5;
		ScoreCalculator calculator = new ScoreCalculator(new Double(1, HEARTS), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(100 + 200, actualDefenderScore);
	}

}
