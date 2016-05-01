/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentqliao.delta;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGameFactory;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * Delta Hanto Test
 * @author agyness
 *
 */
public class DeltaHantoGameTest {
	
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
	
	private static HantoTestGameFactory factory;
	private HantoTestGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoTestGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoTestGame(HantoGameID.DELTA_HANTO);
	}	
	
	/**
	 * blue moves sparrow
	 * @throws HantoException
	 */
	@Test
	public void blueMovesSparrowUsingTestGame() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[4];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		
		game.initializeBoard(pieces);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		game.setTurnNumber(3);
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0, -1), makeCoordinate(-1, 0));
		assertEquals(MoveResult.OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
		assertEquals(HantoPlayerColor.BLUE, piece.getColor());
		assertEquals(HantoPieceType.SPARROW, piece.getType());
	}

	/**
	 * Test valid crab walk
	 * @throws HantoException
	 */
	@Test
	public void validCrabWalk() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[4];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));

		game.initializeBoard(pieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.CRAB,
				new TestHantoCoordinate(-1, 1), new TestHantoCoordinate(1, -1)));
	}

	/**
	 * Test invalid crab walk that causes disconnection 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void crabWalkDisconnection() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[6];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));	
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 0));

		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(1, -1), new TestHantoCoordinate(
				2, -2));
	}

	/**
	 * Test invalid crab walk on crab that cannot move
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidCrabWalk() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[6];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));	
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));		
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 2));

		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(
				-1, 0));
	}

	/**
	 * Test valid butterfly walk
	 * @throws HantoException
	 */
	@Test
	public void validButterflyWalk() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[2];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));

		game.initializeBoard(pieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(0, 1)));
	}

	/**
	 * Test invalid butterfly walk that causes disconnection
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidButterflyWalkDisconnection() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[5];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));		
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));		
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));

		game.initializeBoard(pieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1),
				new TestHantoCoordinate(-2, 2));
	}

	/**
	 * Test invalid butterfly walk on butterfly cannot move
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidButterflyWalk() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[9];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -2));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, -2));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));	
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));		
		pieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0));		
		pieces[8] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, 0));
		

		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0),
				new TestHantoCoordinate(2, -1));
	}

	/**
	 * invalid crab fly
	 * @throws HantoExcpetion
	 */
	@Test(expected = HantoException.class)
	public void invalidFlyCrab() throws HantoException {
		
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[2];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));

		game.initializeBoard(pieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(
				0, 2));
	}
	

	/**
	 * invalid butterfly fly
	 * @throws HantoExcpetion
	 */
	@Test(expected = HantoException.class)
	public void invalidFlyButterfly() throws HantoException {
		
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[2];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(pieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(0, 2));
	}

	/**
	 * valid sparrow fly
	 * @throws HantoException
	 */
	@Test
	public void validlyFlySparrow() throws HantoException {
		
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[3];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0));
		
		game.initializeBoard(pieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(0, 2)));
	}

	/**
	 * valid sparrow fly over neighbors
	 * @throws HantoException
	 */
	@Test
	public void validSparrowFlyingOverNeighbors() throws HantoException {
		
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[4];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));

		game.initializeBoard(pieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(2, 0)));
	}

	/**
	 * valid sparrow fly through neighbors
	 * @throws HantoException
	 */
	@Test
	public void validSparrowFlyingThroughNeighbors() throws HantoException {
		
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[4];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 2));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));

		game.initializeBoard(pieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(1, 0)));
	}
	
	/**
	 * valid blue wins by sparrow flying
	 * @throws HantoException
	 */
	@Test
	public void validBlueWinsByFlyingSparrow() throws HantoException {
	
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[8];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 2));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));		
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		pieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, 0));

		game.initializeBoard(pieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.BLUE_WINS, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, -1), new TestHantoCoordinate(-1, 1)));
	}

	/**
	 * valid red win by sparrow flying
	 * @throws HantoException
	 */
	@Test
	public void validRedWinsByFlyingSparrow() throws HantoException {
		
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[7];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));	
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		

		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.RED_WINS, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 2), new TestHantoCoordinate(1, -1)));
	}
	
	/**
	 * place more than 4 crabs
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidPlaceMoreCrabsThanAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[8];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 0));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));	
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));		
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 2));		
		pieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 3));
		
		game.initializeBoard(pieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0, 4));	
	}
	
	
	/**
	 * place more than 4 sparrows
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidPlaceMoreSparrowsThanAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[8];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));		
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));		
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 0));		
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));		
		pieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 3));
		
		game.initializeBoard(pieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 4));	
	}
	
	/**
	 * place more than 1 butterfly
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidPlaceMoreButterflyThanAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[2];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		
		game.initializeBoard(pieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));	
	}
	
	/**
	 * invalid move after win
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void invalidBlueAttemptsToMoveAfterRedWins() throws HantoException {
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[7];

		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		

		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.RED_WINS, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 2), new TestHantoCoordinate(1, -1)));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0, -2));
	}

	
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}

}
