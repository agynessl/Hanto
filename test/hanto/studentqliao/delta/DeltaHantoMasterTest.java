package hanto.studentqliao.delta;


import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentqliao.HantoGameFactory;
import hanto.studentqliao.common.HantoCoordinateImpl;


import org.junit.*;

public class DeltaHantoMasterTest {

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
	game = factory.makeHantoGame(HantoGameID.DELTA_HANTO);
}


@Test
public void getAnDeltaHantoGameFromTheFactory()
{
	assertTrue(game instanceof DeltaHantoGame);
}

@Test
public void blueResignsImmediately() throws HantoException
{
	// use the factory for coverage
	game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
	assertEquals(RED_WINS, game.makeMove(null, null, null));
}

@Test
public void placeACrab() throws HantoException
{
	assertEquals(OK, game.makeMove(CRAB, null, makeCoordinate(0, 0)));
	final HantoPiece hp = game.getPieceAt(makeCoordinate(0, 0));
	assertEquals(BLUE, hp.getColor());
	assertEquals(CRAB, hp.getType());
}

@Test
public void bluePlacesInitialButterflyAtOrigin() throws HantoException
{
	final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	assertEquals(OK, mr);
	final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
	assertEquals(BLUE, p.getColor());
	assertEquals(BUTTERFLY, p.getType());
}

@Test
public void redMovesFirstAndPlacesButterfly() throws HantoException
{
	game =  factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
	final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	assertEquals(OK, mr);
	HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
	assertEquals(RED, p.getColor());
	assertEquals(BUTTERFLY, p.getType());
}

@Test
public void firstMovePlacesSparrow() throws HantoException
{
	final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	assertEquals(OK, mr);
	final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
	assertEquals(BLUE, p.getColor());
	assertEquals(SPARROW, p.getType());
}

@Test(expected=HantoException.class)
public void placePieceInNonAdjacentPosition() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(SPARROW, null, makeCoordinate(3, -2));
}

@Test(expected=HantoException.class)
public void firstMoveNotAtOrigin() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
}

@Test(expected=HantoException.class)
public void placeTwoButterflies() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
}

@Test(expected=HantoException.class)
public void placePieceOnOccupiedLocation() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(SPARROW, null, makeCoordinate(0, 0));
}


@Test
public void afterFirstMoveBlueButterflyIsAtOrigin() throws HantoException
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
public void bluePlaceButterflyAtWrongLocation()
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

@Test(expected=HantoException.class)
public void tooManySparrows() throws HantoException
{
	makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
			md(SPARROW, 0, -1), md(SPARROW, 0, 2),
			md(SPARROW, 0, -2), md(SPARROW, 0, 3),
			md(SPARROW, 0, -3), md(SPARROW, 0, 4),
			md(SPARROW, 0, -4), md(SPARROW, 0, 5),
			md(SPARROW, 0, -5));
}

@Test(expected=HantoException.class)
public void tooManyCrabs() throws HantoException
{
	makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
			md(CRAB, 0, -1), md(CRAB, 0, 2),
			md(CRAB, 0, -2), md(CRAB, 0, 3),
			md(CRAB, 0, -3), md(CRAB, 0, 4),
			md(CRAB, 0, -4), md(CRAB, 0, 5),
			md(CRAB, 0, -5));
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
	final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	assertEquals(OK, mr);
}

@Test(expected=HantoException.class)
public void PlacePieceOnOccupiedLocation() throws HantoException
{
	game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 0));
}

@Test
public void blueMovesSparrow() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	game.makeMove(SPARROW, null, makeCoordinate(0, -1));
	game.makeMove(SPARROW, null, makeCoordinate(0, 2));
	final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(-1, 0));
	assertEquals(OK, mr);
	final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
	assertEquals(BLUE, piece.getColor());
	assertEquals(SPARROW, piece.getType());
}

@Test(expected=HantoException.class)
public void moveAndDisconnect() throws HantoException
{
	game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
	game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
	game.makeMove(SPARROW, makeCoordinate(0,0), makeCoordinate(0, 2));	// Blue
}

@Test(expected=HantoException.class)
public void moveOpponentPiece() throws HantoException
{
	game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
	game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
	game.makeMove(SPARROW, makeCoordinate(0,-1), makeCoordinate(1, -1));	// Red
}

@Test(expected=HantoException.class)
public void moveWrongType() throws HantoException
{
	game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
	game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
	game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(1, -1));	// Red
}

@Test
public void moveButterfly() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	assertEquals(OK, game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0)));
	final HantoPiece piece = game.getPieceAt(makeCoordinate(1, 0));
	assertEquals(BLUE, piece.getColor());
	assertEquals(BUTTERFLY, piece.getType());
	assertNull(game.getPieceAt(makeCoordinate(0, 0)));
}

@Test(expected=HantoException.class)
public void moveToDisconnectConfiguration() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, -1));
}

@Test(expected=HantoException.class)
public void moveAPieceFromAnEmptyHex() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, -1));
}

@Test(expected=HantoException.class)
public void moveWrongPiece() throws HantoException
{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 0));
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
public void redSelfLose() throws HantoException
{
	MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
			md(SPARROW, -1, 0), md(SPARROW, 0, 2),
			md(SPARROW, 1, -1), md(SPARROW, 1, 2),
			md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
			md(SPARROW, -1, 0, -1, 1), md(SPARROW, 1, 2, 1, 1));
	assertEquals(BLUE_WINS, mr);
}

@Test
public void blueResign() throws HantoException{
	MoveResult result = game.makeMove(null, null, null);
	assertEquals(result, MoveResult.RED_WINS);
}

@Test
public void redResign() throws HantoException{
	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	MoveResult result = game.makeMove(null, null, null);
	assertEquals(result, MoveResult.BLUE_WINS);
}

/**
 * test null to
 * @throws HantoException
 */
@Test(expected=HantoException.class) 
public void testNullTo() throws HantoException
{
	game.makeMove(SPARROW, null, null);
}

/**
 * test null to
 * @throws HantoException
 */
@Test(expected=HantoException.class) 
public void testNullPieceType() throws HantoException
{
	game.makeMove(null, null, makeCoordinate(0, 0));
}



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
