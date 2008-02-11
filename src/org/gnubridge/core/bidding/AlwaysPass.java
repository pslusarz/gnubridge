package org.gnubridge.core.bidding;

import org.gnubridge.core.bidding.rules.BiddingRule;

public class AlwaysPass extends BiddingRule {

	public AlwaysPass() {
		super(null, null);
	}

	@Override
	protected Bid prepareBid() {
		return new Pass();
	}

}
