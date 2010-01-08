package org.gnubridge.core.deck;

public abstract class Suit extends Trump {

	public static final Suit[] list = { SPADES, HEARTS, DIAMONDS, CLUBS };
	public static final Suit[] reverseList = { CLUBS, DIAMONDS, HEARTS, SPADES };

	public static int getIndex(Suit denomination) {
		int result = -1;
		for (Suit suit : reverseList) {
			result++;
			if (suit.equals(denomination)) {
				break;
			}
		}
		return result;
	}

	@Override
	public abstract String toDebugString();

	public boolean isLowerRankThan(Trump other) {
		if (!other.isSuit()) {
			return false;
		}
		return getIndex(this) < getIndex(other.asSuit());
	}
}
