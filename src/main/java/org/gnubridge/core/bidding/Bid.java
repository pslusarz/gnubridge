package org.gnubridge.core.bidding;

//import static org.gnubridge.core.deck.Trump.*;

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

	public static Bid ONE_NOTRUMP = new Bid(1, NoTrump.i());
	public static Bid ONE_SPADES = new Bid(1, Spades.i());
	public static Bid ONE_HEARTS = new Bid(1 , Hearts.i());
	public static Bid ONE_DIAMONDS = new Bid(1, Diamonds.i());
	public static Bid ONE_CLUBS = new Bid(1, Clubs.i());

	public static Bid TWO_NOTRUMP = new Bid(2, NoTrump.i());
	public static Bid TWO_SPADES = new Bid(2, Spades.i());
	public static Bid TWO_HEARTS = new Bid(2 , Hearts.i());
	public static Bid TWO_DIAMONDS = new Bid(2, Diamonds.i());
	public static Bid TWO_CLUBS = new Bid(2, Clubs.i());

	public static Bid THREE_NOTRUMP = new Bid(3, NoTrump.i());
	public static Bid THREE_SPADES = new Bid(3, Spades.i());
	public static Bid THREE_HEARTS = new Bid(3 , Hearts.i());
	public static Bid THREE_DIAMONDS = new Bid(3, Diamonds.i());
	public static Bid THREE_CLUBS = new Bid(3, Clubs.i());

	public static Bid FOUR_NOTRUMP = new Bid(4, NoTrump.i());
	public static Bid FOUR_SPADES = new Bid(4, Spades.i());
	public static Bid FOUR_HEARTS = new Bid(4 , Hearts.i());
	public static Bid FOUR_DIAMONDS = new Bid(4, Diamonds.i());
	public static Bid FOUR_CLUBS = new Bid(4, Clubs.i());

	public static Bid FIVE_NOTRUMP = new Bid(5, NoTrump.i());
	public static Bid FIVE_SPADES = new Bid(5, Spades.i());
	public static Bid FIVE_HEARTS = new Bid(5 , Hearts.i());
	public static Bid FIVE_DIAMONDS = new Bid(5, Diamonds.i());
	public static Bid FIVE_CLUBS = new Bid(5, Clubs.i());

	public static Bid SIX_NOTRUMP = new Bid(6, NoTrump.i());
	public static Bid SIX_SPADES = new Bid(6, Spades.i());
	public static Bid SIX_HEARTS = new Bid(6 , Hearts.i());
	public static Bid SIX_DIAMONDS = new Bid(6, Diamonds.i());
	public static Bid SIX_CLUBS = new Bid(6, Clubs.i());

	public static Bid SEVEN_NOTRUMP = new Bid(7, NoTrump.i());
	public static Bid SEVEN_SPADES = new Bid(7, Spades.i());
	public static Bid SEVEN_HEARTS = new Bid(7 , Hearts.i());
	public static Bid SEVEN_DIAMONDS = new Bid(7, Diamonds.i());
	public static Bid SEVEN_CLUBS = new Bid(7, Clubs.i());

	private final int value;
	private final Trump trump;
	private boolean forcing = false;
	private boolean gameForcing = false;
	private boolean doubled = false;

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
		if (Pass.stringValue().equals(t.toUpperCase())) {
			return new Pass();

		} else if (Double.stringValue().equals(t.toUpperCase())) {
			return new Double();
		} else if (Redouble.stringValue().equals(t.toUpperCase())) {
			return new Redouble();
		}
		return new Bid(bidSize, Trump.instance(t));
	}

	public boolean isPass() {
		return PASS.equals(this);
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

	public Bid makeDoubled() {
		doubled = true;
		return this;
	}

	public boolean isDoubled() {
		return doubled;
	}

	/**
	 * Useful to distinguish Pass, Double, and Redouble
	 */
	public boolean hasTrump() {
		return getTrump() != null;
	}

	public static Bid cloneBid(Bid b) {
		if (b.hasTrump()) {
			return new Bid(b.getValue(), b.getTrump());
		} else if (b.isPass()) {
			return new Pass();
		} else if (b.isDouble()) {
			return new Double();
		}
		return null;
	}

	private boolean isDouble() {
		return DOUBLE.equals(this);
	}

	public String longDescription() {
		String result = toString();
		if (isDoubled()) {
			result += " (Doubled)";
		}
		return result;
	}
}
