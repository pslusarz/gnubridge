package org.gnubridge.core.bidding;

import org.gnubridge.core.Hand;
import org.gnubridge.core.deck.Ace;
import org.gnubridge.core.deck.Clubs;
import org.gnubridge.core.deck.Diamonds;
import org.gnubridge.core.deck.Eight;
import org.gnubridge.core.deck.Five;
import org.gnubridge.core.deck.Four;
import org.gnubridge.core.deck.Hearts;
import org.gnubridge.core.deck.Jack;
import org.gnubridge.core.deck.King;
import org.gnubridge.core.deck.Nine;
import org.gnubridge.core.deck.Queen;
import org.gnubridge.core.deck.Seven;
import org.gnubridge.core.deck.Six;
import org.gnubridge.core.deck.Spades;
import org.gnubridge.core.deck.Ten;
import org.gnubridge.core.deck.Three;
import org.gnubridge.core.deck.Two;

/**
 * adapted from Richard Pavlicek's bridge lessons
 * // http://www.rpbridge.net/ 
 *
 */

public class RPQuizzes {
	public static class Basics {
		public static class Lesson2 {
			public static Hand hand1() {
				return new Hand (
						Ace.of(Spades.i()), Jack.of(Spades.i()), Ten.of(Spades.i()), Six.of(Spades.i()),
						King.of(Hearts.i()), Jack.of(Hearts.i()), Three.of(Hearts.i()),
						Nine.of(Diamonds.i()), Eight.of(Diamonds.i()), Six.of(Diamonds.i()),
						Ace.of(Clubs.i()), King.of(Clubs.i()), Two.of(Clubs.i())
					  );
				
			}
			public static Hand hand2() {
			  return new Hand (
						Ace.of(Spades.i()), Jack.of(Spades.i()), Nine.of(Spades.i()), Seven.of(Spades.i()), Five.of(Spades.i()),
						Three.of(Hearts.i()), Two.of(Hearts.i()),
						King.of(Diamonds.i()), Queen.of(Diamonds.i()), Seven.of(Diamonds.i()), Five.of(Diamonds.i()),
						Four.of(Clubs.i()), Two.of(Clubs.i())
					  );	
			}
			public static Hand hand3() {
				return new Hand ("A,K,4,3", "K,J,9,2", "6,2","Q,9,7" );
			}
			public static Hand hand4() {
				return new Hand ("Q,10", "3", "Q,J,8,6,5","A,K,J,8,2" );
			}
			public static Hand hand5() {
				return new Hand ("K,2", "A,Q,3", "A,8,6,5,3","K,J,3" );
			}
			public static Hand hand6() {
				return new Hand ("A,J,9,7,5", "K,J,10,7", "A,3","K,2" );
			}
			public static Hand hand7() {
				return new Hand ("A,K,4,3", "A,K,J,2", "2","K,9,8,2" );
			}
			public static Hand hand8() {
				return new Hand ("A,K,5,4,3", "2", "A,J,9,7,5,4","3" );
			}
			public static Hand hand9() {
				return new Hand ("A,K,3", "K,J,8", "A,K,6","J,10,9,6" );
			}
			public static Hand hand10() {
				return new Hand ("K,9,8,7,6", "A,Q,J,9,5", "10,7,5" );
			}
			public static Hand hand11() {
				return new Hand ("A,Q,8", "J,8,7,6,4", "A,Q","K,J,6" );
			}
			public static Hand hand12() {
				return new Hand ("A,K,10,2", "J,6,4", "A,Q,8","9,6,4" );
			}

		}
	}

}
