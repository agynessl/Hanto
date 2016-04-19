package hanto.studentqliao.common;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class HantoGameBase implements HantoGame {
	protected HantoBoard board;
	//protected MoveValidator walkValidator;
	
	protected int moveCounter;
	protected boolean gameOver;

	protected boolean firstMove;
	protected HantoPlayerColor onMove, movesFirst;
	protected boolean blueWins, redWins;
	protected int MAX_TURN;
	
	protected HantoCoordinateImpl blueButterflyCoor;
	protected HantoCoordinateImpl redButterflyCoor;
	
	protected abstract  MoveValidator getMoveValidator(HantoPieceType type);
	
	public HantoGameBase(){
		onMove = this.movesFirst = BLUE;
		board = new HantoBoard();
		moveCounter = 1;
		gameOver = false;
		setFirstMove(true);
		blueButterflyCoor = redButterflyCoor = null;
		blueWins = redWins = false;
		//walkValidator = new ButterflyValidator();
		MAX_TURN = Integer.MAX_VALUE;
	}
	/**
	 * 
	 * @param movesFirst
	 */
	public HantoGameBase(HantoPlayerColor movesFirst){
		onMove = this.movesFirst = movesFirst;
		board = new HantoBoard();
		moveCounter = 1;
		gameOver = false;
		setFirstMove(true);
		blueButterflyCoor = redButterflyCoor = null;
		blueWins = redWins = false;
		//walkValidator = new ButterflyValidator();
		MAX_TURN = Integer.MAX_VALUE;
	}
	
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		//game end check
		checkGameEnd();		
		//move check
		validateMove(pieceType,from,to);
		//make the move
		doMove(pieceType,from,to);
		//increment the count
		incrementMove();
		//return result
		return checkResult();
	}
	
	
	/**
	 * 
	 * @throws HantoException
	 */
	protected void checkGameEnd() throws HantoException{
		if(gameOver){
			throw new HantoException("game ends");
		}
	}
	
	/**
	 * 
	 * @throws HantoException
	 */
	private void checkButterflyMovesByFourthMove() throws HantoException
	{
		if (moveCounter == 4) {
			final boolean butterflyUsed = onMove == BLUE ? (blueButterflyCoor != null)
					: (redButterflyCoor != null);
			if (!butterflyUsed) {
				throw new HantoException("You must place Butterfly by the fourth turn. You lose");
			}
		}
	}
	
	/**
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 */
	protected void validateMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		
		if(to == null){
			throw new HantoException("need a valid destination for the piece");
		}
		
		if(pieceType == null){
			throw new HantoException("need a valid piece type");
		}
		
		if(pieceType != BUTTERFLY) {
			checkButterflyMovesByFourthMove(); 
		}
		if(isFirstMove()){
			firstMoveValidator(pieceType, from, to);
		}
		else{
			MoveValidator validator = getMoveValidator(pieceType);
			if(validator == null){
				throw new HantoException("piece type not in this game");
			}
			validator.canMove(board, from, to, onMove, pieceType);
		}

	}
	
	/**
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	private void firstMoveValidator(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		pieceTypeChecker(pieceType);		
		if(onMove == movesFirst){
			if(from != null || to.getX() != 0 || to.getY() != 0){
				throw new HantoException("First move should be put on origin");
			}
		}
		else{
			HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
			HantoCoordinateImpl dest  = new HantoCoordinateImpl(to);
			if(from != null || !(origin.getNeighbors().contains(dest))){
				throw new HantoException("Second move should be put adjacent to origin");
			}
		}		
	}
	
	/**
	 * 
	 * @param type
	 * @throws HantoException
	 */
	

	private void pieceTypeChecker(HantoPieceType type) throws HantoException{
		if(type == null){
			throw new HantoException("Need a valid piece type");
		}
		switch(type){
		case BUTTERFLY:
			return;
		case SPARROW:
			return;
		case CRAB:
			return;
			default:
				throw new HantoException("Gamma Hanto only use Butterfly and Sparrow");
		}
	}
	
	/**
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void doMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(from == null){
			HantoPiece piece = new HantoPieceImpl(onMove, pieceType);
			board.putPieceAt(piece,to);
		}
		else{
			board.movePiece(from,to);
		}
		if(pieceType == BUTTERFLY){
			setButterflyLocation(new HantoCoordinateImpl(to));
		}
	}
	
	/**
	 * 
	 */
	protected void incrementMove(){
		switch(onMove){
		case BLUE:
			onMove = HantoPlayerColor.RED;
			break;
		case RED:
			onMove = HantoPlayerColor.BLUE;
			moveCounter++;
			if(isFirstMove()){
				setFirstMove(false);
			}
			break;
		}
	}
	
	/**
	 * check the result of the game
	 * @return
	 */
	protected MoveResult checkResult(){
		
		if(blueButterflyCoor != null){
			if(board.getNeighborPieces(blueButterflyCoor).size() == 6){
				redWins = true;
				gameOver = true;
			}
		}
		
		if(redButterflyCoor != null){
			if(board.getNeighborPieces(redButterflyCoor).size() == 6){
				blueWins = true;
				gameOver = true;
			}
		}
		
		if(moveCounter > MAX_TURN){
			gameOver = true;
		}
		
		if(gameOver){
			return (gameOver && blueWins && redWins) ? DRAW : 
				(gameOver && blueWins) ? BLUE_WINS: 
					(gameOver && redWins) ? RED_WINS :
						DRAW;
		}
		
		else{
			return OK;
		}
		
		
	}
	
	
	
	/**
	 * The BUTTERFLY was placed. Set the location.
	 * @param to
	 */
	private void setButterflyLocation(final HantoCoordinateImpl to)
	{
		if (onMove == BLUE) {
			blueButterflyCoor = to;
		} else {
			redButterflyCoor = to;
		}
	}	
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		Map<HantoCoordinateImpl,HantoPiece> temp = board.getBoard();
		String s = "";
		for(HantoCoordinateImpl c: temp.keySet()){
			s = s + "(" + c.getX() + ", " + c.getY() + ")" + temp.get(c).getColor() + " " + temp.get(c).getType() + "\n";
		}
		System.out.println(s);
		return s;
	}
	
	public void placeHantoPieceOnBoard(HantoPieceType pieceType, HantoPlayerColor player,
			HantoCoordinate to) {
		// create objects to store into the board
		final HantoCoordinate toCoord = new HantoCoordinateImpl(to);
		// store the coordinate if the piece is butterfly
		if (player == HantoPlayerColor.BLUE) {
			if (pieceType == HantoPieceType.BUTTERFLY) {
				blueButterflyCoor = (HantoCoordinateImpl) toCoord;
			}			
		} else {
			if (pieceType == HantoPieceType.BUTTERFLY) {
				redButterflyCoor = (HantoCoordinateImpl) toCoord;
			}			
		}
		board.putPieceAt(new HantoPieceImpl(player, pieceType), to);
	}

	/**
	 * Set the turn number
	 * 
	 * @param num the number to set it to
	 */
	public void setTurnNumber(int num) {
		moveCounter = num;
	}
	
	/**
	 * Set the current player color
	 * 
	 * @param Color for the current player
	 */
	public void setCurrentPlayerColor(HantoPlayerColor currentPlayerColor) {
		this.onMove = currentPlayerColor;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
}
