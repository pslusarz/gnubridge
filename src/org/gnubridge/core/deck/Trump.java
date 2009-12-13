package org.gnubridge.core.deck;

public abstract class Trump {

	public static Trump CLUBS = Clubs.i();
	public static Trump DIAMONDS = Diamonds.i();
	public static Trump HEARTS = Hearts.i();
	public static Trump SPADES = Spades.i();
	public static Trump NOTRUMP = NoTrump.i();

	@Override
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

	public abstract String toDebugString();

	public boolean isMajorSuit() {
		return Spades.i().equals(this) || Hearts.i().equals(this);
	}

	public Color asSuit() {
		if (this instanceof Color) {
			return (Color) this;
		} else {
			throw new RuntimeException("Trying to treat " + this + " as suit.");
		}
	}

	public boolean isMinorSuit() {
		return Diamonds.i().equals(this) || Clubs.i().equals(this);
	}

	public boolean isSuit() {
		return isMajorSuit() || isMinorSuit();
	}

	public boolean isNoTrump() {
		return NoTrump.i().equals(this);
	}
}
