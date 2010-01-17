package org.gnubridge.core.bidding;

import org.gnubridge.core.deck.Trump;

public class Double extends Bid {

	public Double() {
		super(-2, null);
	}

	public Double(int v, Trump c) {
		super(v, c);
	}
	
	@Override
	public String toString() {
		return "Dou " + super.toString();
	}

	public boolean equals(Object a) {
		if (a instanceof Double) {
			return true;
		}
		return false;
	}
}
