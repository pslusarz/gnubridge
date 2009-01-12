package org.gnubridge.core.deck;


public class Clubs extends Color {

	private static Clubs instance; 

	private Clubs() {
		super();
	}
	
	public static Clubs i() {
		if (instance == null) {
			instance = new Clubs();
		}
		return instance ;
	}
	

	@Override
	public String toString() {
		return "CLUBS";
	}

	@Override
	public String toDebugString() {
		return "Clubs.i()";
	}

}
