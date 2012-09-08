package org.jbridge.presentation.gui;

import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.UsThemVulnerability;
import org.gnubridge.presentation.gui.ScoringTracker;

public class MockScoringTracker extends ScoringTracker {

	public UsThemVulnerability getUsThemVulnerability() {
		return this.usThemVulnerability;
	}

	@Override
	public int getLatestDeclarerScoreChange() {
		return -1;
	}

	@Override
	public int getLatestDefenderScoreChange() {
		return -1;
	}

	@Override
	public int getRunningComputerScore() {
		return -1;
	}

	@Override
	public int getRunningHumanScore() {
		return -1;
	}

	@Override
	public void processFinishedGame(int directionOfHuman, Bid highBid, int declarerTricksTaken) {

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
