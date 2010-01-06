package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;
import static org.gnubridge.core.deck.Trump.*;
import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.presentation.gui.ScoringTracker;

public class ScoreCalculatorTest extends TestCase {
	public void testOneMinorSuitContractMadeWithNoOvertricks() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(ONE_CLUBS, tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(20, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testOneMajorSuitContractWithTwoOvertricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(ONE_SPADES, tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(90, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testGrandSlam() {
		int tricksTakenByDeclarers = 13;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, CLUBS), tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(1000 + 7 * 20, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testSmallSlam() {
		int tricksTakenByDeclarers = 12;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(6, SPADES), tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(500 + 6 * 30, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testNTWithOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(2, NOTRUMP), tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		/* 3 * 30 + 10 makes a 100 point game, but the contract was only 70, so it is not a
		 * 	a "game" - should be 100 points */

		assertEquals(100, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testNTWithoutOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(3, NOTRUMP), tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		/* Now the contract is worth 100, so the 300 game bonus kicks in */
		assertEquals(400, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testUndertricksNT() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(3, NOTRUMP), tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(150, actualDefenderScore);
	}

	public void testUndertricksMajorSuit() {
		int tricksTakenByDeclarers = 2;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, HEARTS), tricksTakenByDeclarers, false);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(550, actualDefenderScore);
	}

	public void testVulnerability() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(2, HEARTS), tricksTakenByDeclarers, true);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(200, actualDefenderScore);
	}

	public void testVulnerabilityWinContract() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(1, HEARTS), tricksTakenByDeclarers, true);
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(30, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testScoreTracker() {
		ScoringTracker tracker = new ScoringTracker();

		int tricksTakenByDeclarers = 6;
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, (new Bid(2, HEARTS)), tricksTakenByDeclarers);

		int actualHumanScore = tracker.getRunningHumanScore();
		int actualComputerScore = tracker.getRunningComputerScore();

		if (tracker.isVulnerable()) {
			assertEquals(0, actualHumanScore);
			assertEquals(200, actualComputerScore);
		} else {
			assertEquals(0, actualHumanScore);
			assertEquals(100, actualComputerScore);
		}

		tracker.nextRound();

		tricksTakenByDeclarers = 8;
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, (new Bid(2, SPADES)), tricksTakenByDeclarers);

		actualHumanScore = tracker.getRunningHumanScore();
		int newActualComputerScore = tracker.getRunningComputerScore();

		assertEquals(60, actualHumanScore);
		assertEquals(actualComputerScore, newActualComputerScore);
	}
}
