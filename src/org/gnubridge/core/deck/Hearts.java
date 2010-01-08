package org.gnubridge.core.deck;


public class Hearts extends Suit {

	private static Hearts instance;

	private Hearts() {
		super();
	}
	
	public static Hearts i() {
		if (instance == null) {
			instance = new Hearts();
		}
		return instance ;
	}

	@Override
	public String toString() {
		return "HEARTS";
	}
	
	@Override
	public String toDebugString() {
		return "Hearts.i()";
	}

}
