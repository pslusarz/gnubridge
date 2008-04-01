package org.gnubridge.presentation.gui;

import org.gnubridge.core.Player;

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


	private MainView view;
	private BiddingController biddingController;
	private GameController gameController;

	public GBController(MainView view) {
		this.view = view;
		biddingController = new BiddingController(view.getBiddingView(), this);
	}


	public void playGame() {
		gameController = new GameController(this, biddingController.getAuction(), biddingController.getCardHolder(), biddingController.allowHumanToPlayIfDummy(), view.getPlayView());
	}


	public void gameFinished() {
		view.getPlayView().display("GAME FINISHED. Contract was: "+biddingController.getAuction().getHighBid()+ 
				", declarers took "+gameController.getGame().getTricksTaken(Player.NORTH_SOUTH)+ " tricks.");
	}
	




	

	




}
