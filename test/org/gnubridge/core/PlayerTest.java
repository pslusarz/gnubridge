package org.gnubridge.core;

import java.util.List;

import junit.framework.TestCase;

import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Suit;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Jack;
import org.gnubridge.core.deck.King;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Queen;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Ten;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;

public class PlayerTest extends TestCase {

	public void testOnePlayerInitialization() {
		String[] westSpades = Card.FullSuit;
		String[] westHearts = {};
		String[] westDiamonds = {};
		String[] westClubs = {};
		Player west = new Player(Direction.WEST_DEPRECATED);
		west.init(westSpades, westHearts, westDiamonds, westClubs);
		for (int i = 0; i < westSpades.length; i++) {
			assertTrue(west.hasUnplayedCard(new Card(westSpades[i], Spades.i())));
		}

		for (int j = 1; j < Suit.list.length; j++) {
			for (int i = 0; i < Card.FullSuit.length; i++) {
				assertFalse(west.hasUnplayedCard(new Card(Card.FullSuit[i], Suit.list[j])));
			}
		}

	}

	public void testInitializationNotByReference() {
		String[] westSpades = { "2" };
		String[] westHearts = {};
		String[] westDiamonds = {};
		String[] westClubs = {};
		Player west = new Player(Direction.WEST_DEPRECATED);
		west.init(westSpades, westHearts, westDiamonds, westClubs);
		westSpades[0] = "3";
		assertFalse(west.hasUnplayedCard(new Card("3", Spades.i())));
	}

	public void testGetLegalMovesHasMatchingColor() {
		String[] westSpades = { "2", "3", "A" };
		String[] westHearts = { "J", "Q" };
		String[] westDiamonds = {};
		String[] westClubs = { "10", "K" };
		Player west = new Player(Direction.WEST_DEPRECATED);
		west.init(westSpades, westHearts, westDiamonds, westClubs);
		Trick trick = new Trick(NoTrump.i());
		trick.addCard(Three.of(Hearts.i()), null);
		trick.addCard(Ace.of(Clubs.i()), null);
		List<Card> moves = west.getPossibleMoves(trick);
		assertEquals(2, moves.size());
		assertEquals(Jack.of(Hearts.i()), moves.get(0));
		assertEquals(Queen.of(Hearts.i()), moves.get(1));

	}

	public void testGetLegalMovesNoMatchingColor() {
		String[] westSpades = { "2", "3", "A" };
		String[] westHearts = { "J", "Q" };
		String[] westDiamonds = {};
		String[] westClubs = { "10", "K" };
		Player west = new Player(Direction.WEST_DEPRECATED);
		west.init(westSpades, westHearts, westDiamonds, westClubs);
		Trick trick = new Trick(NoTrump.i());
		trick.addCard(Three.of(Diamonds.i()), null);
		trick.addCard(Ace.of(Clubs.i()), null);
		List<Card> moves = west.getPossibleMoves(trick);
		assertEquals(7, moves.size());
		assertTrue(moves.contains(Two.of(Spades.i())));
		assertTrue(moves.contains(Three.of(Spades.i())));
		assertTrue(moves.contains(Ace.of(Spades.i())));
		assertTrue(moves.contains(Jack.of(Hearts.i())));
		assertTrue(moves.contains(Queen.of(Hearts.i())));
		assertTrue(moves.contains(Ten.of(Clubs.i())));
		assertTrue(moves.contains(King.of(Clubs.i())));
	}

	public void testGetLegalMovesFirstToPlay() {
		String[] westSpades = { "2", "3", "A" };
		String[] westHearts = { "J", "Q" };
		String[] westDiamonds = {};
		String[] westClubs = { "10", "K" };
		Player west = new Player(Direction.WEST_DEPRECATED);
		west.init(westSpades, westHearts, westDiamonds, westClubs);
		Trick trick = new Trick(NoTrump.i());
		List<Card> moves = west.getPossibleMoves(trick);
		assertEquals(7, moves.size());
		assertTrue(moves.contains(Two.of(Spades.i())));
		assertTrue(moves.contains(Three.of(Spades.i())));
		assertTrue(moves.contains(Ace.of(Spades.i())));
		assertTrue(moves.contains(Jack.of(Hearts.i())));
		assertTrue(moves.contains(Queen.of(Hearts.i())));
		assertTrue(moves.contains(Ten.of(Clubs.i())));
		assertTrue(moves.contains(King.of(Clubs.i())));
	}

	public void testForcedPlay() {
		String[] westSpades = { "2", "3", "A" };
		String[] westHearts = { "J", "Q" };
		String[] westDiamonds = {};
		String[] westClubs = { "10", "K" };
		Player west = new Player(Direction.WEST_DEPRECATED);
		west.init(westSpades, westHearts, westDiamonds, westClubs);
		Trick trick = new Trick(NoTrump.i());
		trick.addCard(Three.of(Diamonds.i()), null);
		trick.addCard(Ace.of(Clubs.i()), null);
		List<Card> moves = west.getPossibleMoves(trick);
		west.play(trick, 3);
		assertTrue(west.hasPlayedCard(moves.get(3)));
		assertFalse(west.hasUnplayedCard(moves.get(3)));
	}

}
