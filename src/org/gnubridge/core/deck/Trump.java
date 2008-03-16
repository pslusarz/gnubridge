package org.gnubridge.core.deck;

public abstract class Trump {
	public abstract String toString();
	public static Trump instance(String s) {
		Trump result = null;
		if (NoTrump.i().toString().equalsIgnoreCase(s)) {
			result = NoTrump.i();
		} else if (Spades.i().toString().equalsIgnoreCase(s)) {
			result = Spades.i();
		} else if (Hearts.i().toString().equalsIgnoreCase(s)) {
			result = Hearts.i();
		} else if (Diamonds.i().toString().equalsIgnoreCase(s)) {
			result = Diamonds.i();
		} else if (Clubs.i().toString().equalsIgnoreCase(s)) {
			result = Clubs.i();
		}
		return result;
	}
}
