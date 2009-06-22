package org.gnubridge.search;

import junit.framework.TestCase;

import org.gnubridge.core.East;
import org.gnubridge.core.Game;
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

public class SearchAcceptanceTest extends TestCase {
  public void test13cards4deep(){
		Game game = new Game(NoTrump.i());
		game.getPlayer(West.i()).init(King.of(Clubs.i()), King.of(Hearts.i()),
				Two.of(Diamonds.i()), Seven.of(Clubs.i()),
				Jack.of(Diamonds.i()), Eight.of(Clubs.i()),
				Four.of(Diamonds.i()), Ten.of(Hearts.i()), Three.of(Clubs.i()),
				Ten.of(Spades.i()), Eight.of(Hearts.i()), Five.of(Spades.i()),
				Ten.of(Diamonds.i()));
		game.getPlayer(North.i()).init(Four.of(Spades.i()), Ace.of(Clubs.i()),
				Five.of(Hearts.i()), Four.of(Hearts.i()), Nine.of(Spades.i()),
				Two.of(Spades.i()), Two.of(Hearts.i()), Nine.of(Clubs.i()),
				Jack.of(Clubs.i()), Nine.of(Diamonds.i()), Jack.of(Hearts.i()),
				Nine.of(Hearts.i()), Three.of(Spades.i()));
		game.getPlayer(East.i()).init(Two.of(Clubs.i()), Five.of(Clubs.i()),
				Queen.of(Clubs.i()), Three.of(Hearts.i()),
				Seven.of(Spades.i()), Seven.of(Hearts.i()),
				Seven.of(Diamonds.i()), Six.of(Clubs.i()),
				Eight.of(Spades.i()), Six.of(Hearts.i()), Jack.of(Spades.i()),
				Queen.of(Hearts.i()), Four.of(Clubs.i()));
		game.getPlayer(South.i()).init(Queen.of(Diamonds.i()),
				Five.of(Diamonds.i()), Queen.of(Spades.i()),
				Three.of(Diamonds.i()), King.of(Diamonds.i()),
				Ten.of(Clubs.i()), Six.of(Diamonds.i()), Ace.of(Diamonds.i()),
				Ace.of(Hearts.i()), Ace.of(Spades.i()), Eight.of(Diamonds.i()),
				King.of(Spades.i()), Six.of(Spades.i()));

		DoubleDummySolver pruned = new DoubleDummySolver(game);
		pruned.setUseDuplicateRemoval(true);
		pruned.setMaxTricks(3);
		pruned.search();
		assertEquals(1, pruned.getRoot().getTricksTaken(Player.WEST_EAST));
  }
}
