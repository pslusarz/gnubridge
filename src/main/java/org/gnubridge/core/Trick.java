package org.gnubridge.core;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.deck.Suit;
import org.gnubridge.core.deck.Trump;

public class Trick {
	private final List<Card> cards;

	private Trump trump;

	private final List<Player> players;

	public Trick(Trump trump2) {
		cards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		this.trump = trump2;
	}

	public Trick duplicate() {
		Trick result = new Trick(getTrump());
		for (Card card : cards) {
			result.addCard(card, players.get(cards.indexOf(card))); //TODO: test player duplication
		}
		return result;
	}

	public void addCard(Card card, Player p) {
		cards.add(card);
		players.add(p);
	}

	public boolean isDone() {
		return cards.size() == 4;
	}

	public Card getHighestCard() {
		Card highest = null;
		for (Card card : cards) {
			if (highest == null) {
				highest = card;
			} else if (card.trumps(highest, trump)) {
				highest = card;
			} else if (card.hasSameColorAs(highest) && card.hasGreaterValueThan(highest)) {
				highest = card;
			}
		}
		return highest;
	}

	public void setTrump(Trump trump) {
		this.trump = trump;
	}

	public Suit getDenomination() {
		if (cards.size() > 0) {
			return cards.get(0).getDenomination();
		} else {
			return null;
		}
	}

	public Trump getTrump() {
		return trump;
	}

	@Override
	public String toString() {
		return cards.toString();
	}

	public List<Card> getCards() {
		List<Card> result = new ArrayList<Card>();
		result.addAll(cards);
		return result;
	}

	public Player whoPlayed(Card card) {
		Player result = null;
		for (Card current : cards) {
			if (card.equals(current)) {
				result = players.get(cards.indexOf(current));
				break;
			}
		}
		return result;
	}

}
