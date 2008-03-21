package org.gnubridge.core.bidding;

import org.gnubridge.core.Direction;
import org.gnubridge.core.deck.Trump;

public class Call {
	private Bid bid;
	private Direction direction;

	public Call(Bid b, Direction d) {
		bid = b;
		direction = d;

	}

	public Bid getBid() {
		return bid;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return direction.toString() + ": " + bid;
	}

	public Trump getTrump() {
		return bid.getTrump();
	}

	public boolean isPass() {
		return new Pass().equals(bid);
	}

	public boolean pairMatches(Direction candidate) {
		if (direction.equals(candidate)
				|| direction.opposite().equals(candidate)) {
			return true;
		} else {
			return false;
		}
	}
}
