package org.gnubridge.core.bidding;

import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Color;

public class ResponseCalculator extends PointCalculator {

	private Bid partnersBid = null;

	private ResponseCalculator(Hand hand) {
		super(hand);
	}

	public ResponseCalculator(Hand hand, Bid partnersBid) {
		this(hand);
		this.partnersBid = partnersBid;
	}

	@Override
	protected int distributionalValueForCardsInColor(Color color) {
		if (!partnersBidIsASuit()) {
			return super.distributionalValueForCardsInColor(color);
		}
		if (color.equals(partnersBid.getTrump())) {
			return 0;
		}
		int result = -1;
		if (4 <= hand.getColorLength((Color) partnersBid.getTrump())) {
			int colorLength = hand.getColorLength(color);
			if (colorLength == 0) {
				result = 5;
			} else if (colorLength == 1) {
				result = 3;
			}
		}

		if (result == -1) {
			result = super.distributionalValueForCardsInColor(color);
		}
		return result;
	}

	private boolean partnersBidIsASuit() {
		return partnersBid.getTrump() instanceof Color;
	}

}
