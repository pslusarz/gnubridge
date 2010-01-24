package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;
import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.presentation.gui.ScoringTracker;

public class ScoringTrackerTest extends TestCase {
	int tricksToMakeLevel1Contract = 7;
	UsThemVulnerability neither = new UsThemVulnerability(false, false);
	ScoringTracker tracker;

	@Override
	public void setUp() {
		tracker = ScoringTracker.getInstance();
	}

	public void testScoreTrackerScoresSingleDeal() {
		tracker.setUsThemVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(80, tracker.getRunningHumanScore());
		assertEquals(0, tracker.getRunningComputerScore());
	}

	public void testScoreTrackerAddsScoresForHumanInTwoDeals() {
		tracker.setUsThemVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(160, tracker.getRunningHumanScore());
	}

	public void testScoreTrackerAddsScoresForDefenderInTwoDeals() {
		tracker.setUsThemVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract - 1);
		assertEquals(50, tracker.getRunningComputerScore());
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract - 1);
		assertEquals(100, tracker.getRunningComputerScore());
	}

	public void testScoreTrackerDeclarerIsSouthOrNorth() {
		tracker.setUsThemVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		tracker.processFinishedGame(Direction.SOUTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(160, tracker.getRunningHumanScore());
	}

	public void testScoreTrackerTwoDealsHumanIsDeclarerAndThenDefender() {
		tracker.setUsThemVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		tracker.processFinishedGame(Direction.EAST_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(80, tracker.getRunningHumanScore());
		assertEquals(80, tracker.getRunningComputerScore());
	}

}
