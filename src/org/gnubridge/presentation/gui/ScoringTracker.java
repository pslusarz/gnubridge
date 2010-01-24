package org.gnubridge.presentation.gui;

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

	private Vulnerability vulnerability;
	private int directionOfHuman;

	public ScoringTracker() {
		runningHumanScore = 0;
		runningComputerScore = 0;
	}

	public void processFinishedGame(int directionOfHuman, Bid highBid, int declarerTricksTaken) {
		this.directionOfHuman = directionOfHuman;

		ScoreCalculator calculator = new ScoreCalculator(highBid, declarerTricksTaken, vulnerability);

		latestDeclarerScoreChange = calculator.getDeclarerScore();
		latestDefenderScoreChange = calculator.getDefenderScore();

		if (isHumanDeclarer()) {
			runningHumanScore += latestDeclarerScoreChange;
			runningComputerScore += latestDefenderScoreChange;
		} else {
			runningHumanScore += latestDefenderScoreChange;
			runningComputerScore += latestDeclarerScoreChange;
		}
	}

	public void setVulnerability(Vulnerability v) {
		vulnerability = v;
	}

	/* toString for vulnerability */
	@Override
	public String toString() {
		String str = "";
		if (isHumanVulnerable()) {
			str += "Us: Vulnerable, Them: ";
		} else {
			str += "Us: Not Vulnerable, Them: ";
		}
		if (isComputerVulnerable()) {
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
		if (isHumanDeclarer()) {
			return vulnerability.isDeclarerVulnerable();
		} else {

			return vulnerability.isDeclarerVulnerable();
		}
	}

	private boolean isHumanDeclarer() {
		if (directionOfHuman == Direction.NORTH_DEPRECATED || directionOfHuman == Direction.SOUTH_DEPRECATED) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isComputerVulnerable() {
		if (isComputerDeclarer()) {
			return vulnerability.isDeclarerVulnerable();
		} else {
			return vulnerability.isDeclarerVulnerable();
		}
	}

	private boolean isComputerDeclarer() {
		return !isHumanVulnerable();
	}
}
