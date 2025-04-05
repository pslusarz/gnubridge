package org.gnubridge.core.bidding;

import org.gnubridge.core.Direction;
import org.gnubridge.core.deck.Trump;

import static org.gnubridge.core.bidding.Bid.DOUBLE;
import static org.gnubridge.core.bidding.Bid.PASS;

public class Call {
	private final Bid bid;
	private final Direction direction;
	private final Call previous;

	public Call(Bid b, Direction d, Call previous) {
		bid = b;
		direction = d;
		this.previous = previous;

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

	public Call getDoubledCall() {
		if (DOUBLE.equals(this.bid)) {
			if (this.previous != null) {
				if (!PASS.equals(this.previous.bid)) {
					return this.previous;
				} else if (this.previous.previous != null && this.previous.previous.previous != null && !PASS.equals(this.previous.previous.previous.getBid())) {
					return this.previous.previous.previous;
				}
			}
		}
		return null;
	}

	public boolean pairMatches(Direction candidate) {
		if (direction.equals(candidate) || direction.opposite().equals(candidate)) {
			return true;
		} else {
			return false;
		}
	}

}
