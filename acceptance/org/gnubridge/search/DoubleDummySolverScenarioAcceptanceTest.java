package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.Deal;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
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
import org.gnubridge.core.deck.Two;

public class DoubleDummySolverScenarioAcceptanceTest extends TestCase {
	public void test13cards3deep() {
		Deal game = new Deal(NoTrump.i());
		game.getPlayer(West.i()).init(King.of(Clubs.i()), King.of(Hearts.i()), Two.of(Diamonds.i()),
				Seven.of(Clubs.i()), Jack.of(Diamonds.i()), Eight.of(Clubs.i()), Four.of(Diamonds.i()),
				Ten.of(Hearts.i()), Three.of(Clubs.i()), Ten.of(Spades.i()), Eight.of(Hearts.i()), Five.of(Spades.i()),
				Ten.of(Diamonds.i()));
		game.getPlayer(North.i()).init(Four.of(Spades.i()), Ace.of(Clubs.i()), Five.of(Hearts.i()),
				Four.of(Hearts.i()), Nine.of(Spades.i()), Two.of(Spades.i()), Two.of(Hearts.i()), Nine.of(Clubs.i()),
				Jack.of(Clubs.i()), Nine.of(Diamonds.i()), Jack.of(Hearts.i()), Nine.of(Hearts.i()),
				Three.of(Spades.i()));
		game.getPlayer(East.i())
				.init(Two.of(Clubs.i()), Five.of(Clubs.i()), Queen.of(Clubs.i()), Three.of(Hearts.i()),
						Seven.of(Spades.i()), Seven.of(Hearts.i()), Seven.of(Diamonds.i()), Six.of(Clubs.i()),
						Eight.of(Spades.i()), Six.of(Hearts.i()), Jack.of(Spades.i()), Queen.of(Hearts.i()),
						Four.of(Clubs.i()));
		game.getPlayer(South.i()).init(Queen.of(Diamonds.i()), Five.of(Diamonds.i()), Queen.of(Spades.i()),
				Three.of(Diamonds.i()), King.of(Diamonds.i()), Ten.of(Clubs.i()), Six.of(Diamonds.i()),
				Ace.of(Diamonds.i()), Ace.of(Hearts.i()), Ace.of(Spades.i()), Eight.of(Diamonds.i()),
				King.of(Spades.i()), Six.of(Spades.i()));

		DoubleDummySolver pruned = new DoubleDummySolver(game);
		pruned.setUseDuplicateRemoval(true);
		pruned.setMaxTricks(3);
		pruned.search();
		assertEquals(0, pruned.getRoot().getTricksTaken(Player.WEST_EAST));
	}

	//  taking too long - commenting out for now. This should be a performance benchmark test.
	//	public void testPruningDuplicate13CardsRunOutOfMemory() {
	//		Game game = new Game(NoTrump.i());
	//		game.getWest()
	//				.init(Four.of(Hearts.i()), Ten.of(Diamonds.i()), Eight.of(Hearts.i()), Ace.of(Clubs.i()),
	//						Seven.of(Diamonds.i()), Ten.of(Hearts.i()), Seven.of(Hearts.i()), Six.of(Clubs.i()),
	//						Nine.of(Clubs.i()), Seven.of(Clubs.i()), Queen.of(Diamonds.i()), Ace.of(Hearts.i()),
	//						Two.of(Hearts.i()));
	//		game.getNorth().init(Ten.of(Clubs.i()), Nine.of(Spades.i()), Six.of(Diamonds.i()), Six.of(Spades.i()),
	//				Eight.of(Diamonds.i()), Ace.of(Diamonds.i()), Queen.of(Hearts.i()), Five.of(Hearts.i()),
	//				Queen.of(Spades.i()), Two.of(Clubs.i()), Eight.of(Spades.i()), King.of(Hearts.i()),
	//				Jack.of(Diamonds.i()));
	//		game.getEast().init(Five.of(Diamonds.i()), Jack.of(Hearts.i()), King.of(Diamonds.i()), Two.of(Spades.i()),
	//				Three.of(Diamonds.i()), Three.of(Hearts.i()), Eight.of(Clubs.i()), Nine.of(Hearts.i()),
	//				Nine.of(Diamonds.i()), King.of(Spades.i()), Three.of(Clubs.i()), Queen.of(Clubs.i()),
	//				Four.of(Spades.i()));
	//		game.getSouth().init(Seven.of(Spades.i()), Five.of(Clubs.i()), Five.of(Spades.i()), Two.of(Diamonds.i()),
	//				Four.of(Clubs.i()), Jack.of(Spades.i()), Six.of(Hearts.i()), Four.of(Diamonds.i()), Ten.of(Spades.i()),
	//				Ace.of(Spades.i()), King.of(Clubs.i()), Three.of(Spades.i()), Jack.of(Clubs.i()));
	//		game.setNextToPlay(Direction.WEST);
	//		DoubleDummySolver pruned2 = new DoubleDummySolver(game);
	//		pruned2.setUseDuplicateRemoval(true);
	//		pruned2.setUsePruneLowestCardToLostTrick(true);
	//		pruned2.setMaxTricks(4);
	//		pruned2.search();
	//		pruned2.printStats();
	//		assertEquals(2, pruned2.getRoot().getTricksTaken(Player.WEST_EAST));
	//	}

