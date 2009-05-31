package org.gnubridge.search;

import junit.framework.TestCase;

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

public class TestPositionLookup extends TestCase {
	public void testSameObjectShownTwice() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		assertFalse(pl.positionEncountered(g, node.getTricksTaken()));
		assertTrue(pl.positionEncountered(g, node.getTricksTaken()));
		assertEquals(node.getTricksTaken(), pl.getNode(g));
	}


	@SuppressWarnings("unused")
	public void testOnlyReturnFirstNodeEncountetredForThePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(g, node
				.getTricksTaken());

		Game identicalTwin = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(identicalTwin);
		identicalTwin.playOneTrick();

		Node identicalTwinNode = new Node(null);
		assertTrue(pl.positionEncountered(identicalTwin, identicalTwinNode
				.getTricksTaken()));
		assertEquals(node.getTricksTaken(), pl.getNode(identicalTwin));

	}

	@SuppressWarnings("unused")
	public void testDistinguishDifferentPlays() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		Game g2 = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		playOneTrickWithSlightTwist(g2);

		assertFalse(pl.positionEncountered(g2, null));
	}

	private void playOneTrickWithSlightTwist(Game g2) {
		g2.play(g2.getNextToPlay().getHand().get(1));
		for (int i = 0; i < 3; i++) {
			g2.play(g2.getNextToPlay().getHand().get(0));
		}

	}

	@SuppressWarnings("unused")
	public void testOneCardPlayedDifferentObjectsSamePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();
		Node node = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(g, node
				.getTricksTaken());

		Game g2 = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g2);
		g2.playOneTrick();
		assertTrue(pl.positionEncountered(g2, null));
		assertEquals(node.getTricksTaken(), pl.getNode(g2));
	}

	public void testOneCardPlayedDifferentCards() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.play(g.getNextToPlay().getHand().get(0));

		PositionLookup pl = new PositionLookup();
		@SuppressWarnings("unused")
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		Game gameWithDifferentCardPlayed = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		gameWithDifferentCardPlayed.play(gameWithDifferentCardPlayed
				.getNextToPlay().getHand().get(1));
		assertFalse(pl.positionEncountered(gameWithDifferentCardPlayed, null));
	}

	@SuppressWarnings("unused")
	public void testCanRememberMoreThanOnePosition() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();

		PositionLookup pl = new PositionLookup();

		Game gameWithDifferentCardPlayed = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(gameWithDifferentCardPlayed);
		playOneTrickWithSlightTwist(gameWithDifferentCardPlayed);

		Node node = new Node(null);
		Node nodeWithDifferentCardPlayed = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(
				gameWithDifferentCardPlayed, nodeWithDifferentCardPlayed
						.getTricksTaken());
		justPresentThePosition = pl.positionEncountered(g, node
				.getTricksTaken());

		assertTrue(pl.positionEncountered(g, null));
		assertEquals(node.getTricksTaken(), pl.getNode(g));
		assertTrue(pl.positionEncountered(gameWithDifferentCardPlayed, null));
		assertEquals(nodeWithDifferentCardPlayed.getTricksTaken(), pl
				.getNode(gameWithDifferentCardPlayed));
	}

	@SuppressWarnings("unused")
	public void testTwoTricksPlayedSameFirstTrick() {
		Game g = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(g);
		g.playOneTrick();
		g.playOneTrick();

		Game sameFirstTrick = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(sameFirstTrick);
		sameFirstTrick.playOneTrick();
		playOneTrickWithSlightTwist(sameFirstTrick);

		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		assertFalse(pl.positionEncountered(sameFirstTrick, null));

	}

	public void testDistinguishNumberOfTricks() {
		Game g = new Game(Spades.i());
		g.getPlayer(West.i()).init(
				new Hand("", "3,2", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(
				new Hand("7", "", "8", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(
				new Hand("", "", "", "4,5").getCardsHighToLow());
		g.getPlayer(South.i()).init(
				new Hand("", "", "", "10,9").getCardsHighToLow());

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
		Node node = new Node(null);
		boolean justPresentThePosition = pl.positionEncountered(g, node
				.getTricksTaken());
		assertTrue(pl.positionEncountered(g, null));
		assertEquals(node.getTricksTaken(), pl.getNode(g));

		differentOrder.play(Three.of(Hearts.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Four.of(Clubs.i()));
		differentOrder.play(Ten.of(Clubs.i()));

		differentOrder.play(Eight.of(Diamonds.i()));
		differentOrder.play(Five.of(Clubs.i()));
		differentOrder.play(Nine.of(Clubs.i()));
		differentOrder.play(Two.of(Hearts.i()));
		Node differentOrderNode = new Node(null);
		assertFalse(pl.positionEncountered(differentOrder, differentOrderNode
				.getTricksTaken()));
		assertEquals(differentOrderNode.getTricksTaken(), pl
				.getNode(differentOrder));
	}

	public void testDistinguishPlayerTurn() {
		Game g = new Game(Spades.i());
		g.getPlayer(West.i()).init(
				new Hand("10", "3", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(
				new Hand("7", "8", "", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(
				new Hand("", "", "", "4,5").getCardsHighToLow());
		g.getPlayer(South.i()).init(
				new Hand("", "", "", "10,9").getCardsHighToLow());

		Game differentOrder = g.duplicate();

		g.play(Three.of(Hearts.i()));
		g.play(Eight.of(Hearts.i()));
		g.play(Four.of(Clubs.i()));
		g.play(Ten.of(Clubs.i()));

		g.play(Seven.of(Spades.i()));
		g.play(Five.of(Clubs.i()));
		g.play(Nine.of(Clubs.i()));
		g.play(Ten.of(Spades.i()));

		Node node = new Node(null);
		PositionLookup pl = new PositionLookup();

		boolean justPresentThePosition = pl.positionEncountered(g, node
				.getTricksTaken());
		assertTrue(pl.positionEncountered(g, null));

		differentOrder.play(Ten.of(Spades.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Four.of(Clubs.i()));
		differentOrder.play(Ten.of(Clubs.i()));

		differentOrder.play(Three.of(Hearts.i()));
		differentOrder.play(Eight.of(Hearts.i()));
		differentOrder.play(Five.of(Clubs.i()));
		differentOrder.play(Nine.of(Hearts.i()));
		assertFalse(pl.positionEncountered(differentOrder, null));
	}

	public void testOnlyApplyToCompletedTricks() {
		Game g = new Game(Spades.i());
		g.getPlayer(West.i()).init(
				new Hand("A,3", "", "", "").getCardsHighToLow());
		g.getPlayer(North.i()).init(
				new Hand("7,2", "", "", "").getCardsHighToLow());
		g.getPlayer(East.i()).init(
				new Hand("", "", "5,4", "").getCardsHighToLow());
		g.getPlayer(South.i()).init(
				new Hand("", "", "", "7,6").getCardsHighToLow());

		Game differentOrder = g.duplicate();

		g.play(Ace.of(Spades.i()));
		g.play(Two.of(Spades.i()));
		g.play(Five.of(Diamonds.i()));
		g.play(Seven.of(Clubs.i()));

		g.play(Three.of(Spades.i()));
		g.play(Seven.of(Spades.i()));

		PositionLookup pl = new PositionLookup();
		boolean justPresentThePosition = pl.positionEncountered(g, null);

		differentOrder.play(Ace.of(Spades.i()));
		differentOrder.play(Seven.of(Spades.i()));
		differentOrder.play(Five.of(Diamonds.i()));
		differentOrder.play(Seven.of(Clubs.i()));

		differentOrder.play(Three.of(Spades.i()));
		differentOrder.play(Two.of(Spades.i()));
		assertFalse(pl.positionEncountered(differentOrder, null));
	}

	// public void testLengthOfUniqueString() {
	// Game g = new Game(NoTrump.i());
	// GameUtils.initializeSingleColorSuits(g, 13);
	//		
	// for (int i=0; i< 13; i++) {
	// g.playOneTrick();
	// }
	//		
	// System.out.println(g.getUniqueString());
	// System.out.println(g.getUniqueString().length());
	// }

	// public void testPrimes() {
	// int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
	// 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
	// 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
	// 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
	// 179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
	// 233, 239, 241, 251, 257, 263, 269, 271, 277, 281};
	//		
	// int i = 1;
	// BigInteger total = new BigInteger("1");
	// for (int prime : primes) {
	// total = total.multiply(new BigInteger(""+ prime));
	// System.out.println(i+"("+prime+"): "+total.toString(Character.MAX_RADIX));
	// i++;
	// }
	// }

}
