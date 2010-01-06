package org.gnubridge.presentation.gui;

import java.util.Random;

import org.gnubridge.core.Direction;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ScoreCalculator;

/* This class tracks the score of the human and the computer.
 * It also keeps track of whether or not the human is vulnerable.
 */
public class ScoringTracker {
	private int latestDeclarerScoreChange;
	private int latestDefenderScoreChange;

	private int runningHumanScore;
	private int runningComputerScore;
	private boolean isVulnerable;

	private final Random randomSeed;

	public ScoringTracker() {
		runningHumanScore = 0;
		runningComputerScore = 0;
		randomSeed = new Random();
		nextRound();
	}

	public void processFinishedGame(int directionOfHuman, Bid highBid, int declarerTricksTaken) {
		boolean humansAreDeclarers = false;
		boolean declarersAreVulnerable = false;
		if (directionOfHuman == Direction.NORTH_DEPRECATED || directionOfHuman == Direction.SOUTH_DEPRECATED) {
			humansAreDeclarers = true;
			declarersAreVulnerable = isVulnerable;
		} else {
			declarersAreVulnerable = !isVulnerable;
		}

		ScoreCalculator calculator = new ScoreCalculator(highBid, declarerTricksTaken, declarersAreVulnerable);

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
	public boolean nextRound() {

		/* Is the human going to be vulnerable? Like in Duplicate Bridge, 
		 * this is randomly determined.
		 */
		isVulnerable = randomSeed.nextBoolean();
		return isVulnerable;
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

	public boolean isVulnerable() {
		return isVulnerable;
	}
}
