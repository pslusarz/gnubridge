package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class Jack {
	public static Card of(Color denomination) {
		return new Card("J", denomination);
	}

	public static boolean isValueOf(Card card) {
		return card.getValue() == Card.strToIntValue("J");
	}
}
