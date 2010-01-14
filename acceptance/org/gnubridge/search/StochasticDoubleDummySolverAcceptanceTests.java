package org.gnubridge.search;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.gnubridge.core.Card;
import org.gnubridge.core.Deal;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Trump;
import org.gnubridge.presentation.GameUtils;

public class StochasticDoubleDummySolverAcceptanceTests extends TestCase {
	static final int SEARCH_DEPTH_CUTOFF = 13;
	static final int CARDS_TO_DEAL = 4;
	private static final int DEALS_TO_TRY = 25;
	private Deal g;
	List<SearchMonkey> monkeys;

	public void testEquivaltenceVariousPruneStrategies() {
		for (int cardDeal = 0; cardDeal < DEALS_TO_TRY; cardDeal++) {
			monkeys = new ArrayList<SearchMonkey>();
			Trump trump = determineTrump(cardDeal);
			g = new Deal(trump);
			GameUtils.initializeRandom(g, CARDS_TO_DEAL);
			System.out.println("*********** DEAL " + cardDeal + " ***********");
			g.playOneTrick(); //somewhat randomizes who's to move next
			g.printHands();
			g.printHandsDebug();
			//System.out.println("*********** SEARCHES ***********");
			for (SearchConfiguration config : SearchConfiguration.values()) {
				SearchMonkey monkey = new SearchMonkey(config);
				monkeys.add(monkey);
				//System.out.println("-----" + config + "---------");
				monkey.runSearch(g.duplicate());
				//System.out.println("-----------------------");
			}
			//System.out.println("*********** END SEARCHES ***********");
			assertAllSearchesFindSameNumberOfTricksTaken();
		}

	}

	private void assertAllSearchesFindSameNumberOfTricksTaken() {
		SearchMonkey previousMonkey = null;
		for (SearchMonkey currentMonkey : monkeys) {
			if (previousMonkey != null) {
				System.out
						.println("**** Now checking equivalence: " + previousMonkey + " and " + currentMonkey + "***");
				assertTrue("search did not determine who took which tricks", currentMonkey.getNorthSouthTricks() > -1);
				assertEquals("all searches should be equivalent (" + previousMonkey + " / " + currentMonkey + ")",
						previousMonkey.getNorthSouthTricks(), currentMonkey.getNorthSouthTricks());
				if (!previousMonkey.getBestMove().equals(currentMonkey.getBestMove())) {
					compareEachOthersBestMoves(previousMonkey, currentMonkey, g.duplicate());
				}
				//System.out.println("**** Equivalence achieved: " + previousMonkey + " and " + currentMonkey + "***");
			}
			previousMonkey = currentMonkey;
		}
	}

	private void compareEachOthersBestMoves(SearchMonkey first, SearchMonkey second, Deal originalGame) {
		Card firstMove = first.getBestMove();
		Card secondMove = second.getBestMove();
		System.out.println("#####>  Search strategies " + first + " and " + second
				+ " differ on what is the best move ( " + firstMove + " versus " + secondMove + "). "
				+ "Now comparing tricks taken if each evaluates the other's best move.");

		Deal firstMovePlayed = originalGame.duplicate();
		firstMovePlayed.play(firstMove);
		firstMovePlayed.printHandsDebug();
		SearchMonkey firstProxy = new SearchMonkey(first.config);
		SearchMonkey secondProxy = new SearchMonkey(second.config);
		firstProxy.runSearch(firstMovePlayed.duplicate());
		secondProxy.runSearch(firstMovePlayed.duplicate());
		assertEquals("played " + firstMove + " as recommended by " + firstProxy, firstProxy.getNorthSouthTricks(),
				secondProxy.getNorthSouthTricks());
		int tricksTakenIfFirstMovePlayed = firstProxy.getNorthSouthTricks();

		Deal secondMovePlayed = originalGame.duplicate();
		secondMovePlayed.play(secondMove);
		firstProxy.runSearch(secondMovePlayed);
		secondProxy.runSearch(secondMovePlayed);
		assertEquals("played " + firstMove + " as recommended by " + secondProxy, firstProxy.getNorthSouthTricks(),
				secondProxy.getNorthSouthTricks());
		int tricksTakenIfSecondMovePlayed = firstProxy.getNorthSouthTricks();
		assertEquals(first + " recommended " + firstMove + ", " + second + " recommended " + secondMove,
				tricksTakenIfFirstMovePlayed, tricksTakenIfSecondMovePlayed);
		System.out.println("#####> No worry, evaluated to the same value, moves must be equivalent.");

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
		MiniMax, NoDuplicatePruning, NoAlphaBetaPruning, DuplicatePruning, DuplicateWithLowestPruning, NoSequencePruning, NoPlayedSequencePruning;
	}

	class SearchMonkey {
		DoubleDummySolver search;

		private int northSouthTricks = -1;

		private Card card;

		private final SearchConfiguration config;

		private SearchMonkey(SearchConfiguration config) {
			this.config = config;
		}

		@Override
		public String toString() {
			return config.name();
		}

		public void runSearch(Deal g) {
			search = new DoubleDummySolver(g);
			search.setMaxTricks(SEARCH_DEPTH_CUTOFF);
			search.setTerminateIfRootOnlyHasOneValidMove(false);
			if (config == SearchConfiguration.MiniMax) {
				search.setUseDuplicateRemoval(false);
				search.useAlphaBetaPruning(false);
				search.setShouldPruneCardsInSequence(false);
				search.setShouldPruneCardsInPlayedSequence(false);

			}
			if (config == SearchConfiguration.NoDuplicatePruning) {
				search.setUseDuplicateRemoval(false);
			}
			if (config == SearchConfiguration.NoAlphaBetaPruning) {
				search.useAlphaBetaPruning(false);
			}
			if (config == SearchConfiguration.DuplicatePruning) {
				search.setUseDuplicateRemoval(true);

			}
			if (config == SearchConfiguration.NoSequencePruning) {
				search.setShouldPruneCardsInSequence(false);
			}
			if (config == SearchConfiguration.NoPlayedSequencePruning) {
				search.setShouldPruneCardsInPlayedSequence(false);
			}
			search.search();
			//search.printStats();

			this.card = search.getRoot().getBestMove().getCardPlayed();
			this.northSouthTricks = search.getRoot().getTricksTaken(Player.NORTH_SOUTH);
		}

		public int getNorthSouthTricks() {
			return northSouthTricks;
		}

		public Card getBestMove() {
			return card;
		}

	}
}
