package org.gnubridge.core.bidding;

import org.gnubridge.core.Direction;

public class Call {
	private Bid bid;
	private Direction direction;
	private int sequence;

	public Call(Bid b, Direction d, int seq) {
		bid = b;
		direction = d;
		sequence = seq;

	}

	public Bid getBid() {
		return bid;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getSequence() {
		return sequence;
	}
	@Override
	public String toString() {
		return direction.toString()+": "+bid;
	}
}
