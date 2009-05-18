package org.gnubridge.search;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.*;
import org.gnubridge.presentation.GameUtils;


import junit.framework.TestCase;

public class DuplicatePruningAcceptanceTests extends TestCase {
	public void testMakeJUnitHappy() {
		assertTrue(true);
	}
	
	public void zzztestPruningDuplicate13CardsRunOutOfMemory() {
		Game game = new Game(NoTrump.i());
		game.getWest().init(Four.of(Hearts.i()), Ten.of(Diamonds.i()),
				Eight.of(Hearts.i()), Ace.of(Clubs.i()),
				Seven.of(Diamonds.i()), Ten.of(Hearts.i()),
				Seven.of(Hearts.i()), Six.of(Clubs.i()), Nine.of(Clubs.i()),
				Seven.of(Clubs.i()), Queen.of(Diamonds.i()),
				Ace.of(Hearts.i()), Two.of(Hearts.i()));
		game.getNorth().init(Ten.of(Clubs.i()), Nine.of(Spades.i()),
				Six.of(Diamonds.i()), Six.of(Spades.i()),
				Eight.of(Diamonds.i()), Ace.of(Diamonds.i()),
				Queen.of(Hearts.i()), Five.of(Hearts.i()),
				Queen.of(Spades.i()), Two.of(Clubs.i()), Eight.of(Spades.i()),
				King.of(Hearts.i()), Jack.of(Diamonds.i()));
		game.getEast().init(Five.of(Diamonds.i()), Jack.of(Hearts.i()),
				King.of(Diamonds.i()), Two.of(Spades.i()),
				Three.of(Diamonds.i()), Three.of(Hearts.i()),
				Eight.of(Clubs.i()), Nine.of(Hearts.i()),
				Nine.of(Diamonds.i()), King.of(Spades.i()),
				Three.of(Clubs.i()), Queen.of(Clubs.i()), Four.of(Spades.i()));
		game.getSouth().init(Seven.of(Spades.i()), Five.of(Clubs.i()),
				Five.of(Spades.i()), Two.of(Diamonds.i()), Four.of(Clubs.i()),
				Jack.of(Spades.i()), Six.of(Hearts.i()), Four.of(Diamonds.i()),
				Ten.of(Spades.i()), Ace.of(Spades.i()), King.of(Clubs.i()),
				Three.of(Spades.i()), Jack.of(Clubs.i()));
		game.setNextToPlay(Direction.WEST);
		Search pruned2 = new Search(game);
		pruned2.setUseDuplicateRemoval(true);
		pruned2.setMaxTricks(4);
		pruned2.search();
		pruned2.printStats();
		pruned2 = null;
	}
	
	public void zzztestGaugeMaxCardsPlayedDuplicateAt13Cards() {
		for (int cardDeal = 0; cardDeal < 50; cardDeal++) {
			Trump trump = determineTrump(cardDeal);
			Game g = new Game(trump);
			GameUtils.initializeRandom(g, 13);
			System.out.println("*********** DEAL "+cardDeal+" ***********");
			g.playOneTrick();
			g.printHandsDebug();
			g.printHands();
			System.out.println("*********** SEARCHES ***********");
			for (SearchConfiguration config : SearchConfiguration.values()) {
				System.out.println("-----"+config+"---------");
				config.runSearch(g);
				System.out.println("-----------------------");
			}
			System.out.println("*********** END SEARCHES ***********");
		}

		assertAllSearchesFindSameNumberOfTricksTaken();
		
		printAverageRunTimes();
		
		printAverageMemoryUsed();
		
		
	}

	private void printAverageMemoryUsed() {
		System.out.println("Average Memory (Kb) / Max memory");
		for (SearchConfiguration config : SearchConfiguration.values()) {
			System.out.println("  "+config+": "+config.getAverageMemoryUsed()+" / "+ config.getMaxMemoryUsed());
			}
	}

	private void printAverageRunTimes() {
		System.out.println("Average run times (ms)");
		for (SearchConfiguration config : SearchConfiguration.values()) {
			System.out.println("  "+config+": "+config.getAverageRunningTime());
		}
	}

private void assertAllSearchesFindSameNumberOfTricksTaken() {
	SearchConfiguration previousConfig = null;
	for (SearchConfiguration config : SearchConfiguration.values()) {
		if (previousConfig != null) {
			assertTrue("search did not determine who took which tricks", config.getNorthSouthTricks() > -1);
		  assertEquals("all searches should be equivalent", previousConfig.getNorthSouthTricks(), config.getNorthSouthTricks());	
		}
		previousConfig = config;
	}
}
	
	

	private Trump determineTrump(int i) {
		if (i % 5 == 0) {
			return NoTrump.i();
		} else if (i % 5 == 1) {
			return Spades.i();
		} else if (i % 5 == 2) {
			return Hearts.i();
		} else if (i % 5 == 3) {
			return Diamonds.i();
		} else {
			return Clubs.i();
		}
	}
	
	public enum SearchConfiguration {
		NoDuplicatePruning(-1),
		DuplicatePruning(1);
		//DuplicateWith16CardCutoff(16),
		//DuplicateWith18CardCutoff(18),
		//DuplicateWith19CardCutoff(19);
		Search search;
		static final int MAX_TRICKS = 4;
		int type;
		int runCount = 0;
		private int totalTimeMillis = 0;
		private int northSouthTricks = -1;
		private long totalMemoryUsedK = 0;
		private long maxMemoryK = 0;
		private SearchConfiguration(int type) {
			this.type = type;
		}
		
		public void runSearch(Game g) {
			search = new Search(g);
			search.setMaxTricks(MAX_TRICKS);
			if (type == -1) {
				search.setUseDuplicateRemoval(false);
			} else {
				search.setUseDuplicateRemoval(true);
			}
			Runtime.getRuntime().gc();
			long initMemory = Runtime.getRuntime().freeMemory();
			search.search();
			Runtime.getRuntime().gc();
            long memoryUsed = (Runtime.getRuntime().freeMemory() - initMemory) / 1024;
			totalMemoryUsedK  += memoryUsed;	
            if (maxMemoryK < memoryUsed) {
            	maxMemoryK = memoryUsed;
            }
			search.printStats();
			
			runCount++;
			totalTimeMillis += search.getRunningTime();
			this.northSouthTricks = search.getRoot().getTricksTaken(Player.NORTH_SOUTH);
		}
		
		public double getAverageRunningTime() {
			return totalTimeMillis / runCount;
		}
		public long getAverageMemoryUsed() {
			return (long) (totalMemoryUsedK / runCount);
		}
		public long getMaxMemoryUsed() {
			return maxMemoryK;
		}

		public int getNorthSouthTricks() {
			return northSouthTricks;
		}
		
		
		
		
		
	}
}
