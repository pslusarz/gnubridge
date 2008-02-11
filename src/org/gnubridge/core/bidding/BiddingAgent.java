package org.gnubridge.core.bidding;

import java.util.ArrayList;
import java.util.List;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.rules.BiddingRule;
import org.gnubridge.core.bidding.rules.Open1NT;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Color;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Trump;

public class BiddingAgent {

	private List<BiddingRule> rules;

	public BiddingAgent(Auctioneer a, Hand h) {
		rules = new ArrayList<BiddingRule>();
		rules.add(new Open1NT(a, h));
		rules.add(new Open1Color(a,h));
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