	//	public void testBugAlphaBetaGivingUpTrick() {
	//		Game game = new Game(Clubs.i());
	//		game.getWest().init(Seven.of(Spades.i()), Three.of(Clubs.i()), King.of(Hearts.i()), Two.of(Clubs.i()),
	//				Jack.of(Hearts.i()), Ace.of(Spades.i()), Nine.of(Spades.i()), Three.of(Hearts.i()), Six.of(Clubs.i()),
	//				Jack.of(Spades.i()), Ten.of(Spades.i()), Six.of(Hearts.i()), Nine.of(Clubs.i()));
	//		game.getNorth().init(Seven.of(Diamonds.i()), Three.of(Spades.i()), King.of(Diamonds.i()), Two.of(Hearts.i()),
	//				Ace.of(Diamonds.i()), Queen.of(Hearts.i()), King.of(Clubs.i()), Eight.of(Diamonds.i()),
	//				Four.of(Spades.i()), Five.of(Diamonds.i()), Nine.of(Hearts.i()), Jack.of(Clubs.i()),
	//				Four.of(Diamonds.i()));
	//		game.getEast().init(Queen.of(Diamonds.i()), Two.of(Diamonds.i()), Six.of(Diamonds.i()), King.of(Spades.i()),
	//				Jack.of(Diamonds.i()), Seven.of(Hearts.i()), Queen.of(Clubs.i()), Five.of(Hearts.i()),
	//				Four.of(Hearts.i()), Three.of(Diamonds.i()), Ten.of(Diamonds.i()), Five.of(Spades.i()),
	//				Eight.of(Spades.i()));
	//		game.getSouth().init(Four.of(Clubs.i()), Queen.of(Spades.i()), Eight.of(Clubs.i()), Ace.of(Clubs.i()),
	//				Ten.of(Hearts.i()), Seven.of(Clubs.i()), Ace.of(Hearts.i()), Six.of(Spades.i()), Nine.of(Diamonds.i()),
	//				Ten.of(Clubs.i()), Two.of(Spades.i()), Five.of(Clubs.i()), Eight.of(Hearts.i()));
	//		game.setNextToPlay(Direction.WEST);
	//		game.play(Seven.of(Spades.i()));
	//		game.play(Four.of(Spades.i()));
	//		DoubleDummySolver search = new DoubleDummySolver(game);
	//		//search.pruneAlphaBeta = false;
	//		search.setMaxTricks(3);
	//		search.search();
	//		search.printOptimalPath();
	//		System.out.println(search.getBestMoves());
	//		assertEquals(King.of(Spades.i()), search.getBestMoves().get(0));
	//	}

	public void testBugAlphaBetaGivingUpTrickShort() {
		Deal game = new Deal(Clubs.i());
		game.getWest().init(Seven.of(Spades.i()), Three.of(Clubs.i()), Jack.of(Spades.i()));
		game.getNorth().init(Four.of(Spades.i()), Five.of(Diamonds.i()), Three.of(Spades.i()));
		game.getEast().init(Queen.of(Diamonds.i()), Five.of(Spades.i()), King.of(Spades.i()));
		game.getSouth().init(Queen.of(Spades.i()), Nine.of(Diamonds.i()), Two.of(Spades.i()));
		game.setNextToPlay(Direction.WEST);
		game.play(Seven.of(Spades.i()));
		game.play(Four.of(Spades.i()));
		DoubleDummySolver search = new DoubleDummySolver(game);

		search.setUseDuplicateRemoval(false);
		search.setShouldPruneCardsInSequence(false);

		search.useAlphaBetaPruning(true);

		search.setMaxTricks(3);
		search.search();
		search.printOptimalPath();
		System.out.println(search.getBestMoves());
		assertEquals(King.of(Spades.i()), search.getBestMoves().get(0));
	}

}
