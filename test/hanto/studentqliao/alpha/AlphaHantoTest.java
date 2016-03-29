/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentqliao.alpha;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;
import static org.junit.Assert.*;
import org.junit.*;
import hanto.common.*;
import hanto.studentqliao.alpha.AlphaHantoGame;

/**
 * Test cases for Alpha Hanto
 * @version Mar 2, 2016
 */
public class AlphaHantoTest
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
	
	private AlphaHantoGame game;
	
	@Before
	public void setup() {
		game = new AlphaHantoGame();
	}
	
	@Test	// 1
	public void blueMakesValidFirstMove() throws HantoException
	{
		assertEquals(OK, game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0)));
	}
	
	@Test	// 2
	public void redMakesValidMove() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		assertEquals(DRAW, game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 1)));
	}
	
	@Test(expected = HantoException.class)	// 3
	public void blueMovesToNonOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 1));
	}
	
	@Test(expected = HantoException.class)	// 4
	public void blueTriesToPlaceSparrow() throws HantoException
	{
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 5
	public void redTriesToPlaceCrab() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(CRAB, null, new TestHantoCoordinate(0, 0));
	}

	@Test(expected = HantoException.class)	// 6
	public void redPlacesButterflyAtInvalidLocation() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(2, 0));
	}
	
	@Test(expected = HantoException.class) // 7
	public void redPlacesButterflyAtOrigin() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
	}
	
	@Test 	// 8
	public void blueButterflyIsAtOrginAfterMove() throws HantoException
	{
		assertNull(game.getPieceAt(new TestHantoCoordinate(0, 0)));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece butterfly = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(BUTTERFLY, butterfly.getType());
		assertEquals(BLUE, butterfly.getColor());
	}
	
	@Test	// 9
	public void redButterflyIsAtCorrectPlaceAfterRedMoves() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(1, -1));
		final HantoPiece butterfly = game.getPieceAt(new TestHantoCoordinate(1, -1));
		assertEquals(BUTTERFLY, butterfly.getType());
		assertEquals(RED, butterfly.getColor());
	}
	
	@Test(expected = HantoException.class)	// 10
	public void attemptToMoveAfterGameEnds() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(1, -1));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(1, 0));
	}
}
