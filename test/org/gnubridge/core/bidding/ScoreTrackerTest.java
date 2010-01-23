package org.gnubridge.core.bidding;

import static org.gnubridge.core.deck.Trump.*;
import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.presentation.gui.ScoringTracker;

public class ScoreTrackerTest extends TestCase {
	public void testScoreTracker() {
		ScoringTracker tracker = new ScoringTracker();

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

		tracker.nextRound();

		tricksTakenByDeclarers = 8;
		tracker.processFinishedGame(Direction.NORTH_DEPRECATED, (new Bid(2, SPADES)), tricksTakenByDeclarers);

		actualHumanScore = tracker.getRunningHumanScore();
		int newActualComputerScore = tracker.getRunningComputerScore();

		assertEquals(110, actualHumanScore);
		assertEquals(actualComputerScore, newActualComputerScore);
	}
}
