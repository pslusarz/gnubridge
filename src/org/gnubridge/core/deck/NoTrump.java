package org.gnubridge.core.deck;


public class NoTrump extends Trump {

	private static NoTrump instance = new NoTrump();

	private NoTrump() {
		super();
	}
	
	public static NoTrump i() {
		return instance ;
	}	
	
//	@Override
//	public int toInt() {
//		throw new RuntimeException("No Trump has no unicode char");
//	}

	@Override
	public String toString() {
		return "NT";
	}

}
