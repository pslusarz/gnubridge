package org.gnubridge.core.bidding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.rules.*;

public class BiddingAgent {

	private final List<BiddingRule> rules;

	public BiddingAgent(Auctioneer a, Hand h) {
		rules = createRules(a, h);
	}

	private ArrayList<BiddingRule> createRules(Auctioneer a, Hand h) {
		ArrayList<BiddingRule> result = new ArrayList<BiddingRule>();
		result.add(new Open1NT(a, h));
		result.add(new Open1Color(a, h));
		result.add(new Respond1NT(a, h));
		result.add(new Respond1Color(a, h));
		result.add(new Rebid1NT(a, h));
		result.add(new Rebid1ColorRaisePartner(a, h));
		result.add(new Rebid1ColorWithNewSuit(a, h));
		result.add(new Rebid1ColorOriginalSuit(a, h));
		result.add(new Rebid1ColorWithNT(a, h));
		result.add(new OvercallSuit(a, h));
		result.add(new RespondOvercallSuit(a, h));
		result.add(new Overcall1NT(a, h));
		result.add(new TakeoutDouble(a, h));
		result.add(new AlwaysPass());
		return result;
	}

	public BiddingAgent(Auctioneer a, Hand h, ArrayList<BiddingRule> additionalTestRules) {
		this(a,h);
		rules.addAll(additionalTestRules);
	}

	public Bid getBid() {
		for (BiddingRule rule : rules) {
			if (rule.getBid() != null) {
				System.out.println("Rule "+rule.getClass().getName()+" recommends "+rule.getBid());
			}
		}
		return rules.stream()
				.map(rule ->  rule.getBid())
				.filter(result -> result != null)
				.max(Comparator.comparing(bid -> ScoreCalculator.calculateRawScore(bid))).get();
	}

}
