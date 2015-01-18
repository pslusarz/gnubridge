package org.gnubridge.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gnubridge.core.deck.Suit;

public class Player {

	public static final int WEST_EAST = 0;
	public static final int NORTH_SOUTH = 1;

	private final int direction;

	private final List<Card> hand;

	private final List<Card> played;

	private final List<Trick> tricks;

	public Player(int i) {
		hand = new ArrayList<Card>();
		played = new ArrayList<Card>();
		tricks = new ArrayList<Trick>();
		direction = i;
	}

	public Player(Direction d) {
		this(d.getValue());
	}

	
	public void hand(String... suits) {
		init(new Hand(suits).getCardsHighToLow());

	}

	public void init(String[]... valueSuits) {
		for (int i = 0; i < valueSuits.length; i++) {
			String[] values = valueSuits[i];
			for (int j = 0; j < values.length; j++) {
				hand.add(new Card(values[j], Suit.list[i]));

			}
		}

	}

	@Override
	public String toString() {
		return getDirection2().toString();

	}

	public void init(Card... cards) {
		for (int i = 0; i < cards.length; i++) {
			hand.add(cards[i]);
		}

	}

	public void init(List<Card> cards) {
		for (Card card : cards) {
			hand.add(card);
		}

	}

	public void init(Player other) {
		init(other.getHand());
		for (Card card : other.getPlayedCards()) {
			played.add(card);
		}
	}

	private List<Card> getPlayedCards() {
		return played;
	}

	public boolean hasUnplayedCard(Card c) {
		return contains(hand, c);

	}

	private boolean contains(List<Card> list, Card c) {
		for (Card current : list) {
			if (current.equals(c)) {
				return true;
			}
		}
		return false;
	}

	public int getUnplayedCardsCount() {
		return hand.size();
	}

	public int getDirection() {
		return direction;
	}

	public Card play(Trick trick) {
		return play(trick, getPossibleMoves(trick).size() - 1);

	}

	public boolean hasPlayedCard(Card c) {
		return contains(played, c);
	}

	public int countTricksTaken() {
		return tricks.size();
	}

	public void addTrickTaken(Trick trick) {
		tricks.add(trick);

	}

	public List<Card> getPossibleMoves(Trick trick) {
		List<Card> matching = new ArrayList<Card>();
		for (Card card : hand) {
			if (card.getDenomination().equals(trick.getDenomination())) {
				matching.add(card);
			}
		}
		if (matching.size() == 0) {
			return orderLowToHigh(hand);
		} else {
			return orderLowToHigh(matching);
		}
	}

	private List<Card> orderLowToHigh(List<Card> cards) {
		List<Card> result = new Hand(cards).getCardsHighToLow();
		Collections.reverse(result);
		return result;
	}

	public Card play(Trick trick, int moveIndex) {
		List<Card> moves = getPossibleMoves(trick);
		if (moves.size() == 0) {
			System.out.println(this + " has no possible move for " + trick + " (hand: " + getHand() + ")");
		}
		Card result = moves.get(moveIndex);
		played.add(result);
		hand.remove(hand.indexOf(result));
		return result;

	}

	public List<Card> getHand() {
		return hand;
	}

	public int pair() {
		return matchPair(direction);
	}

	public static int matchPair(int player) {
		int result;
		switch (player) {
		case Direction.WEST_DEPRECATED:
		case Direction.EAST_DEPRECATED:
			result = Player.WEST_EAST;
			break;
		case Direction.NORTH_DEPRECATED:
		case Direction.SOUTH_DEPRECATED:
			result = Player.NORTH_SOUTH;
			break;
		default:
			throw new RuntimeException("Unknown player: " + player);
		}
		return result;
	}

	public int otherPair() {
		return matchPair((direction + 1) % 4);
	}

	public Direction getDirection2() {
		return Direction.instance(direction);
	}

	public void init(Hand aHand) {
		init(aHand.cards);

	}

	public boolean isPartnerWith(Player other) {
		return pair() == other.pair();

	}

}
