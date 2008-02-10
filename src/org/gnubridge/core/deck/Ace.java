package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

/**
 * Note, the limitations of Java here. This group of classes screams for a
 * hierarchy, but because of the static modifier, no hierarchy is possible, not
 * even a common interface.
 * 
 */
public class Ace {
	public static Card of(Color denomination) {
		return new Card("A", denomination);
	}

	public static boolean isValueOf(Card card) {
		return card.getValue() == Card.strToIntValue("A");
	}

}
