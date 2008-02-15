package org.gnubridge.core.bidding;

import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.North;
import org.gnubridge.core.South;
import org.gnubridge.core.West;

public class Auctioneer {
	// Player[] players = { West.i(), North.i(), East.i(), South.i() };
	private Direction nextToBid;
	private int passCount;
	private Bid highBid;
	private int bidCount;
	private Bid last;
	private Bid beforeLast;

	public Auctioneer(Direction firstToBid) {
		this.nextToBid = firstToBid;
		bidCount = 0;
		last = null;
		beforeLast = null;
	}

	public Direction getNextToBid() {
		return nextToBid;
	}

	public void bid(Bid bid) {
		bidCount++;
		beforeLast = last;
		last = bid;
		if (new Pass().equals(bid)) {
			passCount++;
		} else {
			passCount = 0;
			highBid = bid;
		}
		if (West.i().equals(nextToBid)) {
			nextToBid = North.i();
		} else if (North.i().equals(nextToBid)) {
			nextToBid = East.i();
		} else if (East.i().equals(nextToBid)) {
			nextToBid = South.i();
		} else if (South.i().equals(nextToBid)) {
			nextToBid = West.i();
		}
	}

	public boolean biddingFinished() {
		return (passCount == 3 && highBid != null) || passCount == 4;
	}

	public Bid getHighBid() {
		return highBid;
	}

	public boolean isOpeningBid() {
		if (bidCount > 3) {
			return false;
		} else if (beforeLast == null || beforeLast.equals(new Pass())) {
			return true;
		} else {
			return false;
		}
	}
	
	public Bid getPartnersBid() {
		return beforeLast;
	}
}
