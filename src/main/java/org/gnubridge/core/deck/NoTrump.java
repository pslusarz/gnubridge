package org.gnubridge.core.deck;


public class NoTrump extends Trump {

	private static NoTrump instance = new NoTrump();

	private NoTrump() {
		super();
	}
	
	public static NoTrump i() {
		return instance ;
	}	

	@Override
	public String toString() {
		return "NT";
	}

	@Override
	public String toDebugString() {
		return "NoTrump.i()";
	}

}
