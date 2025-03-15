package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public class Respond1Color extends Response {


    private final Respond1ColorRaiseMajorSuit raiseMajorSuit;
    private final Respond1ColorWithNewSuit newSuit;
    private final Respond1ColorRaiseMinorSuit raiseMinorSuit;
    private final Respond1ColorWithNT nt;

    public Respond1Color(Auctioneer a, Hand h) {
        super(a, h);
        newSuit = new Respond1ColorWithNewSuit(a, h);
        raiseMajorSuit = new Respond1ColorRaiseMajorSuit(a, h);
        raiseMinorSuit = new Respond1ColorRaiseMinorSuit(a, h);
        nt = new Respond1ColorWithNT(a, h);

    }

    @Override
    protected boolean applies() {
        return newSuit.applies() || raiseMajorSuit.applies() || raiseMinorSuit.applies() || nt.applies();
    }

    @Override
    protected Bid prepareBid() {
        Bid newSuitBid = newSuit.applies() ? newSuit.getBid() : null;
        Bid raiseMajorSuitBid = raiseMajorSuit.applies() ? raiseMajorSuit.getBid() : null;
        Bid raiseMinorSuitBid = raiseMinorSuit.applies() ? raiseMinorSuit.getBid() : null;
        Bid ntBid = nt.applies() ? nt.getBid() : null;
        System.out.println("newSuitBid = " + newSuitBid);
        System.out.println("raiseMajorSuitBid = " + raiseMajorSuitBid);
        System.out.println("raiseMinorSuitBid = " + raiseMinorSuitBid);
        System.out.println("ntBid = " + ntBid);
        if (raiseMajorSuitBid != null) {
            return raiseMajorSuitBid;
        } else if (newSuitBid != null && newSuitBid.getTrump().isMajorSuit()) {
            return newSuitBid;
        } else if (ntBid != null && (newSuitBid == null || (newSuitBid.getValue() <= ntBid.getValue()))
                && (raiseMinorSuitBid == null || raiseMinorSuitBid.getValue() < ntBid.getValue())) {
            return ntBid;
        } else if (newSuitBid != null && newSuitBid.getTrump().isMinorSuit()) {
            return newSuitBid;
        } else if (raiseMinorSuitBid != null) {
            return raiseMinorSuitBid;
        }
        return null;
    }
}
