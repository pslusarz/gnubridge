package org.gnubridge.core.deck;


public class Spades extends Color {

	private static Spades instance;

	private Spades() {
		super();
	}
	
	public static Spades i() {
		if (instance == null) {
			instance = new Spades();
		}
		return instance ;
	}

	@Override
	public String toString() {
		return "SPADES";
	}

}
