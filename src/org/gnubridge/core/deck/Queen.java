package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class Queen {
	public static Card of(Color denomination) {
		return new Card("Q", denomination);
	}

	public static boolean isValueOf(Card card) {
		return card.getValue() == Card.strToIntValue("Q");
	}
}
