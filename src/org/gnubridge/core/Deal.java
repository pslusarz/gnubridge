package org.gnubridge.core;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.deck.Suit;
import org.gnubridge.core.deck.Trump;
import org.gnubridge.presentation.GameUtils;

public class Deal {

	private static final int NO_FORCED_MOVE = -1;

	private static Deal preInitializedGame;

	private final Player[] players;

	int nextToPlay;

	Trick currentTrick;

	protected boolean done;

	private Trump trump;

	private int tricksPlayed;

	private Trick previousTrick;
	private Hand playedCards;

	private Player preInitializedHumanPlayer;

	public Deal(Trump trump) {
		players = new Player[4];
		for (int i = Direction.WEST_DEPRECATED; i <= Direction.SOUTH_DEPRECATED; i++) {
			players[i] = new Player(i);
		}
		nextToPlay = Direction.WEST_DEPRECATED;
		this.trump = trump;
		currentTrick = new Trick(this.getTrump());
		tricksPlayed = 0;
		done = false;
		playedCards = new Hand();

	}

	public Player getPlayer(int i) {
		return players[i];
	}

	public Player getWest() {
		return players[Direction.WEST_DEPRECATED];
	}

	public Player getNorth() {
		return players[Direction.NORTH_DEPRECATED];
	}

	public Player getEast() {
		return players[Direction.EAST_DEPRECATED];
	}

	public Player getSouth() {
		return players[Direction.SOUTH_DEPRECATED];
	}

	public void setPlayer(int i, Player p) {
		players[i] = p;

	}

	public void doNextCard() {
		doNextCard(NO_FORCED_MOVE);
	}

	// TODO: test how it interacts with play()
	public List<Card> getPlayedCardsHiToLow(Suit color) {
		return playedCards.getSuitHi2Low(color);
	}

	public void doNextCard(int forcedMoveIndex) {
		Card card;

		if (forcedMoveIndex == NO_FORCED_MOVE) {
			card = players[nextToPlay].play(currentTrick);
		} else {
			card = players[nextToPlay].play(currentTrick, forcedMoveIndex);
		}
		playedCards.add(card);
		currentTrick.addCard(card, players[nextToPlay]); // TODO: test player
		// assignment

		if (currentTrick.isDone()) {
			int winner = getWinnerIndex(currentTrick);
			nextToPlay = winner;
			players[winner].addTrickTaken(currentTrick);
			previousTrick = currentTrick;
			currentTrick = new Trick(this.getTrump());
			tricksPlayed++;
		} else {
			nextToPlay = (nextToPlay + 1) % players.length;
		}
		if (players[nextToPlay].getUnplayedCardsCount() == 0) {
			this.done = true;
		}

	}

	public Trick getPreviousTrick() {
		return previousTrick;
	}

