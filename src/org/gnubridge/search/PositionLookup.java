package org.gnubridge.search;

import java.util.HashMap;
import java.util.Map;

import org.gnubridge.core.Game;


public class PositionLookup {

	Map<String, byte[]> positions;
	public PositionLookup() {
	  positions = new HashMap<String, byte[]>();	
	}
	
	public boolean positionEncountered(Game g, byte[] bs) {
		if (g.getCurrentTrick().getHighestCard() != null) {
			return false;
		}
		byte[] valueToReturn = getNode(g);
		if (valueToReturn == null) {
			byte[] value;
			if (bs == null) {
				value = new byte[2];
			} else {
				value = bs;
			}
			putNode(g, value);
			return false;
		}
		return true;
	}

	public byte[] getNode(Game g) {
		return positions.get(getHash(g));
	}
	
	private void putNode(Game g, byte[] value) {
		positions.put(getHash(g), value);
	}

	private String getHash(Game g) {
		return g.getUniqueString();
	}
	
	
}
