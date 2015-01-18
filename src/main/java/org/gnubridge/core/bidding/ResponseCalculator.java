package org.gnubridge.core.bidding;

import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Suit;

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
	protected int distributionalValueForCardsInSuit(Suit suit) {
		if (!partnersBidIsASuit()) {
			return super.distributionalValueForCardsInSuit(suit);
		}
		if (suit.equals(partnersBid.getTrump())) {
			return 0;
		}
		int result = -1;
		if (4 <= hand.getSuitLength(partnersBid.getTrump().asSuit())) {
			int colorLength = hand.getSuitLength(suit);
			if (colorLength == 0) {
				result = 5;
			} else if (colorLength == 1) {
				result = 3;
			}
		}

		if (result == -1) {
			result = super.distributionalValueForCardsInSuit(suit);
		}
		return result;
	}

	private boolean partnersBidIsASuit() {
		return partnersBid.getTrump() instanceof Suit;
	}

}
