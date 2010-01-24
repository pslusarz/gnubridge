package org.gnubridge.presentation.gui;

import org.gnubridge.core.Direction;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.ScoreCalculator;
import org.gnubridge.core.bidding.UsThemVulnerability;
import org.jbridge.presentation.gui.MockScoringTracker;

/* This class tracks the score of the human and the computer.
 * It also keeps track of whether or not the human is vulnerable.
 */
public class ScoringTracker {
	private static MockScoringTracker instance;
	private int latestDeclarerScoreChange;
	private int latestDefenderScoreChange;

	private int runningHumanScore;
	private int runningComputerScore;

	private int directionOfHuman;
	protected UsThemVulnerability usThemVulnerability;

	protected ScoringTracker() {
		runningHumanScore = 0;
		runningComputerScore = 0;
	}

	public void processFinishedGame(int directionOfHuman, Bid highBid, int declarerTricksTaken) {
		this.directionOfHuman = directionOfHuman;

		ScoreCalculator calculator = new ScoreCalculator(highBid, declarerTricksTaken, usThemVulnerability
				.asDeclarerDefender(directionOfHuman));

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

	public void setUsThemVulnerability(UsThemVulnerability v) {
		usThemVulnerability = v;
	}

	/* toString for vulnerability */
	@Override
	public String toString() {
		String str = "";
		if (usThemVulnerability.areWeVulnerable()) {
			str += "Us: Vulnerable, Them: ";
		} else {
			str += "Us: Not Vulnerable, Them: ";
		}
		if (usThemVulnerability.areTheyVulnerable()) {
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

	private boolean isHumanDeclarer() {
		if (directionOfHuman == Direction.NORTH_DEPRECATED || directionOfHuman == Direction.SOUTH_DEPRECATED) {
			return true;
		} else {
			return false;
		}
	}

	public static void setInstance(MockScoringTracker mock) {
		instance = mock;
	}

	public static ScoringTracker getInstance() {
		ScoringTracker result = null;
		if (instance != null) {
			result = instance;
			instance = null;
		} else {
			result = new ScoringTracker();
		}
		return result;

	}
}
