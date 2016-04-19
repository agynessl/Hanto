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
import hanto.studentqliao.common.HantoCoordinateImpl;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Mar 29, 2016
 * @author Qiaoyu Liao
 */
public class BetaHantoMasterTest
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
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
	}
	
	/**
	 * Blue side place the first piece Butterfly on (0,0)
	 * test should successfully pass, the piece at (0,0) is Blue Butterfly
	 * @throws HantoException
	 */
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * Blue side place the Sparrow as the first piece on (0,0)
	 * test should successfully pass, the piece at (0,0) is Blue Sparrow
	 * @throws HantoException
	 */
	@Test // 2
	public void bluePlaceIntialSparrowAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Blue side place the first piece on place other than (0,0)
	 * throw HantoException
	 */
	@Test // 3
	public void bluePlaceIntialbadCoordinates()
	{
		try{
			game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * Red side place the initial piece butterfly at (0,1) after Blue side move
	 * the piece at (0,1) should be red butterfly
	 * @throws HantoException
	 */
	@Test // 4
	public void redPlaceIntialButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * Red side place the initial piece sparrow at (0,1) after Blue side move
	 * the pieces at (0,1) should be red sparrow
	 * @throws HantoException
	 */
	@Test // 5
	public void redPlaceIntialSparrow() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * blue side places crab while playing
	 * throw HantoException
	 */
	@Test // 6
	public void bluePlaceCrab()
	{
		try{
			game.makeMove(CRAB, null, makeCoordinate(0, 0));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * red side places crab while playing
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 7
	public void redPlaceCrab() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		try{
			game.makeMove(CRAB, null, makeCoordinate(0, 1));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * blue side try to move the pieces on board
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 8
	public void blueMovesButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		try{
			game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * red side try to move the pieces on board
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 9
	public void redMovesButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		try{
			game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(1, 0));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * blue side place the piece at a hex not adjacent to pieces already on the board
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 10
	public void bluePlaceNotAdjacentAfterInitial() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		try{
			game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * red side place the piece at a hex not adjacent to pieces already on the board
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 11
	public void redPlaceNotAdjacentAfterInitial() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		try{
			game.makeMove(BUTTERFLY, null, makeCoordinate(0, 2));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * blue side place the piece at an already taken hex
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 12
	public void bluePlaceOnOccupiedSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		try{
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * red side place the piece at an already taken hex
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 13
	public void redPlaceOnOccupiedSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		try{
			game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * blue side place the piece at a valid hex
	 * move result should be ok, the piece at the last spot should be blue sparrow
	 * @throws HantoException
	 */
	@Test // 14
	public void bluePlaceOnValidSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 2));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * red side place the piece at a valid hex
	 * move result should be ok, the piece at the lst spot should be red sparrow
	 * @throws HantoException
	 */
	@Test // 15
	public void redPlaceOnValidSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -2));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, -2));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * blue side play the first four moves without butterfly
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 16
	public void blueFourthMoveWithoutButterfly() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		try{
			game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * red side play the first four moves without butterfly
	 * throw HantoException
	 * @throws HantoException
	 */
	@Test // 17
	public void redForthMoveWithoutButterfly() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		try{
			game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * blue and red side both play the first four turn with butterfly
	 * check the last pieces put on board
	 * @throws HantoException
	 */
	@Test // 18
	public void blueAndRedButteflyBeforeFourth() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * blue side play butterfly twice
	 * throws HantoException
	 * @throws HantoException
	 */
	@Test // 19
	public void blueButterflyOccurTwice() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		try{
			game.makeMove(BUTTERFLY, null, makeCoordinate(2, -1));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * red side play butterfly twice
	 * throws HantoException
	 * @throws HantoException
	 */
	@Test // 20
	public void redButterflyOccurTwice() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		try{
			game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 2));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
	}
	
	/**
	 * game finished with all 6 turns played but no winner
	 * move result will be DRAW
	 * @throws HantoException
	 */
	@Test // 21
	public void gameReachesEndWithoutWinner() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(3, -1));
		assertEquals(DRAW, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(3, -1));
		assertEquals(RED, p.getColor());
	}
	
	/**
	 * move after game ends
	 * throws exception
	 * @throws HantoException
	 */
	@Test // 22
	public void moveAftergameEnds() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 2));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 1));
		game.makeMove(SPARROW, null, makeCoordinate(3, -1));
		
		try{
			game.makeMove(SPARROW, null, makeCoordinate(3, -2));
		}
		catch(Throwable t){
			assertSame(HantoException.class, t.getClass());
		}
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
	
	/**
	 * at the 4th turn, blue butterfly drop and immediately surrounded by pieces
	 * move result should be RED_WINS
	 * @throws HantoException
	 */
	@Test // 26
	public void RedWinWhenBlueButterflyDrop() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		assertEquals(RED_WINS, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * test the getPieceAt in BetaHantoGame
	 */
	@Test // 27
	public void testGetPieceNull()
	{
		assertNull(game.getPieceAt(makeCoordinate(0, 0)));
	}
	
	/**
	 * test the equals in HantoCoordinateImpl
	 */
	@Test // 28
	public void testHantoCoordinateImplEquals()
	{
		final HantoCoordinateImpl coor = new HantoCoordinateImpl(makeCoordinate(0, 0));
		final HantoCoordinate coor2 = makeCoordinate(0, 0);
		assertTrue(coor.equals(coor2));
	}
	
	/**
	 * test getPrintableBoard in BetaHantoGame
	 * @throws HantoException
	 */
	@Test // 29
	public void testGetPrintableBoard() throws HantoException
	{
		assertEquals(game.getPrintableBoard(), "0");
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(game.getPrintableBoard(), "4");
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
