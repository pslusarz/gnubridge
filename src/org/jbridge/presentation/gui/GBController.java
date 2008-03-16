package org.jbridge.presentation.gui;

import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.presentation.GameUtils;

public class GBController {

	private MainWindow view;
	private Auctioneer auction;

	public GBController(MainWindow view) {
		this.view = view;
		auction = new Auctioneer(West.i());
		Game game = new Game(null);
		GameUtils.initializeRandom(game.getPlayers(), 13);
		view.getBiddingDisplay().addCards(new Hand(game.getSouth().getHand()));
		
	}

	public void placeBid(int bidSize, String trump) {
		view.getBiddingDisplay().display("Bid placed: "+bidSize+" "+trump);
		
	}

}
