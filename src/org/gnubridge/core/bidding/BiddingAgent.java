package org.gnubridge.core.bidding;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.rules.AlwaysPass;
import org.gnubridge.core.bidding.rules.BiddingRule;
import org.gnubridge.core.bidding.rules.Open1Color;
import org.gnubridge.core.bidding.rules.Open1NT;
import org.gnubridge.core.bidding.rules.Opener1NTRespondsToPartner;
import org.gnubridge.core.bidding.rules.Respond1ColorRaiseMajorSuit;
import org.gnubridge.core.bidding.rules.Respond1ColorWithNewSuit;
import org.gnubridge.core.bidding.rules.Respond1NT;

public class BiddingAgent {

	private List<BiddingRule> rules;

	public BiddingAgent(Auctioneer a, Hand h) {
		rules = new ArrayList<BiddingRule>();
		rules.add(new Open1NT(a, h));
		rules.add(new Open1Color(a,h));
		rules.add(new Respond1NT(a,h));
		rules.add(new Respond1ColorWithNewSuit(a,h));
		//need a test rules.add(new Respond1ColorRaiseMajorSuit(a,h));
		rules.add(new Opener1NTRespondsToPartner(a,h));
		rules.add(new AlwaysPass());
	}

	public Bid getBid() {
		Bid result = null;
		for (BiddingRule rule : rules) {
		  result = rule.getBid();
		  if (result != null) {
			  break;
		  }
		}
		return result;
	}



}
