package org.gnubridge.core.bidding;

import java.util.List;

import org.gnubridge.core.Card;
import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.Jack;
import org.gnubridge.core.deck.King;
import org.gnubridge.core.deck.Queen;

public class PointCalculator {
	protected Hand hand;

	public PointCalculator(Hand hand) {
		this.hand = hand;
	}

	public int getHighCardPoints() {
		int highCardPoints = 0;
		for (Color color : Color.list) {
			highCardPoints += getHighCardPoints(hand.getColorHi2Low(color));
		}

		return highCardPoints;
	}

	public int getHighCardPoints(List<Card> cards) {
		int highCardPoints = 0;
		for (Card card : cards) {
			if (Ace.isValueOf(card)) {
				highCardPoints += 4;
			} else if (King.isValueOf(card)) {
				highCardPoints += 3;
			} else if (Queen.isValueOf(card)) {
				highCardPoints += 2;
			} else if (Jack.isValueOf(card)) {
				highCardPoints += 1;
			}
		}
		return highCardPoints;
	}

	public int getDistributionalPoints() {
		int result = 0;
		for (Color color : Color.list) {
			result += distributionalValueForCardsInColor(color);
		}
		return result;
	}

	protected int distributionalValueForCardsInColor(Color color) {
		List<Card> cardsInColor = hand.getColorHi2Low(color);
		int cardsCount = cardsInColor.size();
		int result = 0;
		if (cardsCount == 0) {
			result = 3;
		} else if (cardsCount == 1) {
			result = 2;
		} else if (cardsCount == 2) {
			result = 1;
		}
		return result;
	}

	public int getCombinedPoints() {
		int result = 0;
		for (Color color : Color.list) {
			List<Card> cardsInColor = hand.getColorHi2Low(color);
			result += getHighCardPoints(cardsInColor);
			if (!isFlawed(cardsInColor)) {
				result += distributionalValueForCardsInColor(color);
			}
		}
		return result;
	}

	/**
	 * If a suit contains a singleton king, queen or jack, or a doubleton K-Q,
	 * K-J, Q-J, Q-x or J-x, the holding is flawed because the outstanding ace
	 * or king may capture your honor.
	 */
	private boolean isFlawed(List<Card> cardsInColor) {
		if (cardsInColor.size() == 1) {
			if (isKorQorJ(cardsInColor.get(0))) {
				return true;
			}
		} else if (cardsInColor.size() == 2) {
			if (King.isValueOf(cardsInColor.get(0)) && isQorJ(cardsInColor.get(1))) {
				return true;
			} else if (isQorJ(cardsInColor.get(0))) {
				return true;
			}
		}
		return false;
	}

	private boolean isKorQorJ(Card card) {
		return King.isValueOf(card) || isQorJ(card);
	}

	private boolean isQorJ(Card card) {
		return Queen.isValueOf(card) || Jack.isValueOf(card);
	}

	public boolean isBalanced() {
		int doubletons = 0;
		boolean singletons = false;
		boolean voids = false;
		for (Color color : Color.list) {
			int cardsInColor = hand.getColorLength(color);
			if (cardsInColor == 0) {
				voids = true;
			} else if (cardsInColor == 1) {
				singletons = true;
			} else if (cardsInColor == 2) {
				doubletons++;
			}
		}
		if (doubletons >= 2 || singletons || voids) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isTame() {
		if (hand.matchesSuitLengthsLongToShort(4, 4, 4, 1)) {
			return true;
		}
		if (hand.matchesSuitLengthsLongToShort(5, 4, 2, 2)) {
			return true;
		}
		if (hand.matchesSuitLengthsLongToShort(5, 4, 3, 1)) {
			return true;
		}
		if (hand.matchesSuitLengthsLongToShort(6, 3, 2, 2)) {
			return true;
		}
		if (hand.matchesSuitLengthsLongToShort(6, 3, 3, 1)) {
			return true;
		}

		return false;
	}
}
