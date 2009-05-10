package org.gnubridge.search;

import org.gnubridge.core.Direction;
import org.gnubridge.core.Game;
import org.gnubridge.core.Player;
import org.gnubridge.core.deck.*;
import org.gnubridge.presentation.GameUtils;


import junit.framework.TestCase;

public class DuplicatePruningAcceptanceTests extends TestCase {
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
		//pruned2.lookup.useEfficientStorage = true;
		pruned2.search();
		pruned2.printStats();
		pruned2 = null;
	}

	public void testEquivalenceScenario1() {
		Game game = new Game(Hearts.i());
		game.getWest().init(Eight.of(Spades.i()), Four.of(Diamonds.i()), Six.of(Diamonds.i()), Three.of(Spades.i()), Five.of(Clubs.i()), Two.of(Spades.i()), Ace.of(Hearts.i()), Nine.of(Spades.i()), Five.of(Diamonds.i()), Three.of(Diamonds.i()), Three.of(Hearts.i()), Nine.of(Hearts.i()));
		game.getNorth().init(Two.of(Diamonds.i()), Four.of(Hearts.i()), Eight.of(Hearts.i()), Queen.of(Clubs.i()), King.of(Hearts.i()), Queen.of(Diamonds.i()), Four.of(Clubs.i()), King.of(Spades.i()), Ace.of(Clubs.i()), King.of(Clubs.i()), Three.of(Clubs.i()), Ace.of(Diamonds.i()));
		game.getEast().init(Six.of(Clubs.i()), Ten.of(Clubs.i()), Seven.of(Hearts.i()), Six.of(Hearts.i()), King.of(Diamonds.i()), Eight.of(Diamonds.i()), Ace.of(Spades.i()), Seven.of(Clubs.i()), Nine.of(Diamonds.i()), Six.of(Spades.i()), Seven.of(Spades.i()), Seven.of(Diamonds.i()));
		game.getSouth().init(Nine.of(Clubs.i()), Jack.of(Clubs.i()), Five.of(Hearts.i()), Five.of(Spades.i()), Four.of(Spades.i()), Two.of(Clubs.i()), Jack.of(Diamonds.i()), Eight.of(Clubs.i()), Ten.of(Diamonds.i()), Ten.of(Spades.i()), Queen.of(Spades.i()), Jack.of(Spades.i()));
		game.setNextToPlay(Direction.EAST);
		
		
		
		Search noPruneDuplicatesSearch = new Search(game.duplicate());
		noPruneDuplicatesSearch.setMaxTricks(4);
		noPruneDuplicatesSearch.setUseDuplicateRemoval(false);
		noPruneDuplicatesSearch.search();
		System.out.println("****** NO Pruned duplicates:");
		noPruneDuplicatesSearch.printStats();
		noPruneDuplicatesSearch.printOptimalPath();
		
		
		Search pruneDuplicatesSearch = new Search(game.duplicate());
		pruneDuplicatesSearch.setMaxTricks(4);
		pruneDuplicatesSearch.setUseDuplicateRemoval(true);
		pruneDuplicatesSearch.search();
		System.out.println("****** Pruned duplicates:");
		pruneDuplicatesSearch.printStats();
		pruneDuplicatesSearch.printOptimalPath();
		assertEquals(noPruneDuplicatesSearch.getRoot().getTricksTaken(Player.WEST_EAST), pruneDuplicatesSearch.getRoot().getTricksTaken(Player.WEST_EAST));
	}
	
	public void zzztestGaugeMaxCardsPlayedDuplicateAt13Cards() {
		for (int cardDeal = 0; cardDeal < 1; cardDeal++) {
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
		System.out.println("Average run times (ms)");
		for (SearchConfiguration config : SearchConfiguration.values()) {
			System.out.println(config+": "+config.getAverageRunningTime());
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
		int type;
		int runCount = 0;
		int totalTimeMillis = 0;
		private SearchConfiguration(int type) {
			this.type = type;
		}
		
		public void runSearch(Game g) {
			search = new Search(g);
			search.setMaxTricks(4);
			if (type == -1) {
				search.setUseDuplicateRemoval(false);
			} else {
				search.setUseDuplicateRemoval(true);
			}
			//search.lookup.maxCardsPlayed = type;
			search.search();
			search.printStats();
			
			runCount++;
			totalTimeMillis += search.getRunningTime();
		}
		
		public double getAverageRunningTime() {
			return totalTimeMillis / runCount;
		}
		
		
		
	}
}
