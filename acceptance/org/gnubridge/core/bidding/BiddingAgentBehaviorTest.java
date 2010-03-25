package org.gnubridge.core.bidding;

import static org.gnubridge.core.bidding.Bid.*;

/**
 * 
 * contributed by Jonathan Campbell, based on Pavlicek's Online Bridge Basics
 *
 */
public class BiddingAgentBehaviorTest extends BiddingAgentTestCase {

	public void testQuiz2Question1() {
		givenPlayersCards("A,J,10,6", "K,J,3", "9,8,6", "A,K,2");
		//pointcount=16
		expectPlayerToBid(ONE_NOTRUMP);
	}

	public void testQuiz2Question2() {
		givenPlayersCards("A,J,9,7,5", "3,2", "K,Q,7,5", "4,2");
		//pointcount=12
		expectPlayerToBid(PASS);
	}

	public void testQuiz2Question3() {
		givenPlayersCards("A,K,4,3", "K,J,9,2", "6,2", "Q,9,7");
		//pointcount=14
		expectPlayerToBid(ONE_CLUBS);
	}

	public void testQuiz2Question4() {
		givenPlayersCards("Q,10", "3", "Q,J,8,6,5", "A,K,J,8,2");
		//pointcount=15
		expectPlayerToBid(ONE_DIAMONDS);
	}

	public void testQuiz2Question5() {
		givenPlayersCards("K,2", "A,Q,3", "A,8,6,5,3", "K,J,3");
		//pointcount=17
		expectPlayerToBid(ONE_NOTRUMP);
	}

	public void testQuiz2Question6() {
		givenPlayersCards("A,J,9,7,5", "K,J,10,7", "A,3", "K,2");
		//pointcount=18
		expectPlayerToBid(ONE_SPADES);
	}

	public void testQuiz2Question7() {
		givenPlayersCards("A,K,4,3", "A,K,J,2", "2", "K,9,8,2");
		//pointcount=20
		expectPlayerToBid(ONE_CLUBS);
	}

	public void testQuiz2Question8() {
		givenPlayersCards("A,K,5,4,3", "2", "A,J,9,7,5,4", "3");
		//pointcount=16
		expectPlayerToBid(ONE_DIAMONDS);
	}

	public void testQuiz2Question9() {
		givenPlayersCards("A,K,3", "K,J,8", "A,K,6", "J,10,9,6");
		//pointcount=19
		expectPlayerToBid(ONE_CLUBS);
	}

	public void testQuiz2Question10() {
		givenPlayersCards("K,9,8,7,6", "A,Q,J,9,5", "10,7,5", "");
		//pointcount=13
		expectPlayerToBid(ONE_SPADES);
	}

	public void testQuiz2Question11() {
		givenPlayersCards("A,Q,8", "J,8,7,6,4", "A,Q", "K,J,6");
		//pointcount=17
		expectPlayerToBid(ONE_NOTRUMP);
	}

	public void testQuiz2Question12() {
		givenPlayersCards("A,K,10,2", "J,6,4", "A,Q,8", "9,6,4");
		//pointcount=14
		expectPlayerToBid(ONE_DIAMONDS);
	}
}
