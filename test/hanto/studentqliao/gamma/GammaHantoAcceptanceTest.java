package hanto.studentqliao.gamma;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;
import static org.junit.Assert.*;
import org.junit.*;
import hanto.common.*;
import hanto.studentqliao.HantoGameFactory;

public class GammaHantoAcceptanceTest
{
	class MoveData {
		final HantoPieceType type;
		final HantoCoordinate from, to;
		
		private MoveData(HantoPieceType type, HantoCoordinate from, HantoCoordinate to) 
		{
			this.type = type;
			this.from = from;
			this.to = to;
		}
	}
	
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
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, BLUE);
	}
	
	@Test
	public void bluePlacesButterflyFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesSparrowFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}
	
	@Test
	public void blueMovesSparrow() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, BLUE, SPARROW);
	}
	
	@Test(expected=HantoException.class)
	public void moveToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void moveButterflyToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, 0));
	}
	
	@Test(expected=HantoException.class)
	public void moveSparrowToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 0));
	}
	
	@Test(expected=HantoException.class)
	public void moveFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 1, 0, 1, -1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveTooFar() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, -1, 2));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveWrongPieceType() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(BUTTERFLY, 0, -1, -1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveWrongColorPiece() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, 2, 1, 1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveWhenNotEnoughSpace() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToUseTooManyButterflies() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToUseTooManySparrows() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4),
				md(SPARROW, 0, -4), md(SPARROW, 0, 5),
				md(SPARROW, 0, -5), md(SPARROW, 0, 6),
				md(SPARROW, 0, -6));
	}
	
	@Test(expected=HantoException.class)
	public void tryToUsePieceNotInGame() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(CRANE, 0, -1));
	}
	
	@Test
	public void blueWins() throws HantoException
	{
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test
	public void redSelfLoses() throws HantoException
	{
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 1, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(SPARROW, 1, 2, 1, 1));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test(expected=HantoException.class)
	public void tryToPlacePieceNextToOpponent() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, -2, 0));
	}
	
	@Test
	public void drawAfterTwentyTurns() throws HantoException
	{
		MoveResult mr = makeMoves(
				md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, 1, -1), md(SPARROW, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2));
		assertEquals(DRAW, mr);
	}
	
	@Test(expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveByFirstPlayer() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1),
				md(SPARROW, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveBySecondPlayer() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1),
				md(BUTTERFLY, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveAfterGameIsOver() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(SPARROW, 0, 3));
	}
	
	@Test(expected=HantoException.class)
	public void extraCreditMoveSparrowBeforeButterflyIsOnBoard() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md (BUTTERFLY, 0, 1));
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(SPARROW, piece.getType());
		assertEquals(BLUE, piece.getColor());
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
	
	/**
	 * Make sure that the piece at the location is what you expect
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param color piece color expected
	 * @param type piece type expected
	 */
	private void checkPieceAt(int x, int y, HantoPlayerColor color, HantoPieceType type)
	{
		final HantoPiece piece = game.getPieceAt(makeCoordinate(x, y));
		assertEquals(color, piece.getColor());
		assertEquals(type, piece.getType());
	}
	
	/**
	 * Make a MoveData object given the piece type and the x and y coordinates of the
	 * desstination. This creates a move data that will place a piece (source == null)
	 * @param type piece type
	 * @param toX destination x-coordinate
	 * @param toY destination y-coordinate
	 * @return the desitred MoveData object
	 */
	private MoveData md(HantoPieceType type, int toX, int toY) 
	{
		return new MoveData(type, null, makeCoordinate(toX, toY));
	}
	
	private MoveData md(HantoPieceType type, int fromX, int fromY, int toX, int toY)
	{
		return new MoveData(type, makeCoordinate(fromX, fromY), makeCoordinate(toX, toY));
	}
	
	/**
	 * Make the moves specified. If there is no exception, return the move result of
	 * the last move.
	 * @param moves
	 * @return the last move result
	 * @throws HantoException
	 */
	private MoveResult makeMoves(MoveData... moves) throws HantoException
	{
		MoveResult mr = null;
		for (MoveData md : moves) {
			mr = game.makeMove(md.type, md.from, md.to);
		}
		return mr;
	}
}
