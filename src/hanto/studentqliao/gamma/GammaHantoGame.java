package hanto.studentqliao.gamma;

import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.ArrayList;

import hanto.common.*;
import hanto.studentqliao.common.*;

public class GammaHantoGame implements HantoGame {

	private HantoBoard board;
	private MoveValidator walkValidator;
	
	private int moveCounter;
	private boolean gameOver,firstMove;
	private HantoPlayerColor onMove, movesFirst;
	private int MAX_TURN;
	
	private HantoCoordinateImpl blueButterflyCoor;
	private HantoCoordinateImpl redButterflyCoor;
	
	
	
	
	public GammaHantoGame(HantoPlayerColor movesFirst){
		onMove = this.movesFirst = movesFirst;
		board = new HantoBoard();
		moveCounter = 0;
		gameOver = false;
		firstMove = true;
		blueButterflyCoor = redButterflyCoor = null;
		walkValidator = new WalkValidator();
		MAX_TURN = 20;
	}
	
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		//game end check
		checkGameEnd();
		if(pieceType != BUTTERFLY) { checkButterflyMovesByFourthMove(); }
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
	private void checkGameEnd() throws HantoException{
		if(moveCounter >= MAX_TURN){
			throw new HantoException("game ends");
		}
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
		if (moveCounter == 3) {
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
	private void validateMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to){
		MoveValidator validator = getMoveValidator(pieceType);
		if( validator.canMove(board, from, to, onMove, pieceType) ){
			if(from == null){
				board.putPieceAt(new HantoPieceImpl(onMove, pieceType), to);
			}
			else{
				board.movePiece(from,to);
			}
		}
	}
	
	
	private void doMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to){
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
	private void incrementMove(){
		if(!onMove.equals(firstMove)){
			moveCounter++;
		}
		switch(onMove){
		case BLUE:
			onMove = HantoPlayerColor.RED;
			break;
		case RED:
			onMove = HantoPlayerColor.BLUE;
			break;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private MoveResult checkResult(){
		boolean blueWin = false,redWin = false;
		
		if(blueButterflyCoor != null){
			if(board.getNeighborPieces(blueButterflyCoor).size() == 6){
				redWin = true;
				gameOver = true;
			}
		}
		
		if(redButterflyCoor != null){
			if(board.getNeighborPieces(redButterflyCoor).size() == 6){
				blueWin = true;
				gameOver = true;
			}
		}
		
		if(moveCounter >= MAX_TURN){
			gameOver = true;
		}
		
		if(gameOver){
			return (gameOver && blueWin && redWin) ? DRAW : 
				(gameOver && blueWin) ? BLUE_WINS: 
					(gameOver && redWin) ? RED_WINS :
						gameOver ? DRAW:
							OK;
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
	
	private MoveValidator getMoveValidator(HantoPieceType type){
		return walkValidator;
	}
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
