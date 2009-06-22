package org.gnubridge.core.deck;

public abstract class Trump {
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

	public Color asColor() {
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
}
