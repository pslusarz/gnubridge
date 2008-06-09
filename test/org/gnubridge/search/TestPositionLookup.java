package org.gnubridge.search;

import org.gnubridge.core.Game;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.presentation.GameUtils;

import junit.framework.TestCase;

public class TestPositionLookup extends TestCase {
	public void testOneCardPlayed() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));
		PositionLookup pl = new PositionLookup();
		assertFalse(pl.positionEncountered(g));
		assertTrue(pl.positionEncountered(g));
		Game g2 = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		g2.play(g2.getNextToPlay().getHand().get(1));
		assertFalse(pl.positionEncountered(g2));
	}
	public void testOneCardPlayedDifferentObjectsSamePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));
		
		PositionLookup pl = new PositionLookup();
		assertFalse(pl.positionEncountered(g));
		
		Game g2 = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		g2.play(g2.getNextToPlay().getHand().get(0));
		assertTrue(pl.positionEncountered(g2));
		assertTrue(pl.positionEncountered(g));
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
	public void testCanRememberMoreThanOnePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));
		
		PositionLookup pl = new PositionLookup();
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g);
		
		Game gameWithDifferentCardPlayed = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		gameWithDifferentCardPlayed.play(gameWithDifferentCardPlayed.getNextToPlay().getHand().get(1));
		justPresentThePosition = pl.positionEncountered(gameWithDifferentCardPlayed);
		
		assertTrue(pl.positionEncountered(g));
		assertTrue(pl.positionEncountered(gameWithDifferentCardPlayed));
	}
	
	public void testTwoCardsPlayed() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));		
		g.play(g.getNextToPlay().getHand().get(0));	
		PositionLookup pl = new PositionLookup();
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g);
		assertTrue(pl.positionEncountered(g));
		
		Game sameFirstMove = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(sameFirstMove);
		sameFirstMove.play(sameFirstMove.getNextToPlay().getHand().get(0));
		System.out.println("looking up 2nd position");
		assertFalse(pl.positionEncountered(sameFirstMove));		
		
	}

}
