package org.gnubridge.search;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.gnubridge.core.Game;

public class PositionLookup {

	Map<String, byte[]> positions;
	private Game lastGameLookedUp;
	private byte[] lastNode;

	public PositionLookup() {
		try {
			positions = new WeakHashMap<String, byte[]>(20000000, 0.5f);
		} catch (OutOfMemoryError e) {
			positions = new WeakHashMap<String, byte[]>();
		}
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
		if (g == lastGameLookedUp) {
			return lastNode;
		}
		byte[] result = positions.get(getHash(g));
		lastGameLookedUp = g;
		lastNode = result;
		return result;
	}

	private void putNode(Game g, byte[] value) {
		positions.put(getHash(g), value);
	}

	private String getHash(Game g) {
		return g.getUniqueString();
	}

}
