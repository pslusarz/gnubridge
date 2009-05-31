package org.gnubridge.core.bidding;

import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Color;

public class ResponseCalculator extends PointCalculator {

	private Bid partnersResponse = null;

	private ResponseCalculator(Hand hand) {
		super(hand);
	}

	public ResponseCalculator(Hand hand, Bid partnersResponse) {
		this(hand);
		this.partnersResponse = partnersResponse;
	}

	@Override
	protected int distributionalValueForCardsInColor(Color color) {
		if (!partnersResponseIsASuit()) {
		  return super.distributionalValueForCardsInColor(color);	
		}
		if (color.equals(partnersResponse.getTrump())) {
		  return 0;	
		}
		int result = -1;
		if ( 4 <= hand.getColorLength((Color) partnersResponse.getTrump())) {
			List<Card> cardsInColor = hand.getColorHi2Low(color);
			if (cardsInColor.size() == 0) {
				result = 5;
			} else if (cardsInColor.size() == 1) {
				result = 3;
			}
		}

		if (result == -1) {
			result = super.distributionalValueForCardsInColor(color);
		}
		return result;
	}

	private boolean partnersResponseIsASuit() {
		return partnersResponse.getTrump() instanceof Color;
	}

}
