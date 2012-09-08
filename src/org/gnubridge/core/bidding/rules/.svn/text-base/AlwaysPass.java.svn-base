package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.Pass;

public class AlwaysPass extends BiddingRule {

	public AlwaysPass() {
		super(null, null);
	}

	@Override
	protected Bid prepareBid() {
		return new Pass();
	}

	@Override
	protected boolean applies() {
		return true;
	}

}
