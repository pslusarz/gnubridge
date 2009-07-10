package org.gnubridge.core.deck;

public abstract class Color extends Trump {
	public static final Color[] list = { Spades.i(), Hearts.i(), Diamonds.i(), Clubs.i() };
	public static final Color[] reverseList = { Clubs.i(), Diamonds.i(), Hearts.i(), Spades.i(), };

	public static int getIndex(Color denomination) {
		int result = -1;
		for (Color color : reverseList) {
			result++;
			if (color.equals(denomination)) {
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
