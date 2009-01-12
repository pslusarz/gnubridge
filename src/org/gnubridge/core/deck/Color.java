package org.gnubridge.core.deck;



public abstract class Color extends Trump {
	public static final Color[] list = { Spades.i(), Hearts.i(), Diamonds.i(), Clubs.i() };
	public static final Color[] reverseList = { Clubs.i(), Diamonds.i(),  Hearts.i(), Spades.i(), };
	public static int getIndex(Color denomination) {
		int result = -1;
		for (Color color : reverseList) {
			result ++;
			if (color.equals(denomination)) {
				break;
			}
		}
 		return result;
	}
	public abstract String toDebugString();
}
