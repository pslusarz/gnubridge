package org.gnubridge.core.bidding;

import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Trump;

public class Bid {
	private int value;
	private Trump trump;

	public Bid(int v, Trump c) {
		value = v;
		trump = c;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Bid)) {
			return super.equals(other);
		} else {
			return value == ((Bid) other).getValue()
					&& trump == ((Bid) other).getTrump();
		}
	}

	public int getValue() {
		return value;
	}

	public Trump getTrump() {
		return trump;
	}

	public boolean greaterThan(Bid other) {
		if (other == null) {
			return true;
		}
		if (this.equals(new Pass())) {
			return false;
		}
		if (new Pass().equals(other)) {
			return true;
		}
		if (getValue() > other.getValue()) {
			return true;
		} else if (getValue() < other.getValue()) {
			return false;
		} else {
			return isColorGreater(other);
		}
	}

	private boolean isColorGreater(Bid other) {
		if (Clubs.i().equals(trump)) {
			return false;
		}
		if (trump.equals(Diamonds.i())) {
			if (other.getTrump().equals(Clubs.i())) {
				return true;
			} else {
				return false;
			}
		}
		if (trump.equals(Hearts.i())) {
			if (other.getTrump().equals(Clubs.i())
					|| other.getTrump().equals(Diamonds.i())) {
				return true;
			} else {
				return false;
			}
		}
		if (trump.equals(Spades.i())) {
			if (other.getTrump().equals(Clubs.i())
					|| other.getTrump().equals(Diamonds.i())
					|| other.getTrump().equals(Hearts.i())) {
				return true;
			} else {
				return false;
			}
		}
		if (!NoTrump.i().equals(other.getTrump())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Integer.toString(getValue()) + " " + trump.toString();
	}

	public static Bid makeBid(int bidSize, String t) {
		Trump tr = Trump.instance(t);
		if (tr == null) {
			return new Pass();
		} else {
			return new Bid(bidSize, tr);
		}
	}
}