	public int getWinnerIndex(Trick trick) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].hasPlayedCard(trick.getHighestCard())) {
				return i;
			}
		}
		throw new RuntimeException("Cannot find winning player for trick: " + trick);
	}

	public boolean isDone() {
		return this.done;
	}

	public Player getNextToPlay() {
		return players[nextToPlay];
	}

	public Deal duplicate() {
		Deal result = new Deal(getTrump());
		for (int i = Direction.WEST_DEPRECATED; i <= Direction.SOUTH_DEPRECATED; i++) {
			result.getPlayer(i).init(this.getPlayer(i));
		}
		result.nextToPlay = nextToPlay;
		result.setCurrentTrick(currentTrick.duplicate());
		if (previousTrick != null) {
			result.setPreviousTrick(previousTrick.duplicate());
		}
		result.setPlayedCards(playedCards.getCardsHighToLow()); // TODO: untested
		return result;
	}

	private void setPreviousTrick(Trick trick) {
		previousTrick = trick;

	}

	private void setPlayedCards(List<Card> cards) {
		playedCards = new Hand(cards);

	}

	private void setCurrentTrick(Trick trick) {
		currentTrick = trick;

	}

	public void playMoves(List<Integer> moves) {
		for (int move : moves) {
			doNextCard(move);
		}
	}

	public Trick getCurrentTrick() {
		return currentTrick;
	}

	public void setNextToPlay(int direction) {
		this.nextToPlay = direction;

	}

	public int getTricksTaken(int pair) {
		switch (pair) {
		case Player.WEST_EAST:
			return getPlayer(Direction.WEST_DEPRECATED).countTricksTaken()
					+ getPlayer(Direction.EAST_DEPRECATED).countTricksTaken();
		case Player.NORTH_SOUTH:
			return getPlayer(Direction.NORTH_DEPRECATED).countTricksTaken()
					+ getPlayer(Direction.SOUTH_DEPRECATED).countTricksTaken();
		default:
			throw new RuntimeException("Unknown pair: " + pair);
		}

	}

	public boolean oneTrickLeft() {
		return (getCurrentTrick().getHighestCard() == null && getNextToPlay().getHand().size() == 1);
	}

	public void setTrump(Trump d) {
		this.trump = d;
		if (currentTrick != null) {
			currentTrick.setTrump(d);
		}

	}

	public Trump getTrump() {
		return trump;
	}

	public int getTricksPlayed() {
		return tricksPlayed;
	}

	public List<Player> getPlayers() {
		List<Player> result = new ArrayList<Player>();
		for (int i = 0; i < players.length; i++) {
			result.add(players[i]);
		}
		return result;
	}

	public Player getPlayer(Direction d) {
		return getPlayer(d.getValue());
	}

	public boolean isLegalMove(Card card) {
		return getNextToPlay().getPossibleMoves(currentTrick).contains(card);
	}

	public void play(String card) {
		play(Card.get(card));
	}

	public void play(Card c) {
		List<Card> possibleMoves = getNextToPlay().getPossibleMoves(currentTrick);
		doNextCard(possibleMoves.indexOf(c));

	}

	// TODO: note, this is currently only tested by TestPositionLookup
	// indirectly
	public Hand getPlayedCards() {
		return playedCards;
	}

	public void setHumanPlayer(Player p) {
		preInitializedHumanPlayer = p;
	}

	public Player selectHumanPlayer() {
		Player result;
		if (preInitializedHumanPlayer != null) {
			result = preInitializedHumanPlayer;
			preInitializedHumanPlayer = null;
		} else {
			result = players[(int) Math.floor(Math.random() * players.length)];
		}
		return result;
	}

	public static void setPreInitializedGame(Deal preInitializedGame) {
		Deal.preInitializedGame = preInitializedGame;
	}

	public static Deal construct() {
		Deal result;
		if (preInitializedGame != null) {
			result = preInitializedGame;
			preInitializedGame = null;
		} else {
			result = new Deal(null);
			GameUtils.initializeRandom(result.getPlayers(), 13);
		}
		return result;
	}

	public void printHandsDebug() {
		for (Player player : players) {
			System.out.println("game.get" + player + "().init(" + printHandDebug(player.getHand()) + ");");
		}
		System.out.println("game.setNextToPlay(Direction." + getNextToPlay().toString().toUpperCase() + ");");
		if (getTrump() != null) {
			System.out.println("game.setTrump(" + getTrump().toDebugString() + ");");
		}

	}

	public void printHands() {
		for (Player player : players) {
			System.out.println(player + ": " + player.getHand());
		}
	}

	private String printHandDebug(List<Card> cards) {
		String result = "";
		boolean noLeadingCommaOnFirstElement = true;
		for (Card card : cards) {
			if (noLeadingCommaOnFirstElement) {
				noLeadingCommaOnFirstElement = false;
			} else {
				result += ", ";
			}
			result += card.toDebugString();

		}
		return result;
	}

	public void playOneTrick() {
		for (int i = 0; i < 4; i++) {
			play(getNextToPlay().getHand().get(0));
		}

	}

	public String getKeyForWeakHashMap() {
		String cardsPlayedRepresentation = "";
		for (Card card : getPlayedCards().getCardsHighToLow()) {
			cardsPlayedRepresentation += card.getIndex() + ",";
		}
		String unique = cardsPlayedRepresentation + "*" + getDeclarerTricksTaken() + "*"
				+ getNextToPlay().getDirection();
		return unique;
	}

	public int getDeclarerTricksTaken() {
		return getTricksTaken(Player.NORTH_SOUTH);
	}

	public List<Card> getPossibleMoves() {
		return getNextToPlay().getPossibleMoves(currentTrick);
	}

}
