package org.gnubridge.core.bidding.rules;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.PointCalculator;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Suit;
import org.gnubridge.core.deck.Trump;

import java.util.Comparator;
import java.util.List;

import static org.gnubridge.core.bidding.Bid.*;

public class RespondTakeoutDouble extends BiddingRule {

    public RespondTakeoutDouble(Auctioneer a, Hand h) {
        super(a, h);
    }

    @Override
    protected Bid prepareBid() {
        PointCalculator pointCalculator = new PointCalculator(hand);
        List<Suit> suits = hand.getSuitsWithAtLeastCards(4);
        suits.removeAll(auction.getEnemyTrumps());
        Suit longestSuit = suits.stream()
                .max(Comparator.comparingInt(suit -> hand.getSuitLength(suit)))
                .orElse(null);
        Trump trumpToBid = null;
        if (longestSuit == null) {
            trumpToBid = NoTrump.i();
        } else {
            trumpToBid = longestSuit;
        }

        Bid enemyHighBid = auction.getHighBid();
        int lowestBid = 0;
        if (trumpToBid.isSuit() && trumpToBid.asSuit().isLowerRankThan(enemyHighBid.getTrump())) {
            lowestBid = enemyHighBid.getValue() + 1;
        } else {
            lowestBid = enemyHighBid.getValue();
        }
        int points = pointCalculator.getCombinedPoints();
        if (points < 10) {
            return new Bid(lowestBid, trumpToBid);
        } else if (points < 13) {
            return new Bid(lowestBid+1, trumpToBid);
        } else {
            if (trumpToBid.isNoTrump()) {
                return THREE_NOTRUMP;
            } else if (trumpToBid.isMajorSuit()) {
                return new Bid(4, trumpToBid);
            } else {
                return new Bid(5, trumpToBid);
            }
        }
    }

    @Override
    protected boolean applies() {
        return auction.getCalls().size() == 3 && DOUBLE.equals(auction.getPartnersLastCall().getBid()) && PASS.equals(auction.getLastCall().getBid());
    }
}
