package org.gnubridge.presentation.gui;

import java.util.Random;

import org.gnubridge.core.Direction;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ScoreCalculator;
import org.gnubridge.core.bidding.Vulnerability;

/* This class tracks the score of the human and the computer.
 * It also keeps track of whether or not the human is vulnerable.
 */
public class ScoringTracker {
	private int latestDeclarerScoreChange;
	private int latestDefenderScoreChange;

	private int runningHumanScore;
	private int runningComputerScore;
	private boolean isHumanVulnerable;
	private boolean isComputerVulnerable;

	private final Random randomSeed;

	public ScoringTracker() {
		runningHumanScore = 0;
		runningComputerScore = 0;
		randomSeed = new Random();
		nextRound();
	}

	public void processFinishedGame(int directionOfHuman, Bid highBid, int declarerTricksTaken) {
		boolean humansAreDeclarers = false;
		Vulnerability vulnerability;
		if (directionOfHuman == Direction.NORTH_DEPRECATED || directionOfHuman == Direction.SOUTH_DEPRECATED) {
			humansAreDeclarers = true;
			vulnerability = new Vulnerability(isHumanVulnerable, isComputerVulnerable);
		} else {
			vulnerability = new Vulnerability(isComputerVulnerable, isHumanVulnerable);
		}

		ScoreCalculator calculator = new ScoreCalculator(highBid, declarerTricksTaken, vulnerability);

		latestDeclarerScoreChange = calculator.getDeclarerScore();
		latestDefenderScoreChange = calculator.getDefenderScore();

		if (humansAreDeclarers) {
			runningHumanScore += latestDeclarerScoreChange;
			runningComputerScore += latestDefenderScoreChange;
		} else {
			runningHumanScore += latestDefenderScoreChange;
			runningComputerScore += latestDeclarerScoreChange;
		}
	}

	/* Starts the next, determines the vulnerability and returns it */
	public String nextRound() {

		/* Is the human going to be vulnerable? Like in Duplicate Bridge, 
		 * this is randomly determined.
		 */
		isHumanVulnerable = randomSeed.nextBoolean();
		isComputerVulnerable = randomSeed.nextBoolean();
		return toString();
	}

	/* toString for vulnerability */
	@Override
	public String toString() {
		String str = "";
		if (isHumanVulnerable) {
			str += "Us: Vulnerable, Them: ";
		} else {
			str += "Us: Not Vulnerable, Them: ";
		}
		if (isComputerVulnerable) {
			str += "Vulnerable";
		} else {
			str += "Not Vulnerable";
		}
		return str;
	}

	public int getLatestDeclarerScoreChange() {
		return latestDeclarerScoreChange;
	}

	public int getLatestDefenderScoreChange() {
		return latestDefenderScoreChange;
	}

	public int getRunningHumanScore() {
		return runningHumanScore;
	}

	public int getRunningComputerScore() {
		return runningComputerScore;
	}

	public boolean isHumanVulnerable() {
		return isHumanVulnerable;
	}

	public boolean isComputerVulnerable() {
		return isComputerVulnerable;
	}
}
