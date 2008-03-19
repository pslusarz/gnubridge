package org.jbridge.presentation.gui;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.Player;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.BiddingAgent;
import org.gnubridge.core.deck.Trump;
import org.gnubridge.presentation.GameUtils;

public class GBController {

	private MainWindow view;
	private Auctioneer auction;
	Player human;
	Game game;
	private Game game2;

	public GBController(MainWindow view) {
		this.view = view;
		auction = new Auctioneer(West.i());
		game = new Game(null);
		GameUtils.initializeRandom(game.getPlayers(), 13);
		human = game.getSouth();
		view.setCards(new Hand(human.getHand()));
		view.setAuction(auction);
		doAutomatedBidding();

	}

	private void doAutomatedBidding() {
		while (!auction.biddingFinished()
				&& !auction.getNextToBid().equals(human.getDirection2())) {
			Hand hand = new Hand(game.getPlayer(
					auction.getNextToBid().getValue()).getHand());
			BiddingAgent ba = new BiddingAgent(auction, hand);
			auction.bid(ba.getBid());
			view.auctionStateChanged();
		}

	}

	public void placeBid(int bidSize, String trump) {
		if (!auction.biddingFinished()) {
			if (!auction.getNextToBid().equals(human.getDirection2())) {
				view.getBiddingDisplay().display("Not your turn to bid");
				return;
			}
			Bid candidate = Bid.makeBid(bidSize, trump);
			if (!auction.isValid(candidate)) {
				view.getBiddingDisplay().display("Invalid bid");
				return;
			}
			auction.bid(candidate);
			view.getBiddingDisplay().display("Bid placed:" + candidate);
			doAutomatedBidding();
		}
		if (auction.biddingFinished()) {
			view.getBiddingDisplay().display(
					"BIDDING COMPLETE. High bid: " + auction.getHighBid());
		}
	}

	public void playGame() {
		view.getBiddingDisplay().display("Play game not implemented");
//		game2 = new Game(auction.getHighBid().getTrump());
//		Direction dummyDir = auction.getDummy();
//		
//		game2.getNorth().init(game.getPlayer(dummyDir.getValue()).getHand());
//		Direction east = Direction.clockwise(dummyDir);
//		game2.getEAST().init(game.getPlayer(east.getValue()).getHand());
//		Direction south = Direction.clockwise(east);
//		game2.getSouth().init(game.getPlayer(south.getValue()).getHand());
//		Direction west = Direction.clockwise(south);
//		game2.getSouth().init(game.getPlayer(west.getValue()).getHand());
//		game2.setNextToPlay(west.getValue());
//		view.setGame(game2);
//		doAutomatedPlay();
	}

}
