package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

import static org.gnubridge.core.bidding.Bid.DOUBLE;

public class RespondTakeoutDouble extends BiddingRule {

    public RespondTakeoutDouble(Auctioneer a, Hand h) {
        super(a, h);
    }

    @Override
    protected Bid prepareBid() {
        return null;
    }

    @Override
    protected boolean applies() {
        return DOUBLE.equals(auction.getPartnersLastCall());
    }
}
