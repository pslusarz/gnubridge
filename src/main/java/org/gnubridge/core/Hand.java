package org.gnubridge.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.gnubridge.core.deck.Suit;

public class Hand {
	List<Card> cards;

	/**
	 * Caching optimization for pruning played cards
	 * and perhaps others
	 */
	List<Card> orderedCards;
	Suit color;
	List<Card> colorInOrder;

	public Hand() {
		this.cards = new ArrayList<Card>();
	}

	public Hand(Card... cards) {
		this();
		for (Card card : cards) {
			this.cards.add(card);
		}
	}

	public Hand(List<Card> cards) {
		this();
		this.cards.addAll(cards);
	}

	public Hand(String... colorSuits) {
		this();
		int i = 0;
		for (String colorSuit : colorSuits) {
			cards.addAll(createCards(colorSuit, Suit.list[i]));
			i++;
		}

	}

	public void add(Card c) {
		cards.add(c);
		orderedCards = null;
		if (c.getDenomination().equals(color)) {
			colorInOrder = null;
		}

	}

	private Collection<? extends Card> createCards(String colorSuit, Suit color) {
		List<Card> results = new ArrayList<Card>();
		if (!"".equals(colorSuit.trim())) {

			String[] cardTokens = colorSuit.split(",");
			for (String cardToken : cardTokens) {
				results.add(new Card(cardToken.trim(), color));
			}
		}
		return results;
	}

	public List<Card> getSuitHi2Low(Suit suit) {
		if (suit.equals(this.color) && colorInOrder != null) {
			return colorInOrder;
		}
		List<Card> result = new ArrayList<Card>();
		for (Card card : cards) {
			if (card.getDenomination().equals(suit)) {
				insertInOrder(result, card);

			}
		}
		this.color = suit;
		colorInOrder = result;
		return result;
	}

	private void insertInOrder(List<Card> ordered, Card x) {
		if (ordered.isEmpty()) {
			ordered.add(x);
		} else {
			int i = 0;
			boolean inserted = false;
			for (Card card : ordered) {
				if (!card.hasGreaterValueThan(x)) {
					ordered.add(i, x);
					inserted = true;
					break;
				}
				i++;
			}
			if (!inserted) {
				ordered.add(x);
			}
		}

	}

	public int getSuitLength(Suit suit) {
		return getSuitHi2Low(suit).size();
	}

	public List<Card> getCardsHighToLow() {
		if (orderedCards != null) {
			List<Card> copyOfOrderedCards = new ArrayList<Card>();
			copyOfOrderedCards.addAll(orderedCards);
			return copyOfOrderedCards;
		}
		List<Card> orderedCards = new ArrayList<Card>();
		for (Suit color : Suit.list) {
			orderedCards.addAll(getSuitHi2Low(color));
		}
		this.orderedCards = orderedCards;
		return getCardsHighToLow();
	}

	public Suit getLongestSuit() {
		int longest = 0;
		Suit result = null;
		for (Suit suit : Suit.list) {
			if (longest < getSuitLength(suit)) {
				longest = getSuitLength(suit);
				result = suit;
			}
		}
		return result;
	}

	public int getLongestColorLength() {
		int result = 0;
		for (Suit color : Suit.list) {
			if (result < getSuitLength(color)) {
				result = getSuitLength(color);
			}
		}
		return result;
	}

	public boolean contains(Card card) {
		if (cards.size() == 0 && card == null) {
			return true;
		} else {
			return cards.contains(card);
		}
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	public boolean matchesSuitLengthsLongToShort(int suitLength1, int suitLength2, int suitLength3, int suitLength4) {
		List<Integer> suitLengths = new ArrayList<Integer>();
		for (Suit color : Suit.list) {
			suitLengths.add(getSuitLength(color));
		}
		Collections.sort(suitLengths);
		Collections.reverse(suitLengths);
		if (suitLengths.get(0) == suitLength1 && suitLengths.get(1) == suitLength2 && suitLengths.get(2) == suitLength3
				&& suitLengths.get(3) == suitLength4) {
			return true;
		} else {
			return false;
		}
	}

	public List<Suit> getSuitsWithAtLeastCards(int minimumSuitLength) {
		List<Suit> results = new ArrayList<Suit>();
		for (Suit suit : Suit.list) {
			if (getSuitLength(suit) >= minimumSuitLength) {
				results.add(suit);
			}
		}
		return results;
	}

	public List<Suit> getSuitsWithCardCount(int suitLength) {
		List<Suit> results = new ArrayList<Suit>();
		for (Suit suit : Suit.list) {
			if (getSuitLength(suit) == suitLength) {
				results.add(suit);
			}
		}
		return results;
	}

	//at least AQJXXX or KQTXXX
	//Pavlicek, lesson 7 online bridge basics
	public Collection<Suit> getGood5LengthSuits() {
		Collection<Suit> result = new ArrayList<Suit>();
		List<Suit> suitsOfLength5 = getSuitsWithCardCount(5);
		for (Suit suit : suitsOfLength5) {
			List<Card> cardsInSuit = getSuitHi2Low(suit);
			if (isAtLeastAQJXX(cardsInSuit) || isAtLeastKQTXX(cardsInSuit)) {
				result.add(suit);
			}
		}
		return result;
	}

	//at least QJXXX
	//Pavlicek, lesson 7 online bridge basics
	public Collection<Suit> getDecent5LengthSuits() {
		Collection<Suit> result = new ArrayList<Suit>();
		List<Suit> suitsOfLength5 = getSuitsWithCardCount(5);
		for (Suit suit : suitsOfLength5) {
			List<Card> cardsInSuit = getSuitHi2Low(suit);
			if (isAtLeastQJXXX(cardsInSuit)) {
				result.add(suit);
			}
		}
		return result;
	}

	private boolean isAtLeastQJXXX(List<Card> fiveCards) {
		if (fiveCards.get(0).getValue() >= Card.QUEEN && // keep Eclipse from formatting
				fiveCards.get(1).getValue() >= Card.JACK //

		) {
			return true;
		}
		return false;
	}

	private boolean isAtLeastKQTXX(List<Card> fiveCards) {
		if (fiveCards.get(0).getValue() >= Card.KING && // 
				fiveCards.get(1).getValue() >= Card.QUEEN && // 
				fiveCards.get(2).getValue() >= Card.TEN //

		) {
			return true;
		}
		return false;
	}

	private boolean isAtLeastAQJXX(List<Card> fiveCards) {
		if (fiveCards.get(0).getValue() >= Card.ACE && //
				fiveCards.get(1).getValue() >= Card.QUEEN && //
				fiveCards.get(2).getValue() >= Card.JACK //

		) {
			return true;
		}
		return false;
	}

	public boolean haveStopper(Suit suit) {
		List<Card> cardsInSuit = getSuitHi2Low(suit);
		if (cardsInSuit.size() > 0 && cardsInSuit.get(0).getValue() == Card.ACE) {
			return true;
		}
		if (cardsInSuit.size() > 1 && cardsInSuit.get(0).getValue() == Card.KING) {
			return true;
		}
		if (cardsInSuit.size() > 2 && cardsInSuit.get(0).getValue() == Card.QUEEN) {
			return true;
		}
		if (cardsInSuit.size() > 3 && cardsInSuit.get(0).getValue() == Card.JACK) {
			return true;
		}
		return false;
	}

}
