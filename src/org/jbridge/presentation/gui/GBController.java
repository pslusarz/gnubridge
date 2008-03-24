package org.jbridge.presentation.gui;

import java.util.List;

import javax.swing.SwingWorker;

import org.gnubridge.core.Card;
import org.gnubridge.core.Direction;
import org.gnubridge.core.East;
import org.gnubridge.core.Game;
import org.gnubridge.core.Hand;
import org.gnubridge.core.North;
import org.gnubridge.core.Player;
import org.gnubridge.core.South;
import org.gnubridge.core.West;
import org.gnubridge.core.bidding.Auctioneer;
import org.gnubridge.core.bidding.Bid;
import org.gnubridge.core.bidding.BiddingAgent;
import org.gnubridge.core.deck.NoTrump;
import org.gnubridge.core.deck.Trump;
import org.gnubridge.presentation.GameUtils;
import org.gnubridge.search.Search;

public class GBController {

//	public class SearchWorker extends SwingWorker<Void, String> {
//		int seconds = 0;
//
//		@Override
//		protected Void doInBackground() throws Exception {
//			for (; seconds < 30; seconds++) {
//				Thread.sleep(1000);
//				publish("Seconds: " + seconds);
//			}
//			return null;
//		}
//
//		@Override
//		protected void process(List<String> messages) {
//			view.display(messages.get(messages.size() - 1));
//		}
//
//		@Override
//		public void done() {
//			view.display("Search finished.");
//		}
//
//	}
	public class SearchWorker extends SwingWorker<Void, String> {
		Search search;
		@Override
		protected Void doInBackground() throws Exception {
			search = new Search(game);
			search.setMaxTricks(2);
			search.search();
			return null;
		}
		
		@Override
		public void done() {
			playCard(search.getBestMoves().get(0));
		}
		
	}

	private MainWindow view;
	private Auctioneer auction;
	Player human;
	Game holdPlayerCards;
	private Game game;
	private Direction humanDirection;

	public GBController(MainWindow view) {
		this.view = view;
		auction = new Auctioneer(West.i());
		holdPlayerCards = new Game(null);
		GameUtils.initializeRandom(holdPlayerCards.getPlayers(), 13);
		human = holdPlayerCards.getSouth();
		view.setCards(new Hand(human.getHand()));
		view.setAuction(auction);
		doAutomatedBidding();
		// fake bidding to get to the other page
		//		auction.bid(new Bid(7, NoTrump.i()));
		//		doAutomatedBidding();
		//        playGame();
	}

	private void doAutomatedBidding() {
		while (!auction.biddingFinished()
				&& !auction.getNextToBid().equals(human.getDirection2())) {
			Hand hand = new Hand(holdPlayerCards.getPlayer(
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
			view.auctionStateChanged();
			doAutomatedBidding();
		}
		if (auction.biddingFinished()) {
			view.getBiddingDisplay().display(
					"BIDDING COMPLETE. High bid: " + auction.getHighBid());
		}
	}

	public void playGame() {
		//view.getBiddingDisplay().display("Play game not implemented");
		game = makeGame(auction, holdPlayerCards);

		humanDirection = allowHumanToPlayIfDummy();
		view.setGame(game, humanDirection);
		doAutomatedPlay();
	}
	
	public void playCard(Card c) {
	  System.out.println("Playing: "+c);
	  game.play(c);
	  view.gameStateChanged();
	  doAutomatedPlay();
	}

	private void doAutomatedPlay() {
		if (humanHasMove(humanDirection, game)) {
			System.out.println("Human has move");
			return;

		}
		System.out.println("Computer has move: "+game.getNextToPlay());
		SearchWorker w = new SearchWorker();
		w.execute();

	}

	private boolean humanHasMove(Direction player, Game g) {
		Direction nextToMove = g.getNextToPlay().getDirection2();
		if (player.equals(nextToMove)
				|| (player.equals(South.i()) && nextToMove.equals(North.i()))) {
			return true;
		} else {
			return false;
		}
	}

	private Direction allowHumanToPlayIfDummy() {
		Direction newHuman = auction.getDummyOffsetDirection(human
				.getDirection2());
		if (North.i().equals(newHuman)) {
			newHuman = South.i();
		}
		return newHuman;
	}

	private Game makeGame(Auctioneer a, Game cardHolder) {
		Game result = new Game(a.getHighBid().getTrump());

		result.getPlayer(a.getDummyOffsetDirection(North.i())).init(
				cardHolder.getPlayer(North.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(East.i())).init(
				cardHolder.getPlayer(East.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(South.i())).init(
				cardHolder.getPlayer(South.i()).getHand());
		result.getPlayer(a.getDummyOffsetDirection(West.i())).init(
				cardHolder.getPlayer(West.i()).getHand());
		result.setNextToPlay(West.i().getValue());
		return result;
	}

}
