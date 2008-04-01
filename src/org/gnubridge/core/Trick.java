package org.gnubridge.core;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.Trump;

public class Trick {
	private List<Card> cards;

	private Trump trump;

	public Trick(Trump trump2) {
		cards = new ArrayList<Card>();
		this.trump = trump2;
	}
	
	public Trick duplicate() {
		Trick result = new Trick(getTrump());
		for (Card card : cards) {
			result.addCard(card);
		}
		return result;
	}

	public void addCard(Card card) {
		cards.add(card);
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
			} else if (	card.hasSameColorAs(highest) && card.hasGreaterValueThan(highest)) {
				highest = card;
			}
		}
		return highest;
	}

	public void setTrump(Color trump) {
		this.trump = trump;
	}

	public Color getDenomination() {
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
		List<Card> result = new ArrayList<Card> ();
		result.addAll(cards);
		return result;
	}

}
