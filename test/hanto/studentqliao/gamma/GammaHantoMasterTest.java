/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentqliao.gamma;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentqliao.HantoGameFactory;
import hanto.studentqliao.common.HantoCoordinateImpl;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Apr 7, 2016
 * @author Qiaoyu Liao
 */
public class GammaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		/**
		 * TestHantoCoordinate Constructor
		 * @param x coordinate
		 * @param y coordinate
		 */
		TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	
	private static HantoGameFactory factory = HantoGameFactory.getInstance();
	private HantoGame game;
	
	
	/**
	 *  initialize the factory 
	 */
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	/**
	 * setup the beta hanto game
	 */
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO);
	}
	
	/**
	 * Blue side place the first piece Butterfly on (0,0)
	 * test should successfully pass, the piece at (0,0) is Blue Butterfly
	 * @throws HantoException
	 */
	
	
	
	
	
	// 1
		@Test	
		public void bluePlacesInitialButterflyAtOrigin() throws HantoException
		{
			final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(BLUE, p.getColor());
			assertEquals(BUTTERFLY, p.getType());
		}
		
		// 2
		@Test	
		public void bluePlacesInitialSparrowAtOrigin() throws HantoException
		{
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(BLUE, p.getColor());
			assertEquals(SPARROW, p.getType());
		}
		
		// 3
		@Test(expected = HantoException.class)
		public void  bluePlaceInitialButterflyAtNonOrigin() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		}
		
		// 4
		@Test(expected = HantoException.class)
		public void bluePlaceInitialPieceNotButterflyOrSparrow() throws HantoException
		{
			game.makeMove(CRAB, null, makeCoordinate(0,0));
		}
		
		// 5
		@Test	
		public void redPlacesInitialSparrowAtOrigin() throws HantoException
		{
			game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);	// RedFirst
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(RED, p.getColor());
			assertEquals(SPARROW, p.getType());
		}

		// 6
		@Test(expected = HantoException.class)	
		public void validFirstMoveNonAdjacentHexSecondButterflyMove() throws HantoException
		{
			game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
		}
		
		// 7
		@Test(expected = HantoException.class)	
		public void validFirstMoveNonAdjacentHexSecondSparrowMove() throws HantoException
		{
			game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-1, -1));
		}
		
		// 8 
		@Test(expected = HantoException.class)	
		public void blueTriesToPlacePieceOnOccupiedHex() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		}
		
		// 9
		@Test(expected = HantoException.class)
		public void redTriesToPlacePieceOnOccupiedHex() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, 	null, makeCoordinate(0, -1));
		}
		
		// 10
		@Test(expected = HantoException.class)
		public void blueTriesToPlacePieceAdjacentToRedNotBlue() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 0));
		}
		
		// 11
		@Test(expected = HantoException.class)
		public void blueTriesToPlacePieceAdjacentToBothRedAndBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		}
		
		// 12
		@Test(expected = HantoException.class)
		public void blueTriesToPlacePieceNotAdjacentToRedOrBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 1));
		}
		
		// 13
		@Test
		public void blueTriesToPlacePieceAdjacentBlueNotRed() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1));
			final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			assertEquals(OK, mr);
		}
		

		
		// 14
		@Test (expected = HantoException.class)
		public void redTriesToPlacePieceAdjacentToBlueNotRed()throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(2, 0));

		}
		
		// 15
		@Test (expected = HantoException.class)
		public void redTriesToPlacePieceAdjacentToBothBlueAndRed()throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		}
		
		// 16
		@Test(expected = HantoException.class)
		public void redTriesToPlacePieceNotAdjacentToRedOrBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(2, -2));
		}
		
		// 17
		@Test
		public void redTriesToPlacePieceAdjacentRedNotBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(2, -1));
			assertEquals(OK, mr);
		}
		
		// 18
		@Test(expected = HantoException.class)
		public void blueAttemptsToPlaceTwoButterflies() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		}
		
		// 19
		@Test(expected = HantoException.class)
		public void redAttemptsToPlaceTwoButterflies() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));
		}
		
		// 20
		@Test(expected = HantoException.class)
		public void blueDoesNotPlaceButterflyByFourthMove() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
			game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
			game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
			game.makeMove(SPARROW, null, makeCoordinate(2, 0));	// Move 4
		}
		
		// 21
		@Test(expected = HantoException.class)
		public void redDoesNotPlaceButterflyByFourthMove() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
			game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
			game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
			game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));	// Move 4
			game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
		}
		
		// 22
		@Test(expected = HantoException.class)	
		public void blueTriesToMoveNonExistingButterly() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, makeCoordinate(0, -1), makeCoordinate(-1, 0));
		}
		
		// 23
		@Test(expected = HantoException.class)	
		public void redTriesToMoveNonExistingSparrow() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1)); // Move 2
			game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
			game.makeMove(SPARROW, null, makeCoordinate(2, -2)); // Move 3
			game.makeMove(SPARROW, makeCoordinate(0, 2), makeCoordinate(1, 1));
		}
		
		// 24
		@Test	
		public void blueTriesToMoveSparrowAdjacentToRed() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Move 2
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
			final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(-1, 1), makeCoordinate(0, 1)); // Move 3
			assertEquals(mr, OK);
		}
		
		
		// 25
		@Test	
		public void redTriesToMoveButterflyAdjacentToBlue() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Move 2
			final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, 1)); // Move 2
			assertEquals(mr, OK);
		}
		
		// 26
		@Test(expected = HantoException.class)	
		public void blueTriesToMoveRedButterfly() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, -1)); // Move 2
		} 
		
		// 27
		@Test(expected = HantoException.class)	
		public void redTriesToMoveBlueSparrow() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1)); // Move 2
			game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, -1)); 
		}
		
		// 28
		@Test(expected = HantoException.class)	
		public void blueTriesToMoveSparrowBeforePlaceButterfly() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, -1)); // Move 2
		}
		
		// 29
		@Test(expected = HantoException.class)	
		public void redTriesToMoveSparrowBeforePlaceButterfly() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1)); // Move 2
		}
		
		// 30
		@Test(expected = HantoException.class)	
		public void blueTriesToMoveSparrowThroughSingleHexOpening() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(2, -2));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(2, -1));
			game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(-1, 1));
		}	
		
		// 31
		@Test(expected = HantoException.class)	
		public void redTriesToMoveButterflyThroughSingleHexOpening() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1,0));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, makeCoordinate(-1, 0), makeCoordinate(-1, 1));
		}
		
		
		// 32
		@Test(expected = HantoException.class)	
		public void blueTriesToMoveButterflyTwoHex() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
			game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 2));
		}
		
		// 33
		@Test(expected = HantoException.class)	
		public void redTriesToMoveSparrowThreeHex() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1,-1));
			game.makeMove(SPARROW, null, makeCoordinate(0,1));
			game.makeMove(SPARROW, null, makeCoordinate(2,-1));
			game.makeMove(SPARROW, null, makeCoordinate(0,2));
			game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, 1));
		}
		
		@Test(expected = HantoException.class)	
		public void playerTriesToMovePieceViolatingContiguity() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 1));
		}
		
		/**
		 * red butterfly is surrounded and blue side wins
		 * Move result will be BLUE_WINS
		 * @throws HantoException
		 */
		@Test // 23
		public void gameBlueWins() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));
			game.makeMove(SPARROW, null, makeCoordinate(0, 2));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
			game.makeMove(SPARROW, null, makeCoordinate(2, 0));
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
			assertEquals(BLUE_WINS, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(-1, 2));
			assertEquals(BLUE, p.getColor());
		}
		
		/**
		 * blue butterfly is surrounded and blue side wins
		 * Move result will be RED_WINS
		 * @throws HantoException
		 */
		@Test // 24
		public void gameRedWins() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));
			game.makeMove(SPARROW, null, makeCoordinate(0, 2));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
			assertEquals(RED_WINS, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(-1, 2));
			assertEquals(RED, p.getColor());
		}
		
		/**
		 * red and blue butterflies are both surrounded
		 * move result will be DRAW
		 * @throws HantoException
		 */
		@Test // 25
		public void gameBothWinsToDraw() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(2, -1));
			game.makeMove(SPARROW, null, makeCoordinate(2, 0));
			game.makeMove(SPARROW, null, makeCoordinate(1, -1));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			assertEquals(DRAW, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
			assertEquals(RED, p.getColor());
		}
		
		@Test	
		public void checkPrintableBoard() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.getPrintableBoard();
		}
		
		//@Test
		//public void
	
	/**
	 * helper method
	 * @param x
	 * @param y
	 * @return HantoCoordinate created
	 */
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
