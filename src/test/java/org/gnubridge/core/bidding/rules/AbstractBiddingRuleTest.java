package org.gnubridge.core.bidding.rules;

import static org.gnubridge.core.Direction.*;
import junit.framework.TestCase;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;

public abstract class AbstractBiddingRuleTest<T extends BiddingRule> extends TestCase {
    protected Auctioneer auctioneer;
    protected T rule;
    private final Class<T> ruleClass;

    @SuppressWarnings("unchecked")
    public AbstractBiddingRuleTest() {
        // Get the generic type at runtime
        this.ruleClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected void givenNoPriorBids() {
        auctioneer = new Auctioneer(WEST);
    }

    protected void givenBidding(Bid... bids) {
        givenNoPriorBids();
        for (Bid bid : bids) {
            auctioneer.bid(bid);
        }
    }

    protected T createRule(Auctioneer auctioneer, Hand hand) {
        try {
            Constructor<T> constructor = ruleClass.getConstructor(Auctioneer.class, Hand.class);
            return constructor.newInstance(auctioneer, hand);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create rule instance", e);
        }
    }

    protected void andPlayersCards(String... cardsBySuits) {
        Hand hand = createHand(cardsBySuits);
        andPlayersCards(hand);
    }

    protected void andPlayersCards(Hand hand) {
        rule = createRule(auctioneer, hand);
    }

    protected void ruleShouldBid(Bid bid) {
        assertEquals(bid, rule.getBid());
    }

    protected Hand createHand(String... cardsBySuits) {
        return new Hand(cardsBySuits);
    }
}