package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Trump;
import org.gnubridge.presentation.GameUtils;

public class StochasticDoubleDummySolverAcceptanceTests extends TestCase {
	static final int SEARCH_DEPTH_CUTOFF = 3;
	static final int CARDS_TO_DEAL = 6;
	private static final int DEALS_TO_TRY = 5;

	public void testEquivalenceVariousPruneStrategies() {
		for (int cardDeal = 0; cardDeal < DEALS_TO_TRY; cardDeal++) {
			Trump trump = determineTrump(cardDeal);
			Game g = new Game(trump);
			GameUtils.initializeRandom(g, CARDS_TO_DEAL);
			System.out.println("*********** DEAL " + cardDeal + " ***********");
			g.printHandsDebug();
			g.printHands();
			g.playOneTrick(); //somewhat randomizes who's to move next
			System.out.println("*********** SEARCHES ***********");
			for (SearchConfiguration config : SearchConfiguration.values()) {
				System.out.println("-----" + config + "---------");
				config.runSearch(g);
				System.out.println("-----------------------");
			}
			System.out.println("*********** END SEARCHES ***********");
			printAverageRunTimes();
			assertAllSearchesFindSameNumberOfTricksTaken();
		}

	}

	private void printAverageRunTimes() {
		System.out.println("Average run times (ms)");
		for (SearchConfiguration config : SearchConfiguration.values()) {
			System.out.println("  " + config + ": " + config.getAverageRunningTime());
		}
	}

	private void assertAllSearchesFindSameNumberOfTricksTaken() {
		SearchConfiguration previousConfig = null;
		for (SearchConfiguration config : SearchConfiguration.values()) {
			if (previousConfig != null) {
				assertTrue("search did not determine who took which tricks", config.getNorthSouthTricks() > -1);
				assertEquals("all searches should be equivalent", previousConfig.getNorthSouthTricks(), config
						.getNorthSouthTricks());
			}
			previousConfig = config;
		}
	}

	private Trump determineTrump(int i) {
		if (i % 5 == 0) {
			return NoTrump.i();
		} else if (i % 5 == 1) {
			return Spades.i();
		} else if (i % 5 == 2) {
			return Hearts.i();
		} else if (i % 5 == 3) {
			return Diamonds.i();
		} else {
			return Clubs.i();
		}
	}

	public enum SearchConfiguration {
		MiniMax(-2), NoDuplicatePruning(-1), NoAlphaBetaPruning(0), DuplicatePruning(1), DuplicateWithLowestPruning(2), NoSequencePruning(
				3), NoPlayedSequencePruning(4);
		DoubleDummySolver search;

		int type;
		int runCount = 0;
		private int totalTimeMillis = 0;
		private int northSouthTricks = -1;

		private SearchConfiguration(int type) {
			this.type = type;
		}

		public void runSearch(Game g) {
			search = new DoubleDummySolver(g);
			search.setMaxTricks(SEARCH_DEPTH_CUTOFF);
			if (type == -2) {
				search.setUseDuplicateRemoval(false);
				search.pruneAlphaBeta = false;
				search.setUsePruneLowestCardToLostTrick(false);
				search.usePruning(false);
				search.setShouldPruneCardsInSequence(false);
				search.setShouldPruneCardsInPlayedSequence(false);

			}
			if (type == -1) {
				search.setUseDuplicateRemoval(false);
			}
			if (type == 0) {
				search.pruneAlphaBeta = false;
			}
			if (type == 1) {
				search.setUseDuplicateRemoval(true);
				search.setUsePruneLowestCardToLostTrick(false);

			}
			if (type == 2) {
				search.setUseDuplicateRemoval(true);
				search.setUsePruneLowestCardToLostTrick(true);

			}
			if (type == 3) {
				search.setShouldPruneCardsInSequence(false);
			}
			if (type == 4) {
				search.setShouldPruneCardsInPlayedSequence(false);
			}
			search.search();
			search.printStats();

			runCount++;
			totalTimeMillis += search.getRunningTime();
			this.northSouthTricks = search.getRoot().getTricksTaken(Player.NORTH_SOUTH);
		}

		public double getAverageRunningTime() {
			return totalTimeMillis / runCount;
		}

		public int getNorthSouthTricks() {
			return northSouthTricks;
		}

	}
}
