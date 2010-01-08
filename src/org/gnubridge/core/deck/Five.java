package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class Five {
	public static Card of(Suit denomination) {
		return new Card("5", denomination);
	}
}
