package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Eight;
import org.gnubridge.core.deck.Five;
import org.gnubridge.core.deck.Four;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Jack;
import org.gnubridge.core.deck.King;
import org.gnubridge.core.deck.Nine;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Queen;
import org.gnubridge.core.deck.Seven;
import org.gnubridge.core.deck.Six;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Ten;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Trump;
import org.gnubridge.core.deck.Two;
import org.gnubridge.presentation.GameUtils;

public class DuplicatePruningAcceptanceTests extends TestCase {

	public void testPruningDuplicate13CardsRunOutOfMemory() {
		Game game = new Game(NoTrump.i());
		game.getWest()
				.init(Four.of(Hearts.i()), Ten.of(Diamonds.i()), Eight.of(Hearts.i()), Ace.of(Clubs.i()),
						Seven.of(Diamonds.i()), Ten.of(Hearts.i()), Seven.of(Hearts.i()), Six.of(Clubs.i()),
						Nine.of(Clubs.i()), Seven.of(Clubs.i()), Queen.of(Diamonds.i()), Ace.of(Hearts.i()),
						Two.of(Hearts.i()));
		game.getNorth().init(Ten.of(Clubs.i()), Nine.of(Spades.i()), Six.of(Diamonds.i()), Six.of(Spades.i()),
				Eight.of(Diamonds.i()), Ace.of(Diamonds.i()), Queen.of(Hearts.i()), Five.of(Hearts.i()),
				Queen.of(Spades.i()), Two.of(Clubs.i()), Eight.of(Spades.i()), King.of(Hearts.i()),
				Jack.of(Diamonds.i()));
		game.getEast().init(Five.of(Diamonds.i()), Jack.of(Hearts.i()), King.of(Diamonds.i()), Two.of(Spades.i()),
				Three.of(Diamonds.i()), Three.of(Hearts.i()), Eight.of(Clubs.i()), Nine.of(Hearts.i()),
				Nine.of(Diamonds.i()), King.of(Spades.i()), Three.of(Clubs.i()), Queen.of(Clubs.i()),
				Four.of(Spades.i()));
		game.getSouth().init(Seven.of(Spades.i()), Five.of(Clubs.i()), Five.of(Spades.i()), Two.of(Diamonds.i()),
				Four.of(Clubs.i()), Jack.of(Spades.i()), Six.of(Hearts.i()), Four.of(Diamonds.i()), Ten.of(Spades.i()),
				Ace.of(Spades.i()), King.of(Clubs.i()), Three.of(Spades.i()), Jack.of(Clubs.i()));
		game.setNextToPlay(Direction.WEST);
		DoubleDummySolver pruned2 = new DoubleDummySolver(game);
		pruned2.setUseDuplicateRemoval(true);
		pruned2.setUsePruneLowestCardToLostTrick(true);
		pruned2.setMaxTricks(4);
		pruned2.search();
		pruned2.printStats();
		assertEquals(2, pruned2.getRoot().getTricksTaken(Player.WEST_EAST));
	}

	public void testEquivalenceVariousPruneStrategies() {
		for (int cardDeal = 0; cardDeal < 5; cardDeal++) {
			Trump trump = determineTrump(cardDeal);
			Game g = new Game(trump);
			GameUtils.initializeRandom(g, 6);
			System.out.println("*********** DEAL " + cardDeal + " ***********");
			g.printHandsDebug();
			g.printHands();
			g.playOneTrick();
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

	public void testPruneLowestCardToLostTrickBugDoNotApplyIfTrickTakenByPartner() {
		Game game = new Game(NoTrump.i());
		game.getWest().init(new Hand("", "8,4", "2", ""));//E: AH, W: 4H???
		game.getNorth().init(new Hand("3", "", "5,4", ""));
		game.getEast().init(new Hand("", "A,6,5", "", ""));
		game.getSouth().init(new Hand("", "10", "3", "3"));
		game.setNextToPlay(Direction.EAST);
		DoubleDummySolver search = new DoubleDummySolver(game);
		search.setUseDuplicateRemoval(false);
		search.pruneAlphaBeta = false;
		search.setUsePruneLowestCardToLostTrick(true);
		//search.setUsePruneLowestCardToLostTrick(false);
		search.usePruning(false);
		search.search();
		search.printStats();
		System.out.println(search.getBestMoves());
		search.printOptimalPath();
		assertEquals(3, search.getRoot().getTricksTaken(Player.WEST_EAST));
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
		static final int MAX_TRICKS = 5;
		int type;
		int runCount = 0;
		private int totalTimeMillis = 0;
		private int northSouthTricks = -1;

		private SearchConfiguration(int type) {
			this.type = type;
		}

		public void runSearch(Game g) {
			search = new DoubleDummySolver(g);
			search.setMaxTricks(MAX_TRICKS);
			if (type == -2) {
				search.setUseDuplicateRemoval(false);
				search.pruneAlphaBeta = false;
				search.setUsePruneLowestCardToLostTrick(false);
				search.usePruning(false);

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
