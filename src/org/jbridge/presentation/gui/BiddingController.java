package org.jbridge.presentation.gui;

import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.Player;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.BiddingAgent;
import org.gnubridge.presentation.GameUtils;

public class BiddingController {

	private BiddingView view;
	private Game cardHolder;
	private Auctioneer auction;
	private Player human;
	private GBController parent;

	public BiddingController(BiddingView v, GBController p) {
		view = v;
		view.setController(this);
		parent = p;
		cardHolder = new Game(null);
		GameUtils.initializeRandom(cardHolder.getPlayers(), 13);
		auction = new Auctioneer(West.i());
		view.setAuction(auction);
		human = cardHolder.getSouth();
		view.setCards(new Hand(human.getHand()));
		doAutomatedBidding();
		
		// fake bidding to get to the other page
		//		auction.bid(new Bid(7, NoTrump.i()));
		//		doAutomatedBidding();
		//        playGame();
	}

	public Game getCardHolder() {
		return cardHolder;
	}

	public Auctioneer getAuction() {
		return auction;
	}

	public Player getHuman() {
		return human;
	}
	
	private void doAutomatedBidding() {
		while (!auction.biddingFinished()
				&& !auction.getNextToBid().equals(human.getDirection2())) {
			Hand hand = new Hand(cardHolder.getPlayer(
					auction.getNextToBid().getValue()).getHand());
			BiddingAgent ba = new BiddingAgent(auction, hand);
			auction.bid(ba.getBid());
			view.auctionStateChanged();
		}

	}
	
	public void placeBid(int bidSize, String trump) {
		if (!auction.biddingFinished()) {
			if (!auction.getNextToBid().equals(human.getDirection2())) {
				view.display("Not your turn to bid");
				return;
			}
			Bid candidate = Bid.makeBid(bidSize, trump);
			if (!auction.isValid(candidate)) {
				view.display("Invalid bid");
				return;
			}
			auction.bid(candidate);
			view.display("Bid placed:" + candidate);
			view.auctionStateChanged();
			doAutomatedBidding();
		}
		if (auction.biddingFinished()) {
			view.display(
					"BIDDING COMPLETE. High bid: " + auction.getHighBid());
		}
	}

	public void playGame() {
		parent.playGame();
		
	}

}
