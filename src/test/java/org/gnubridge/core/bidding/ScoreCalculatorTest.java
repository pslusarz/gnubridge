package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;

import junit.framework.TestCase;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;

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
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, Clubs.i()), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(1000 + 7 * 20, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testSmallSlam() {
		int tricksTakenByDeclarers = 12;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(6, Spades.i()), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();
		assertEquals(500 + 6 * 30, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testNTWithOverTricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(2, NoTrump.i()), tricksTakenByDeclarers,
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
		ScoreCalculator calculator = new ScoreCalculator(new Bid(3, NoTrump.i()), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		/* Now the contract is worth 100, so the 300 game bonus kicks in */
		assertEquals(400, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testUndertricksNT() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(3, NoTrump.i()), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(150, actualDefenderScore);
	}

	public void testUndertricksMajorSuit() {
		int tricksTakenByDeclarers = 2;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, Hearts.i()), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(550, actualDefenderScore);
	}

	public void testUndertricksMinorSuit() {
		int tricksTakenByDeclarers = 12;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(7, Clubs.i()), tricksTakenByDeclarers, new Vulnerability(
				false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(50, actualDefenderScore);
	}

	public void testVulnerability() {
		int tricksTakenByDeclarers = 6;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(2, Hearts.i()), tricksTakenByDeclarers, new Vulnerability(
				true, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(200, actualDefenderScore);
	}

	public void testVulnerabilityWinContract() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(1, Hearts.i()), tricksTakenByDeclarers, new Vulnerability(
				true, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(80, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testDoubledContractMet() {
		int tricksTakenByDeclarers = 7;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(1, Hearts.i()).makeDoubled(), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(110, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testDoubledContractOvertricks() {
		int tricksTakenByDeclarers = 9;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(1, Hearts.i()).makeDoubled(), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(110 + 100 * 2, actualDeclarerScore);
		assertEquals(0, actualDefenderScore);
	}

	public void testDoubledContractUndertricks() {
		int tricksTakenByDeclarers = 5;
		ScoreCalculator calculator = new ScoreCalculator(new Bid(1, Hearts.i()).makeDoubled(), tricksTakenByDeclarers,
				new Vulnerability(false, false));
		int actualDeclarerScore = calculator.getDeclarerScore();
		int actualDefenderScore = calculator.getDefenderScore();

		assertEquals(0, actualDeclarerScore);
		assertEquals(100 + 200, actualDefenderScore);
	}

	public void testRaw() {
		assertEquals(0, ScoreCalculator.calculateRawScore(PASS));
		assertEquals(20, ScoreCalculator.calculateRawScore(ONE_CLUBS));
		assertEquals(40, ScoreCalculator.calculateRawScore(TWO_DIAMONDS));
		assertEquals(100, ScoreCalculator.calculateRawScore(THREE_NOTRUMP));
		assertEquals(120, ScoreCalculator.calculateRawScore(FOUR_HEARTS));
		assertEquals(150, ScoreCalculator.calculateRawScore(FIVE_SPADES));
	}

}
