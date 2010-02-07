package org.gnubridge.core.bidding;

import static org.gnubridge.core.Direction.*;
import static org.gnubridge.core.bidding.Bid.*;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Direction;

public class Auctioneer {
	private Direction nextToBid;
	private int passCount;
	private Bid highBid;
	private int bidCount;
	private Call last;
	private Call beforeLast;
	private final List<Call> calls;

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

	public void bid(Bid b) {
		Bid bid = Bid.cloneBid(b);
		beforeLast = last;
		last = new Call(bid, nextToBid);
		calls.add(last);
		bidCount++;
		if (bid.isPass()) {
			passCount++;
		} else {
			passCount = 0;
			if (DOUBLE.equals(bid)) {
				getHighBid().makeDoubled();
			} else {
				highBid = bid;
			}
		}

		nextToBid = nextToBid.clockwise();
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
		} else if (beforeLast == null || beforeLast.getBid().isPass()) {
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
			if (candidate.equals(DOUBLE)) {
				if (getHighCall() != null && !getHighCall().pairMatches(nextToBid) && !getHighBid().isDoubled()) {
					return true;
				}
			} else if (candidate.isPass() || candidate.greaterThan(getHighBid())) {
				result = true;
			}
		}
		return result;
	}

	public Direction getDummy() {
		Direction result = null;
		if (biddingFinished() && getHighCall() != null) {
			for (Call call : calls) {
				if (call.getBid().hasTrump() && call.getTrump().equals(getHighCall().getTrump())
						&& call.pairMatches(getHighCall().getDirection())) {
					result = call.getDirection().opposite();
					break;
				}
			}
		}
		return result;
	}

	public Call getHighCall() {
		Bid highBid = this.highBid;
		for (Call call : calls) {
			if (call.getBid().equals(highBid)) {
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
			if (d.equals(NORTH)) {
				break;
			} else {
				d = d.clockwise();
				offset = offset.clockwise();
			}
		}
		return offset;
	}

	public boolean mayOvercall() {
		if (bidCount == 1) {
			if (firstBid().is1Suit()) {
				return true;
			}
		} else if (bidCount == 2) {
			if (firstBid().isPass() && secondBid().is1Suit()) {
				return true;
			}
		} else if (bidCount == 3) {
			if (firstBid().isPass() && secondBid().isPass() && thirdBid().is1Suit()) {
				return true;
			}
		}
		return false;
	}

	private Bid thirdBid() {
		return calls.get(2).getBid();
	}

	private Bid secondBid() {
		return calls.get(1).getBid();
	}

	private Bid firstBid() {
		return calls.get(0).getBid();
	}

	private int getCallOrderZeroBased(Bid bid) {
		int result = -1;
		for (Call call : calls) {
			result++;
			if (bid.equals(call.getBid())) {
				return result;
			}
		}
		return -1;
	}

	public boolean isOvercall(Bid bid) {
		if (PASS.equals(bid)) {
			return false;
		}
		boolean result = false;
		int callOrder = getCallOrderZeroBased(bid);
		if (callOrder == 1 && !PASS.equals(calls.get(0).getBid())) {
			return true;
		}
		return result;
	}

}
