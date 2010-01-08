package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class Four {
	public static Card of(Suit denomination) {
		return new Card("4", denomination);
	}
}
