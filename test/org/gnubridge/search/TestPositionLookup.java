package org.gnubridge.search;

import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Eight;
import org.gnubridge.core.deck.Five;
import org.gnubridge.core.deck.Four;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Nine;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Seven;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Ten;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;
import org.gnubridge.presentation.GameUtils;

import junit.framework.TestCase;

public class TestPositionLookup extends TestCase {
	public void testSameObjectShownTwice() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		playOneTrick(g);
		
		PositionLookup pl = new PositionLookup();
		assertFalse(pl.positionEncountered(g));
		assertTrue(pl.positionEncountered(g));
	}
	public void testIgnoreStartupGame() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		
		PositionLookup pl = new PositionLookup();
		assertFalse(pl.positionEncountered(g));
		assertFalse(pl.positionEncountered(g));
	}
	private void playOneTrick(Game g) {
		for (int i = 0; i< 4; i++) {
			g.play(g.getNextToPlay().getHand().get(0));
		}
		
	}
	
	@SuppressWarnings("unused")
	public void testDistinguishDifferentPlays() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		playOneTrick(g);
		
		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g);

		Game g2 = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		playOneTrickWithSlightTwist(g2);
		
		assertFalse(pl.positionEncountered(g2));
	}
	
	private void playOneTrickWithSlightTwist(Game g2) {
		g2.play(g2.getNextToPlay().getHand().get(1));
		for (int i = 0; i< 3; i++) {
			g2.play(g2.getNextToPlay().getHand().get(0));
		}
		
	}
	
	@SuppressWarnings("unused")
	public void testOneCardPlayedDifferentObjectsSamePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		playOneTrick(g);
		
		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g);
		
		Game g2 = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		playOneTrick(g2);
		assertTrue(pl.positionEncountered(g2));
	}
	
	public void testOneCardPlayedDifferentCards() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));
		
		PositionLookup pl = new PositionLookup();
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g);
		
		Game gameWithDifferentCardPlayed = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		gameWithDifferentCardPlayed.play(gameWithDifferentCardPlayed.getNextToPlay().getHand().get(1));
		assertFalse(pl.positionEncountered(gameWithDifferentCardPlayed));		
	}

	@SuppressWarnings("unused")
	public void testCanRememberMoreThanOnePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		playOneTrick(g);
		
		PositionLookup pl = new PositionLookup();
		
		Game gameWithDifferentCardPlayed = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		playOneTrickWithSlightTwist(gameWithDifferentCardPlayed);
		
		boolean justPresentThePosition = pl.positionEncountered(gameWithDifferentCardPlayed);
		justPresentThePosition = pl.positionEncountered(g);

		assertTrue(pl.positionEncountered(g));
		assertTrue(pl.positionEncountered(gameWithDifferentCardPlayed));
	}
	
	@SuppressWarnings("unused")
	public void testTwoTricksPlayedSameFirstTrick() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		playOneTrick(g);	
		playOneTrick(g);	
		
		Game sameFirstTrick = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(sameFirstTrick);
		playOneTrick(sameFirstTrick);
		playOneTrickWithSlightTwist(sameFirstTrick);
		
		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g);

		assertFalse(pl.positionEncountered(sameFirstTrick));	
		
	}
	
	public void testDistinguishNumberOfTricks() {
		Game g = new Game(Spades.i());
		g.getPlayer(West.i()).init(new Hand("", "3,2", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(new Hand("7", "", "8", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(new Hand("", "", "", "4,5").getCardsHighToLow());
		g.getPlayer(South.i()).init(new Hand("", "", "", "10,9").getCardsHighToLow());
		
		Game differentOrder = g.duplicate();
		
		g.play(Three.of(Hearts.i()));
		g.play(Eight.of(Diamonds.i()));
		g.play(Four.of(Clubs.i()));
		g.play(Ten.of(Clubs.i()));
		
		g.play(Two.of(Hearts.i()));
		g.play(Seven.of(Spades.i()));
		g.play(Five.of(Clubs.i()));
		g.play(Nine.of(Clubs.i()));
		
		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g);
		assertTrue(pl.positionEncountered(g));
		
		differentOrder.play(Three.of(Hearts.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Four.of(Clubs.i()));
		differentOrder.play(Ten.of(Clubs.i()));
		
		differentOrder.play(Eight.of(Diamonds.i()));
		differentOrder.play(Five.of(Clubs.i()));
		differentOrder.play(Nine.of(Clubs.i()));
		differentOrder.play(Two.of(Hearts.i()));
		assertFalse(pl.positionEncountered(differentOrder));
	}
	
	public void testDistinguishPlayerTurn() {
		Game g = new Game(Spades.i());
		g.getPlayer(West.i()).init(new Hand("10", "3", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(new Hand("7", "8", "", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(new Hand("", "", "", "4,5").getCardsHighToLow());
		g.getPlayer(South.i()).init(new Hand("", "", "", "10,9").getCardsHighToLow());
		
		Game differentOrder = g.duplicate();
		
		g.play(Three.of(Hearts.i()));
		g.play(Eight.of(Hearts.i()));
		g.play(Four.of(Clubs.i()));
		g.play(Ten.of(Clubs.i()));
		
		g.play(Seven.of(Spades.i()));
		g.play(Five.of(Clubs.i()));
		g.play(Nine.of(Clubs.i()));
		g.play(Ten.of(Spades.i()));
		
		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g);
		assertTrue(pl.positionEncountered(g));
		
		differentOrder.play(Ten.of(Spades.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Four.of(Clubs.i()));
		differentOrder.play(Ten.of(Clubs.i()));
		
		differentOrder.play(Three.of(Hearts.i()));
		differentOrder.play(Eight.of(Hearts.i()));
		differentOrder.play(Five.of(Clubs.i()));
		differentOrder.play(Nine.of(Hearts.i()));
		assertFalse(pl.positionEncountered(differentOrder));
	}
	public void testOnlyApplyToCompletedTricks() {
		Game g = new Game(Spades.i());
		g.getPlayer(West.i()).init(new Hand("A,3", "", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(new Hand("7,2", "", "", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(new Hand("", "", "5,4", "").getCardsHighToLow());
		g.getPlayer(South.i()).init(new Hand("", "", "", "7,6").getCardsHighToLow());
		
		Game differentOrder = g.duplicate();
		
		g.play(Ace.of(Spades.i()));
		g.play(Two.of(Spades.i()));
		g.play(Five.of(Diamonds.i()));
		g.play(Seven.of(Clubs.i()));
		
		g.play(Three.of(Spades.i()));
		g.play(Seven.of(Spades.i()));
		
		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g);
		
		differentOrder.play(Ace.of(Spades.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Five.of(Diamonds.i()));
		differentOrder.play(Seven.of(Clubs.i()));
		
		differentOrder.play(Three.of(Spades.i()));
		differentOrder.play(Two.of(Spades.i()));
		assertFalse(pl.positionEncountered(differentOrder));
	}


}
