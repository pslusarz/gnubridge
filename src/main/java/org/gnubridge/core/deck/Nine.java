package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class Nine {
	public static Card of(Suit denomination) {
		return new Card("9", denomination);
	}
}
