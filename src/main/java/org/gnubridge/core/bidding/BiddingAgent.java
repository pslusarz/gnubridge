package org.gnubridge.core.bidding;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.rules.AlwaysPass;
import org.gnubridge.core.bidding.rules.BiddingRule;
import org.gnubridge.core.bidding.rules.Open1Color;
import org.gnubridge.core.bidding.rules.Open1NT;
import org.gnubridge.core.bidding.rules.Overcall1NT;
import org.gnubridge.core.bidding.rules.OvercallSuit;
import org.gnubridge.core.bidding.rules.Rebid1ColorOriginalSuit;
import org.gnubridge.core.bidding.rules.Rebid1ColorRaisePartner;
import org.gnubridge.core.bidding.rules.Rebid1ColorWithNT;
import org.gnubridge.core.bidding.rules.Rebid1ColorWithNewSuit;
import org.gnubridge.core.bidding.rules.Rebid1NT;
import org.gnubridge.core.bidding.rules.Respond1ColorRaiseMajorSuit;
import org.gnubridge.core.bidding.rules.Respond1ColorRaiseMinorSuit;
import org.gnubridge.core.bidding.rules.Respond1ColorWithNT;
import org.gnubridge.core.bidding.rules.Respond1ColorWithNewSuit;
import org.gnubridge.core.bidding.rules.Respond1NT;
import org.gnubridge.core.bidding.rules.RespondOvercallSuit;

public class BiddingAgent {

	private final List<BiddingRule> rules;

	public BiddingAgent(Auctioneer a, Hand h) {
		rules = new ArrayList<BiddingRule>();
		rules.add(new Open1NT(a, h));
		rules.add(new Open1Color(a, h));
		rules.add(new Respond1NT(a, h));
		rules.add(new Respond1ColorRaiseMajorSuit(a, h));
		rules.add(new Respond1ColorWithNewSuit(a, h));
		rules.add(new Respond1ColorRaiseMinorSuit(a, h));
		rules.add(new Respond1ColorWithNT(a, h));
		rules.add(new Rebid1NT(a, h));
		rules.add(new Rebid1ColorRaisePartner(a, h));
		rules.add(new Rebid1ColorWithNewSuit(a, h));
		rules.add(new Rebid1ColorOriginalSuit(a, h));
		rules.add(new Rebid1ColorWithNT(a, h));
		rules.add(new OvercallSuit(a, h));
		rules.add(new RespondOvercallSuit(a, h));
		rules.add(new Overcall1NT(a, h));
		rules.add(new AlwaysPass());
	}

	public Bid getBid() {
		Bid result = null;
		for (BiddingRule rule : rules) {
			result = rule.getBid();
			if (result != null) {
				//System.out.println("rule: " + rule.getClass() + " recommends: " + result);
				break;
			}
		}
		return result;
	}

}
