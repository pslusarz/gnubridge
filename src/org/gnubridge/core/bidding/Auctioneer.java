package org.gnubridge.core.bidding;

import java.util.ArrayList;
import java.util.List;

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
	private Call last;
	private Call beforeLast;
	private List<Call> calls;

	public Auctioneer(Direction firstToBid) {
		this.nextToBid = firstToBid;
		bidCount = 0;
		last = null;
		beforeLast = null;
		calls = new ArrayList<Call>();
	}

	public Direction getNextToBid() {
		return nextToBid;
	}

	public List<Call> getCalls() {
		ArrayList<Call> result = new ArrayList<Call>();
		result.addAll(calls);
		return result;
	}

	public void bid(Bid bid) {
		beforeLast = last;
		last = new Call(bid, nextToBid);
		calls.add(last);
		bidCount++;
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
		} else if (beforeLast == null || beforeLast.getBid().equals(new Pass())) {
			return true;
		} else {
			return false;
		}
	}

	public Call getPartnersLastCall() {
		return beforeLast;
	}

	public Call getPartnersCall(Call playerCall) {
		int current = calls.indexOf(playerCall);
		if (current >= 2) {
			return calls.get(current - 2);
		} else {
			return null;
		}
	}

	public Call getLastCall() {
		return last;
	}

	public boolean isValid(Bid candidate) {
		boolean result = false;
		if (candidate != null) {
			if (candidate.equals(new Pass())
					|| candidate.greaterThan(getHighBid())) {
				result = true;
			}
		}
		return result;
	}

	public Direction getDummy() {
		Direction result = null;
		if (biddingFinished() && getHighCall() != null) {
			for (Call call : calls) {
				if (!call.isPass()
						&& call.getTrump().equals(getHighCall().getTrump())
						&& call.pairMatches(getHighCall().getDirection())) {
					result = call.getDirection().opposite();
					break;
				}
			}
		}
		return result;
	}

	public Call getHighCall() {
		for (Call call : calls) {
			if (call.getBid().equals(getHighBid())) {
				return call;
			}
		}
		return null;
	}

	/**
	 *      The parties in bidding are referred to by directions of the world, but
	 *      these are not the same directions as the ones during play. This method
	 *      provides a way to find the offset from what this class considers a
	 *      direction and what direction ends up being when the contract is played.
	 *      
	 *      ie: if auction's West becomes the dummy (South during play), the offset
	 *      is 1 move clockwise, and when given South as parameter, this method 
	 *      returns West.
	 */
	public Direction getDummyOffsetDirection(Direction original) {
		Direction d = getDummy();
		Direction offset = original;
		for (int i = 0; i < 4; i++) {
			if (d.equals(North.i())) {
				break;
			} else {
				d = d.clockwise();
				offset = offset.clockwise();
			}
		}
		return offset;
	}
}
