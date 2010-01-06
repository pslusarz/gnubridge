package org.gnubridge.core.bidding;

import static org.gnubridge.core.deck.Trump.*;

import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Trump;

public class Bid {
	public static Bid PASS = new Pass();
	public static Bid DOUBLE = new Double();
	public static Bid REDOUBLE = new Redouble();

	public static Bid ONE_NOTRUMP = new Bid(1, NOTRUMP);
	public static Bid ONE_SPADES = new Bid(1, SPADES);
	public static Bid ONE_HEARTS = new Bid(1, HEARTS);
	public static Bid ONE_DIAMONDS = new Bid(1, DIAMONDS);
	public static Bid ONE_CLUBS = new Bid(1, CLUBS);

	public static Bid TWO_NOTRUMP = new Bid(2, NOTRUMP);
	public static Bid TWO_SPADES = new Bid(2, SPADES);
	public static Bid TWO_HEARTS = new Bid(2, HEARTS);
	public static Bid TWO_DIAMONDS = new Bid(2, DIAMONDS);
	public static Bid TWO_CLUBS = new Bid(2, CLUBS);

	public static Bid THREE_NOTRUMP = new Bid(3, NOTRUMP);
	public static Bid THREE_SPADES = new Bid(3, SPADES);
	public static Bid THREE_HEARTS = new Bid(3, HEARTS);
	public static Bid THREE_DIAMONDS = new Bid(3, DIAMONDS);
	public static Bid THREE_CLUBS = new Bid(3, CLUBS);

	public static Bid FOUR_NOTRUMP = new Bid(4, NOTRUMP);
	public static Bid FOUR_SPADES = new Bid(4, SPADES);
	public static Bid FOUR_HEARTS = new Bid(4, HEARTS);
	public static Bid FOUR_DIAMONDS = new Bid(4, DIAMONDS);
	public static Bid FOUR_CLUBS = new Bid(4, CLUBS);

	public static Bid SEVEN_NOTRUMP = new Bid(7, NOTRUMP);
	public static Bid SEVEN_SPADES = new Bid(7, SPADES);
	public static Bid SEVEN_HEARTS = new Bid(7, HEARTS);
	public static Bid SEVEN_DIAMONDS = new Bid(7, DIAMONDS);
	public static Bid SEVEN_CLUBS = new Bid(7, CLUBS);

	private final int value;
	private final Trump trump;
	private boolean forcing = false;
	private boolean gameForcing = false;

	public Bid(int v, Trump c) {
		value = v;
		trump = c;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Bid)) {
			return super.equals(other);
		} else {
			return value == ((Bid) other).getValue() && trump == ((Bid) other).getTrump();
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
			if (other.getTrump().equals(Clubs.i()) || other.getTrump().equals(Diamonds.i())) {
				return true;
			} else {
				return false;
			}
		}
		if (trump.equals(Spades.i())) {
			if (other.getTrump().equals(Clubs.i()) || other.getTrump().equals(Diamonds.i())
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

	public boolean isPass() {
		return new Pass().equals(this);
	}

	public boolean isForcing() {
		return forcing;
	}

	public boolean isGameForcing() {
		return gameForcing;
	}

	public void makeForcing() {
		forcing = true;

	}

	public void makeGameForcing() {
		forcing = true;
		gameForcing = true;

	}

	public boolean is1Suit() {
		if (getValue() == 1 && getTrump().isSuit()) {
			return true;
		} else {
			return false;
		}
	}
}
