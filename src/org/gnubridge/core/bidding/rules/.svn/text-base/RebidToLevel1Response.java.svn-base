package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;

public abstract class RebidToLevel1Response extends Rebid {

	public RebidToLevel1Response(Auctioneer a, Hand h) {
		super(a, h);
	}

	@Override
	protected boolean applies() {
		return super.applies() && response.getValue() == 1;
	}

}
