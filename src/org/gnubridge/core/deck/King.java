package org.gnubridge.core.deck;

import org.gnubridge.core.Card;

public class King {
	   public static Card of(Color denomination) {
		   return new Card("K", denomination);
	   }

	public static boolean isValueOf(Card card) {
		return card.getValue() == Card.strToIntValue("K");
	}
}
