package org.gnubridge.core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Two;
import org.gnubridge.presentation.GameUtils;

public class GameTest extends TestCase {

	Game game;

	protected void setUp() {
		game = new Game(NoTrump.i());
	}

	public void testConstructorCreatesPlayers() {
		for (int i = Direction.WEST; i <= Direction.SOUTH; i++) {
			assertNotNull(game.getPlayer(i));
			assertEquals(i, game.getPlayer(i).getDirection());
		}
	}
	
	public void testFirstTrickPlayedClockwise() {
		GameUtils.initializeSingleColorSuits(game);
		game.doNextCard();
		assertEquals(12, game.getPlayer(Direction.WEST).getUnplayedCardsCount());
		assertEquals(13, game.getPlayer(Direction.NORTH).getUnplayedCardsCount());
		assertEquals(13, game.getPlayer(Direction.EAST).getUnplayedCardsCount());
		assertEquals(13, game.getPlayer(Direction.SOUTH).getUnplayedCardsCount());
		game.doNextCard();
		assertEquals(12, game.getPlayer(Direction.WEST).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.NORTH).getUnplayedCardsCount());
		assertEquals(13, game.getPlayer(Direction.EAST).getUnplayedCardsCount());
		assertEquals(13, game.getPlayer(Direction.SOUTH).getUnplayedCardsCount());
		game.doNextCard();
		assertEquals(12, game.getPlayer(Direction.WEST).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.NORTH).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.EAST).getUnplayedCardsCount());
		assertEquals(13, game.getPlayer(Direction.SOUTH).getUnplayedCardsCount());
		game.doNextCard();
		assertEquals(12, game.getPlayer(Direction.WEST).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.NORTH).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.EAST).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.SOUTH).getUnplayedCardsCount());
		game.doNextCard();
		assertEquals(11, game.getPlayer(Direction.WEST).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.NORTH).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.EAST).getUnplayedCardsCount());
		assertEquals(12, game.getPlayer(Direction.SOUTH).getUnplayedCardsCount());
	}

	public void testGameEndsWhenPlayersRunOutOfCards() {
		game.getPlayer(Direction.WEST).init(
				new Card[] { Two.of(Spades.i()) });
		game.getPlayer(Direction.NORTH).init(
				new Card[] { Two.of(Hearts.i()) });
		game.getPlayer(Direction.SOUTH).init(
				new Card[] { Two.of(Diamonds.i()) });
		game.getPlayer(Direction.EAST).init(
				new Card[] { Two.of(Clubs.i()) });
		for (int i = 0; i < 4; i++) {
			assertFalse("game ended before all cards were played", game
					.isDone());
			game.doNextCard();
		}
		assertTrue("not ended, but all cards have been played", game.isDone());
	}

	public void testGameEndsWhen52CardsPlayed() {
		GameUtils.initializeSingleColorSuits(game);
		int cardCount = 0;
		while (!game.isDone()) {
			assertNotSame("Ran out of cards, but game not finished", 52,
					cardCount);
			game.doNextCard();
			cardCount++;

		}
		assertEquals(52, cardCount);
	}

	public void testPreviousTrickTakerFirstToPlay() {
		game.getPlayer(Direction.WEST).init(Ace.of(Hearts.i()),
				Two.of(Spades.i()));
		game.getPlayer(Direction.NORTH).init(Ace.of(Diamonds.i()),
				Two.of(Hearts.i()));
		game.getPlayer(Direction.SOUTH).init(Two.of(Diamonds.i()),
				Ace.of(Spades.i()));
		game.getPlayer(Direction.EAST).init(Ace.of(Clubs.i()),
				Two.of(Clubs.i()));
		assertEquals(game.getPlayer(Direction.WEST), game.getNextToPlay());
		playTrick(game);
		assertEquals(game.getPlayer(Direction.SOUTH), game.getNextToPlay());

	}

	public void testGameKeepsTrackOfTricksTaken() {
		game.getPlayer(Direction.WEST).init(Ace.of(Hearts.i()),
				Two.of(Spades.i()));
		game.getPlayer(Direction.NORTH).init(Ace.of(Diamonds.i()),
				Two.of(Hearts.i()));
		game.getPlayer(Direction.SOUTH).init(Two.of(Diamonds.i()),
				Ace.of(Spades.i()));
		game.getPlayer(Direction.EAST).init(Ace.of(Clubs.i()),
				Two.of(Clubs.i()));
		assertEquals(game.getPlayer(Direction.WEST), game.getNextToPlay());
		playTrick(game);
		assertFalse(game.isDone());
		playTrick(game);
		assertTrue(game.isDone());
		assertEquals(1, game.getPlayer(Direction.SOUTH).countTricksTaken());
		assertEquals(1, game.getPlayer(Direction.NORTH).countTricksTaken());
	}

	public void testDuplicateReproducesHands() {
		Game original = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(original);
		Game clone = original.duplicate();
		for (int i = Direction.WEST; i <= Direction.SOUTH; i++) {
			List<Card> originalHand = original.getPlayer(i).getHand();
			List<Card> clonedHand = clone.getPlayer(i).getHand();
			assertEquals(originalHand, clonedHand);
		}
	}

	public void testDuplicateClonedDoesNotFollowOriginalsPlay() {
		Game original = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(original);
		Game clone = original.duplicate();
		Player originalPlayer = original.getNextToPlay();
		Player clonedPlayer = clone.getNextToPlay();
		Card card = originalPlayer.getPossibleMoves(new Trick(NoTrump.i())).get(12);
		assertTrue("Precondition - clone does not have original's cards",
				clonedPlayer.hasUnplayedCard(card));
		original.doNextCard();
		assertFalse("Precondition - original didn't play the expected card",
				originalPlayer.hasUnplayedCard(card));
		assertTrue(clonedPlayer.hasUnplayedCard(card));
	}
	
	public void testDuplicatePlayedCards() {
		Game original = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(original);
		Player originalPlayer = original.getNextToPlay();
		Card card = originalPlayer.getPossibleMoves(new Trick(NoTrump.i())).get(12);
		original.doNextCard();
		Game clone = original.duplicate();
		Player clonedPlayer = clone.getPlayer(originalPlayer.getDirection());

		assertFalse("Precondition - original didn't play the expected card",
				originalPlayer.hasUnplayedCard(card));
		assertFalse(clonedPlayer.hasUnplayedCard(card));
		assertTrue(clonedPlayer.hasPlayedCard(card));
	}

	public void testDuplicateNextToPlay() {
		Game original = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(original);
		original.doNextCard();
		Game clone = original.duplicate();
		assertEquals(original.nextToPlay, clone.nextToPlay);
	}

	public void testDuplicateTrump() {
		Game original = new Game(NoTrump.i());
		original.setTrump(Spades.i());
		Game clone = original.duplicate();
		assertEquals(Spades.i(), clone.getTrump());
		Game original2 = new Game(NoTrump.i());
		original2.setTrump(NoTrump.i());
		Game clone2 = original2.duplicate();
		assertEquals(NoTrump.i(), clone2.getTrump());
	}
	
	public void testDuplicateCurrentTrick() {
		Game original = new Game(Clubs.i());
		GameUtils.initializeSingleColorSuits(original);
		original.doNextCard();
		Game clone = original.duplicate();
		assertNotNull(clone.getCurrentTrick());
		assertEquals(Ace.of(Spades.i()), clone.getCurrentTrick().getHighestCard());
		assertEquals(Clubs.i(), clone.getCurrentTrick().getTrump());
	}

	public void testPlayMovesOneByOne() {
		Game game = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(game);
		Player player = game.getNextToPlay();
		Card card = player.getPossibleMoves(game.getCurrentTrick()).get(3);
		game.playMoves(newList(3));
		assertTrue(player.hasPlayedCard(card));
		player = game.getNextToPlay();
		card = player.getPossibleMoves(game.getCurrentTrick()).get(2);
		game.playMoves(newList(2));
		assertTrue(player.hasPlayedCard(card));
	}
	
	public void testPlayMovesTricksTaken() {
		Game game = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(game);
		game.playMoves(newList(0,1,2,3));
		assertEquals(1, game.getPlayer(Direction.WEST).countTricksTaken());
		game.playMoves(newList(1,1,2,3,4,5,6,0,6));
		assertEquals(3, game.getPlayer(Direction.WEST).countTricksTaken());
	}

	public void testPlayMovesOneByOneSameAsList() {
		Game original = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(original);
		Game clone = original.duplicate();

		List<Integer> moves = new ArrayList<Integer>();
		List<Card> cards = new ArrayList<Card>();

		playMove(original, moves, cards, 3);
		playMove(original, moves, cards, 2);
		playMove(original, moves, cards, 6);
		playMove(original, moves, cards, 4);

		clone.playMoves(moves);
		
		for (int i = Direction.WEST; i <= Direction.SOUTH; i++) {
		  assertTrue(clone.getPlayer(i).hasPlayedCard(cards.get(i)));	
		}
		
	}
	
	public void testGetUniqueStringCanBeUsedAsKeyToWeakHashMap() {
		Game game = new Game(NoTrump.i());
		GameUtils.initializeSingleColorSuits(game);
		assertFalse(game.getUniqueString() == game.getUniqueString());
	}
	

	private void playMove(Game game, List<Integer> moves, List<Card> cards, int i) {
		Player player = game.getNextToPlay();
		Card card = player.getPossibleMoves(game.getCurrentTrick()).get(i);
		game.playMoves(newList(i));
		moves.add(i);
		cards.add(card);		
	}

	private List<Integer> newList(int... numbers) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < numbers.length; i++) {
			result.add(numbers[i]);
		}
		return result;
	}

	private void playTrick(Game g) {
		for (int i = 0; i < 4; i++) {
			g.doNextCard();
		}

	}

}
