package org.gnubridge.core.deck;

public abstract class Suit extends Trump {

	//note, DO NOT USE statics from Trump here, ie SPADES instead of Spades.i()
	public static final Suit[] list = { Spades.i(), Hearts.i(), Diamonds.i(), Clubs.i() };
	public static final Suit[] reverseList = { Clubs.i(), Diamonds.i(), Hearts.i(), Spades.i(), };

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
