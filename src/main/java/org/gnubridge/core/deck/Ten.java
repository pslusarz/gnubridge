package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class Ten {
	   public static Card of(Suit denomination) {
		   return new Card("10", denomination);
	   }
}
