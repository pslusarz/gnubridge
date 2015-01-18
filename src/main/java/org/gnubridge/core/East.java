package org.gnubridge.core;

public class East extends Direction {
	private static East instance;

	private East() {
	}
	
    public static East i() {
    	if (instance == null) {
    		instance = new East();
    	} 
    	return instance;
    }
    
	@Override
	public int getValue() {
		return Direction.EAST_DEPRECATED;
	}
}
