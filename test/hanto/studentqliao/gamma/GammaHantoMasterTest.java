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
		 * 1 blue place butterfly at origin
		 * @throws HantoException
		 */
		@Test	
		public void bluePlacesInitialButterflyAtOrigin() throws HantoException
		{
			final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(BLUE, p.getColor());
			assertEquals(BUTTERFLY, p.getType());
		}
		
		
		/**	
		 * 2 blue place the sparrow at 0,0
		 * @throws HantoException
		 */
		@Test
		public void bluePlacesInitialSparrowAtOrigin() throws HantoException
		{
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			assertEquals(OK, mr);
			final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
			assertEquals(BLUE, p.getColor());
			assertEquals(SPARROW, p.getType());
		}
		
		
		/**
		 * 3 place the first move at non-origin
		 * @throws HantoException
		 */
		@Test
		public void  bluePlaceInitialButterflyAtNonOrigin() throws HantoException
		{
			try{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 4 place the first sparrow at non-origin
		 * @throws HantoException
		 */
		@Test
		public void bluePlaceInitialPieceNotButterflyOrSparrow() throws HantoException
		{
			
			try{
				game.makeMove(CRAB, null, makeCoordinate(0,0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 5
		 * @throws HantoException
		 */
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

		/**
		 * 6 
		 * @throws HantoException
		 */
		@Test
		public void validFirstMoveNonAdjacentHexSecondButterflyMove() throws HantoException
		{
			game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
			
		}
		
		/**
		 * 7
		 * @throws HantoException
		 */
		@Test
		public void validFirstMoveNonAdjacentHexSecondSparrowMove() throws HantoException
		{
			game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(-1, -1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 8 
		 * @throws HantoException
		 */
		@Test
		public void blueTriesToPlacePieceOnOccupiedHex() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 9
		 * @throws HantoException
		 */
		@Test
		public void redTriesToPlacePieceOnOccupiedHex() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			
			try{
				game.makeMove(SPARROW, 	null, makeCoordinate(0, -1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 10
		 * @throws HantoException
		 */
		@Test
		public void blueTriesToPlacePieceAdjacentToRedNotBlue() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 11
		 * @throws HantoException
		 */
		@Test
		public void blueTriesToPlacePieceAdjacentToBothRedAndBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 12
		 * @throws HantoException
		 */
		@Test
		public void blueTriesToPlacePieceNotAdjacentToRedOrBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 13
		 * @throws HantoException
		 */
		@Test
		public void blueTriesToPlacePieceAdjacentBlueNotRed() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1));
			final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			assertEquals(OK, mr);
		}
		

		
		/**
		 * 14
		 * @throws HantoException
		 */
		@Test 
		public void redTriesToPlacePieceAdjacentToBlueNotRed()throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(2, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}

		}
		
		/**
		 * 15
		 * @throws HantoException
		 */
		@Test 
		public void redTriesToPlacePieceAdjacentToBothBlueAndRed()throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(1, -1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 16
		 * @throws HantoException
		 */
		@Test
		public void redTriesToPlacePieceNotAdjacentToRedOrBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(2, -2));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 17
		 * @throws HantoException
		 */
		@Test
		public void redTriesToPlacePieceAdjacentRedNotBlue() throws HantoException 
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(2, -1));
			assertEquals(OK, mr);
		}
		
		/**
		 * 18
		 * @throws HantoException
		 */
		@Test
		public void blueAttemptsToPlaceTwoButterflies() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 19
		 * @throws HantoException
		 */
		@Test
		public void redAttemptsToPlaceTwoButterflies() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			
			try{
				game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 20
		 * @throws HantoException
		 */
		@Test
		public void blueDoesNotPlaceButterflyByFourthMove() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
			game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
			game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(2, 0));	// Move 4
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 21
		 * @throws HantoException
		 */
		@Test
		public void redDoesNotPlaceButterflyByFourthMove() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
			game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
			game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
			game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));	// Move 4
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(-3, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 22
		 * @throws HantoException
		 */
		@Test	
		public void blueTriesToMoveNonExistingButterly() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(BUTTERFLY, makeCoordinate(0, -1), makeCoordinate(-1, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 23
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveNonExistingSparrow() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1)); // Move 2
			game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
			game.makeMove(SPARROW, null, makeCoordinate(2, -2)); // Move 3
			
			try{
				game.makeMove(SPARROW, makeCoordinate(0, 2), makeCoordinate(1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 24
		 * @throws HantoException
		 */
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
		
		
		/**
		 * 25
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveButterflyAdjacentToBlue() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Move 2
			final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, 1)); // Move 2
			assertEquals(mr, OK);
		}
		
		/**
		 * 26
		 * @throws HantoException
		 */
		@Test	
		public void blueTriesToMoveRedButterfly() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, -1)); // Move 2
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		} 
		
		/**
		 * 27
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveBlueSparrow() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1)); // Move 2
			 
			try{
				game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, -1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 28
		 * @throws HantoException
		 */
		@Test	
		public void blueTriesToMoveSparrowBeforePlaceButterfly() throws HantoException
		{
			game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, -1)); // Move 2
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 29
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveSparrowBeforePlaceButterfly() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1)); // Move 2
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 30
		 * @throws HantoException
		 */
		@Test	
		public void blueTriesToMoveSparrowThroughSingleHexOpening() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(2, -2));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(2, -1));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(-1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}	
		
		/**
		 * 31
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveButterflyThroughSingleHexOpening() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1,0));
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
			
			try{
				game.makeMove(BUTTERFLY, makeCoordinate(-1, 0), makeCoordinate(-1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		
		/**
		 * 32
		 * @throws HantoException
		 */
		@Test	
		public void blueTriesToMoveButterflyTwoHex() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
			
			try{
				game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 2));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 33
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveSparrowThreeHex() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1,-1));
			game.makeMove(SPARROW, null, makeCoordinate(0,1));
			game.makeMove(SPARROW, null, makeCoordinate(2,-1));
			game.makeMove(SPARROW, null, makeCoordinate(0,2));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 34
		 * @throws HantoException
		 */
		@Test	
		public void playerTriesToButterflyPieceViolatingContiguity() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			
			try{
				game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 35
		 * @throws HantoException
		 */
		@Test	
		public void playerTriesToMovePieceViolatingContiguity() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
			
			try{
				game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 36
		 * @throws HantoException
		 */
		@Test	
		public void blueTriesToMoveSparrowToOccupiedLocation() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			game.makeMove(SPARROW, null, makeCoordinate(2, 0));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 37
		 * @throws HantoException
		 */
		@Test	
		public void redTriesToMoveSparrowToOccupiedLocation() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(0, 1), makeCoordinate(0, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 38
		 * @throws HantoException
		 */
		@Test	
		public void playerTriesToMoveSparrowViolatingContiguity() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(0, -1));
			
			try{
				game.makeMove(SPARROW, makeCoordinate(1, 0), makeCoordinate(1, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 39
		 * @throws HantoException
		 */
		@Test 
		public void placeMoreThenLimitedSparrows() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-1, 0));//b1
			game.makeMove(SPARROW, 	null, makeCoordinate(2, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-2, 0));//b2
			game.makeMove(SPARROW, 	null, makeCoordinate(3, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-3, 0));//b3
			game.makeMove(SPARROW, 	null, makeCoordinate(4, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-4, 0));//b4
			game.makeMove(SPARROW, 	null, makeCoordinate(5, 0));
			game.makeMove(SPARROW, null, makeCoordinate(-5, 0));//b5
			game.makeMove(SPARROW, 	null, makeCoordinate(6, 0));
			
			try{
				game.makeMove(SPARROW, null, makeCoordinate(-6, 0));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 40
		 * @throws HantoException
		 */
		@Test
		public void RedMovePieceblueButterflyIsSorroundedAndRedWins() throws HantoException {
	        game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	        game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(1, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	        game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(1, 1), makeCoordinate(0, 1));
	        game.makeMove(SPARROW, makeCoordinate(-2, 1), makeCoordinate(-1, 0));
	        MoveResult mr = game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, -1));
	        assertEquals(mr, RED_WINS);
		}
		
		
		
		/**
		 * 41
		 * @throws HantoException
		 */
		@Test
		public void BlueMovePieceReddButterflyIsSorroundedAndBlueWins() throws HantoException {
	        // Turn 1
	        game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	        game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(1, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	        game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	        MoveResult mr = game.makeMove(SPARROW, makeCoordinate(-1, 1), makeCoordinate(0, 1));
	        assertEquals(mr, BLUE_WINS);
		}
		
		/**
		 * 42
		 * @throws HantoException
		 */
	    @Test
	    public void blueRedDrawGameBothSorroundButterfly() throws HantoException {
	    	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	    	game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	    	game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	    	game.makeMove(SPARROW, null, makeCoordinate(1, 1));
	    	game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
	    	game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	    	game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	    	game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	    	game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
	    	game.makeMove(SPARROW, null, makeCoordinate(2, -2));
	    	game.makeMove(SPARROW, makeCoordinate(-1, 2), makeCoordinate(0, 1));
	    	MoveResult mr = game.makeMove(SPARROW, makeCoordinate(2, -2), makeCoordinate(1, -1));
	        assertEquals(DRAW, mr);
	    }
	    

	    /**
	     * 43
	     * @throws HantoException
	     */
	    @Test
	    public void pieceTypeNotMatchingActualPieceForSparrow() throws HantoException {
	    	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	    	game.makeMove(SPARROW, null, makeCoordinate(1, 0));
	    	
	    	try{
	    		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(0, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
	    }
	   
	    /**
	     * 44
	     * @throws HantoException
	     */
	    @Test
	    public void pieceTypeNotMatchingActualPieceForButterfly() throws HantoException {
	    	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	    	game.makeMove(SPARROW, null, makeCoordinate(1, 0));
	    	game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	    	
	    	try{
	    		game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, 1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
	    }
		
	    /**
	     * 45
	     * @throws HantoException
	     */
	    @Test
	    public void reachFortyTurn() throws HantoException
	    {
	    	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	        game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(1, 1));
	        game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	        game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, null, makeCoordinate(2, 0));//8
	        
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
	        game.makeMove(SPARROW, makeCoordinate(2, 0), makeCoordinate(2, 1));
	        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	        MoveResult mr = game.makeMove(SPARROW, makeCoordinate(2, 1), makeCoordinate(2, 0));
	        assertEquals(DRAW, mr);
	        
	        
	        try{
	        	game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, -1));
			}
			catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
	
	    }
	    
	    
	    /**
	     * 46
	     * @throws HantoException
	     */
		@Test	
		public void checkPrintableBoard() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
			assertNotEquals(game.getPrintableBoard(),"");
		}
		
		/**
		 * 47
		 * @throws HantoException
		 */
		@Test	
		public void checkCoordinateEquals() throws HantoException
		{
			assertFalse( (new HantoCoordinateImpl(0,0)).equals(new HantoCoordinateImpl(0,1)));
			assertFalse( (new HantoCoordinateImpl(0,0)).equals(1));
		}
	
		/**
		 * 48
		 * @throws HantoException
		 */
		@Test	
		public void checkMoveToNull() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	    
	        
	        try{
	        	game.makeMove(SPARROW, null, null);
	        }
	        catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
			
		}
		
		/**
		 * 49
		 * @throws HantoException
		 */
		@Test	
		public void checkPieceTypeNull() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	        game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	        try{
		    	game.makeMove(null, null, makeCoordinate(1, 1));
	        }
	        catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * 50
		 * @throws HantoException
		 */
		@Test	
		public void checkPieceTypeOthers() throws HantoException
		{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	        game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	    	
	    	try{
	    		game.makeMove(CRAB, null, makeCoordinate(1, 1));
	        }
	        catch(Throwable t){
				assertSame(HantoException.class, t.getClass());
			}
		}
		
		/**
		 * test null to
		 * @throws HantoException
		 */
		@Test(expected=HantoException.class) // 29
		public void testNullTo() throws HantoException
		{
			game.makeMove(SPARROW, null, null);
		}
		
		/**
		 * test null to
		 * @throws HantoException
		 */
		@Test(expected=HantoException.class) // 29
		public void testNullPieceType() throws HantoException
		{
			game.makeMove(null, null, makeCoordinate(0, 0));
		}
		
		
		
	
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
