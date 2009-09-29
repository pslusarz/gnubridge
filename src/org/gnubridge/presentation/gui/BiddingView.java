package org.gnubridge.presentation.gui;

import org.gnubridge.core.Hand;
import org.gnubridge.core.bidding.Auctioneer;

public interface BiddingView {

	public abstract void setCards(Hand hand);

	public abstract void setAuction(Auctioneer auction);

	public abstract void auctionStateChanged();

	public abstract void display(String msg);

	public abstract void hide();

	public abstract void show();

	public abstract void setController(BiddingController c);

}