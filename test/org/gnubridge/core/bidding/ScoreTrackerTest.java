package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;
import static org.gnubridge.core.deck.Trump.*;

import java.util.Random;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.presentation.gui.ScoringTracker;

public class ScoreTrackerTest extends TestCase {
	int tricksToMakeLevel1Contract = 7;
	Vulnerability neither = new Vulnerability(false, false);

	public void testScoreTracker() {
		ScoringTracker tracker = new ScoringTracker();
		tracker.setVulnerability(new Vulnerability(new Random().nextBoolean(), new Random().nextBoolean()));

		int tricksTakenByDeclarers = 6;
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, (new Bid(2, HEARTS)), tricksTakenByDeclarers);

		int actualHumanScore = tracker.getRunningHumanScore();
		int actualComputerScore = tracker.getRunningComputerScore();

		if (tracker.isHumanVulnerable()) {
			assertEquals(0, actualHumanScore);
			assertEquals(200, actualComputerScore);
		} else {
			assertEquals(0, actualHumanScore);
			assertEquals(100, actualComputerScore);
		}

		tracker.setVulnerability(new Vulnerability(new Random().nextBoolean(), new Random().nextBoolean()));

		tricksTakenByDeclarers = 8;
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, (new Bid(2, SPADES)), tricksTakenByDeclarers);

		actualHumanScore = tracker.getRunningHumanScore();
		int newActualComputerScore = tracker.getRunningComputerScore();

		assertEquals(110, actualHumanScore);
		assertEquals(actualComputerScore, newActualComputerScore);
	}

	public void testScoreTrackerScoresSingleDeal() {
		ScoringTracker tracker = new ScoringTracker();
		tracker.setVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(80, tracker.getRunningHumanScore());
		assertEquals(0, tracker.getRunningComputerScore());
	}

	public void testScoreTrackerAddsScoresForHumanInTwoDeals() {
		ScoringTracker tracker = new ScoringTracker();
		tracker.setVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(160, tracker.getRunningHumanScore());
	}

	public void testScoreTrackerDeclarerIsSouthOrNorth() {
		ScoringTracker tracker = new ScoringTracker();
		tracker.setVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		tracker.processFinishedGame(Direction.SOUTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(160, tracker.getRunningHumanScore());
	}

	public void testScoreTrackerTwoDealsHumanIsDeclarerAndThenDefender() {
		ScoringTracker tracker = new ScoringTracker();
		tracker.setVulnerability(neither);
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		tracker.processFinishedGame(Direction.EAST_DEPRECATED, ONE_HEARTS, tricksToMakeLevel1Contract);
		assertEquals(80, tracker.getRunningHumanScore());
		assertEquals(80, tracker.getRunningComputerScore());
	}
}
