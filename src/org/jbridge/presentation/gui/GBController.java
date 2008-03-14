package org.jbridge.presentation.gui;

public class GBController {

	private MainWindow view;

	public GBController(MainWindow view) {
		this.view = view;
	}

	public void placeBid(int bidSize, String trump) {
		view.left().display("Bid placed: "+bidSize+" "+trump);
		
	}

}
