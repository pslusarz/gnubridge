package org.gnubridge.core.bidding;

import org.gnubridge.core.Direction;

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
		return direction.toString()+": "+bid;
	}
}
