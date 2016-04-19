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
	
	@Test
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
	}
	
	@Test
	public void redMovesFirstAndPlacesButterfly() throws HantoException
	{
		game =  factory.makeHantoTestGame(HantoGameID.DELTA_HANTO, HantoPlayerColor.RED);
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
		HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(HantoPlayerColor.RED, p.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
	}
	
	@Test
	public void firstMovePlacesSparrow() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.SPARROW, p.getType());
	}
	
	@Test(expected=HantoException.class)
	public void placePieceInNonAdjacentPosition() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(3, -2));
	}
	
	@Test(expected=HantoException.class)
	public void firstMoveNotAt0_0() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveAndDisconnect() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0,0), makeCoordinate(0, 2));	// Blue
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveOpponentPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0,-1), makeCoordinate(1, -1));	// Red
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveWrongType() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(0,0), makeCoordinate(1, -1));	// Red
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlacePieceOnOccupiedLocation() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test
	public void redWins() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[7];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 0));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(1, 0));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.SPARROW, new TestHantoCoordinate(1, -2));
		game.initializeBoard(pieces);
		game.setTurnNumber(10);
		game.setPlayerMoving(HantoPlayerColor.RED);
		MoveResult result =game.makeMove(HantoPieceType.SPARROW, makeCoordinate(1, -2), makeCoordinate(0, -1));
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	@Test
	public void blueWins() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[7];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 0));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(1, 0));
		pieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));
		pieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(1, -2));
		game.initializeBoard(pieces);
		game.setTurnNumber(10);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		MoveResult result =game.makeMove(HantoPieceType.SPARROW, makeCoordinate(1, -2), makeCoordinate(0, -1));
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	
	public void attemptFly() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 1));
		MoveResult result = game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 1));
		assertEquals(result, MoveResult.OK);
	}
	

	@Test
	public void getAnDeltaHantoGameFromTheFactory()
	{
		assertTrue(game instanceof DeltaHantoTestGame);
	}
	
	@Test
	public void blueMakesValidFirstMove() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}
	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
	
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
	}

	@Test(expected = HantoException.class)
	public void PlacesUnsupportedPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.HORSE, null, new TestHantoCoordinate(0, 0));
	}

	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	@Test(expected = HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation()
			throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}

	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly()
			throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}
		
	@Test(expected = HantoException.class)
	public void twoPieceOnSameCoordinate() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)
	public void PieceNumberCheckSparrow() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[5];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 3));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 4));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.SPARROW, new TestHantoCoordinate(0, 5));
		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 6));
	}
	
	@Test(expected = HantoException.class)
	public void PieceNumberCheckCrab() throws HantoException
	{
		HantoTestGame.PieceLocationPair[] pieces = new HantoTestGame.PieceLocationPair[5];
		pieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		pieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new TestHantoCoordinate(0, 2));
		pieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new TestHantoCoordinate(0, 3));
		pieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new TestHantoCoordinate(0, 4));
		pieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new TestHantoCoordinate(0, 5));
		game.initializeBoard(pieces);
		game.setTurnNumber(4);
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, 6));
	}
	
	@Test
	public void bluePlacesButterflyFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, piece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesSparrowFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}
	
	@Test
	public void blueMovesSparrow() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 2));
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0, -1), makeCoordinate(-1, 0));
		assertEquals(MoveResult.OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
		assertEquals(HantoPlayerColor.BLUE, piece.getColor());
		assertEquals(HantoPieceType.SPARROW, piece.getType());
	}
	
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
	
	@Test
	public void moveButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0)));
		final HantoPiece piece = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(HantoPlayerColor.BLUE, piece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, piece.getType());
		assertNull(game.getPieceAt(makeCoordinate(0, 0)));
	}
	
	@Test(expected=HantoException.class)
	public void moveToDisconnectConfiguration() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveAPieceFromAnEmptyHex() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveWrongPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 0));
	}
	
	@Test
	public void blueResign() throws HantoException{
		MoveResult result = game.makeMove(null, null, null);
		assertEquals(result, MoveResult.RED_WINS);
	}
	
	@Test
	public void redResign() throws HantoException{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		MoveResult result = game.makeMove(null, null, null);
		assertEquals(result, MoveResult.BLUE_WINS);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 那啥 林学长的
	 */
	
	/**
	 * Test valid crab walk
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testValidCrabWalkAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.CRAB,
				new TestHantoCoordinate(1, 0), new TestHantoCoordinate(1, -1)));
	}

	/**
	 * Test invalid crab walk that causes disconnection between the pieces.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testInvalidCrabWalkDisconnection() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[6];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 0));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(1, -1), new TestHantoCoordinate(
				2, -2));
	}

	/**
	 * Test invalid crab walk on crab that cannot move.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testInvalidCrabWalkAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[6];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(
				-1, 0));
	}

	/**
	 * Test valid butterfly walk
	 * 
	 * @throws HantoException
	 */
	@Test
	public void testValidButterflyWalkAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(0, 1)));
	}

	/**
	 * Test invalid butterfly walk that causes disconnection between the pieces.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testInvalidButterflyWalkDisconnection() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[5];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 1),
				new TestHantoCoordinate(-2, 2));
	}

	/**
	 * Test invalid butterfly walk on butterfly that cannot move.
	 * 
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testInvalidButterflyWalkAttempts() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[9];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, -2));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, 0));
		initialPieces[8] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, -2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.RED);

		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0),
				new TestHantoCoordinate(2, -1));
	}

	/**
	 * Test that attempts to make crab fly
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToFlyCrab() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(
				0, 2));
	}

	/**
	 * Test that attempts to make butterfly fly
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToFlyButterfly() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(0, 2));
	}

	/**
	 * Test that validly flies a sparrow
	 */
	@Test
	public void testValidlyFlySparrow() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[3];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0));
		
		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(0, 2)));

	}

	/**
	 * Test for blue player resigning
	 */
	@Test
	public void testBluePlayerResign() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// making moves
		assertEquals(MoveResult.RED_WINS, game.makeMove(null, null, null));
	}

	/**
	 * Test for red player resigning
	 */
	@Test
	public void testRedPlayerResign() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.RED);
		
		// making moves
		assertEquals(MoveResult.BLUE_WINS, game.makeMove(null, null, null));
	}

	/**
	 * Test for sparrow flying over neighbors
	 */
	@Test
	public void testSparrowFlyingOverNeighbors() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1, 0));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(2, 0)));
	}

	/**
	 * Test for sparrow flying through neighbors
	 */
	@Test
	public void testSparrowFlyingThroughNeighbors() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1, 2));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, -1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(1, 0)));
	}

	/**
	 * Test for blue player winning the game by flying a sparrow to surround the red butterfly
	 */
	@Test
	public void testBlueWinsByFlyingSparrow() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[8];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 2));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(2, 0));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		assertEquals(MoveResult.BLUE_WINS, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, -1), new TestHantoCoordinate(-1, 1)));
	}

	/**
	 * Test for red player winning the game by flying a sparrow to surround the blue butterfly
	 */
	@Test
	public void testRedWinsByFlyingSparrow() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[7];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.RED);

		assertEquals(MoveResult.RED_WINS, game.makeMove(HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 2), new TestHantoCoordinate(1, -1)));
	}
	
	/**
	 * Test to make sure that players are not allowed to place more pieces than what the game allows
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceMoreCrabsThanAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[8];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 2));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.CRAB, new TestHantoCoordinate(-1, 0));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(0, 3));
		
		game.initializeBoard(initialPieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0, 4));	
	}
	
	/**
	 * Test to make sure that players are not allowed to place more pieces than what the game allows
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceMoreSparrowsThanAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[8];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(1, 0));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 3));
		
		game.initializeBoard(initialPieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 4));	
	}
	
	/**
	 * Test to make sure that players are not allowed to place more pieces than what the game allows
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToPlaceMoreButterflyThanAllowed() throws HantoException {
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		
		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));	
	}
	
	/**
	 * Test for blue player attempts to move after red wins
	 */
	@Test(expected = HantoException.class)
	public void testBlueAttemptsToMoveAfterRedWins() throws HantoException {
		// initial test hanto game
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[7];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.CRAB, new TestHantoCoordinate(1, 0));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
				HantoPieceType.SPARROW, new TestHantoCoordinate(0, 2));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE,
				HantoPieceType.SPARROW, new TestHantoCoordinate(-1, 0));

		game.initializeBoard(initialPieces);
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
