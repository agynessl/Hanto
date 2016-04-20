/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentqliao.beta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentqliao.HantoGameFactory;
import org.junit.*;

/**
 * Test cases for Beta Hanto. In general, when I say BLUE I mean the
 * first player and RED for the second player.
 * @version Sep 14, 2014
 */
public class BetaHantoAcceptanceTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
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
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
	}
	
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test	// 2
	public void bluePlacesInitialSparrowAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test	// 3
	public void redPlacesInitialSparrowAtOrigin() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);	// RedFirst
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test	// 4
	public void validFirstAndSecondMove() throws HantoException
	{
		// Blue Butterfly -> (0, 0)
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, mr);
		p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test(expected = HantoException.class)	// 5
	public void validFirstMoveNonAdjacentHexSecondMove() throws HantoException
	{
		game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test(expected = HantoException.class)	// 6
	public void firstMoveIsNotAtOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected = HantoException.class)	// 7
	public void blueAttemptsToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
	}
	
	@Test(expected = HantoException.class)	// 8
	public void redAttemptsToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, 	null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	@Test(expected = HantoException.class)	// 9
	public void blueTriesToPlacePieceOnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 10
	public void redTriesToPlacePieceOnOccupiedHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, 	null, makeCoordinate(0, -1));
	}
	
	@Test(expected = HantoException.class)	// 11
	public void blueDoesNotPlaceButterflyByFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));	// Move 4
	}
	
	@Test(expected = HantoException.class)	// 12
	public void redDoesNotPlaceButterflyByFourthTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));	// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));
	}
	
	@Test	// 13
	public void blueWinsBeforeLastTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		assertEquals(BLUE_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));	// Move 4
	}
	
	@Test	// 14
	public void redSelfLosesBeforeLastTurn() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// Move 4
		assertEquals(BLUE_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));
	}
	
	@Test	// 15
	public void redWinsOnLastTurn() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));		// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		game.makeMove(SPARROW, null, makeCoordinate(0, 5));		// Move 5
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));
		game.makeMove(SPARROW, null, makeCoordinate(0, 7));		// Move 6
		assertEquals(RED_WINS, game.makeMove(SPARROW, null, makeCoordinate(-1,1)));
	}
	
	@Test(expected = HantoException.class)	// 16
	public void moveAfterGameIsOverLessThanSixTurns() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));		// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));		// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,1));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	}
	
	@Test	// 17
	public void drawnGame() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));		// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));		// Move 5
		assertEquals(DRAW, game.makeMove(SPARROW, null, makeCoordinate(-1, 1)));
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
