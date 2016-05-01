/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

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

/**
 * Base for all Hanto Game
 * @author Qiaoyu Liao
 * @version Apr 19, 2016
 */
public abstract class HantoGameBase implements HantoGame {
	protected HantoBoard board;
	
	protected int moveCounter;
	protected boolean gameOver;

	protected boolean firstMove;
	protected HantoPlayerColor onMove, movesFirst;
	protected boolean blueWins, redWins;
	protected int MAX_TURN;
	
	protected HantoCoordinateImpl blueButterflyCoor;
	protected HantoCoordinateImpl redButterflyCoor;
	
	/**
	 * get the move validator for specific type
	 * @param type
	 * @return MoveValidator for the piece type
	 */
	protected abstract MoveValidator getMoveValidator(HantoPieceType type);
	
	/**
	 * constructor for hanto base 
	 * @param movesFirst
	 */
	protected HantoGameBase(HantoPlayerColor movesFirst){
		onMove = this.movesFirst = movesFirst;
		board = new HantoBoard();
		moveCounter = 1;
		gameOver = false;
		setFirstMove(true);
		blueButterflyCoor = redButterflyCoor = null;
		blueWins = redWins = false;
		MAX_TURN = Integer.MAX_VALUE;
	}
	
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		//game end check
		checkGameEnd();
		//move check
		validateMove(pieceType, from, to);
		//make the move
		doMove(pieceType, from, to);
		//increment the count
		incrementMove();
		//return result
		return checkResult();
	}
	
	
	/**
	 * check id game over
	 * @throws HantoException
	 */
	protected void checkGameEnd() throws HantoException{
		if(gameOver){
			throw new HantoException("game ends");
		}
	}
	
	/**
	 * check the butterfly placed by fourth move
	 * @throws HantoException
	 */
	protected void checkButterflyMovesByFourthMove() throws HantoException
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
	 * validate the move
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void validateMove(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException{
		
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
			final MoveValidator validator = getMoveValidator(pieceType);
			if(validator == null){
				throw new HantoException("piece type not in this game");
			}
			validator.canMove(board, from, to, onMove, pieceType);
		}

	}
	
	/**
	 * validator for the first turn move
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void firstMoveValidator(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException{
		//check piece type
		if(getMoveValidator(pieceType) == null){
			throw new HantoException("the piece type canno be played in this game");
		}
		
		if(onMove == movesFirst){
			if(from != null || to.getX() != 0 || to.getY() != 0){
				throw new HantoException("First move should be put on origin");
			}
		}
		else{
			final HantoCoordinateImpl origin = new HantoCoordinateImpl(0, 0);
			final HantoCoordinateImpl dest  = new HantoCoordinateImpl(to);
			if(from != null || !(origin.getNeighbors().contains(dest))){
				throw new HantoException("Second move should be put adjacent to origin");
			}
		}
	}


	/**
	 * do the actual move
	 * @param pieceType
	 * @param from
	 * @param to
	 */
	protected void doMove(HantoPieceType pieceType, 
			HantoCoordinate from, HantoCoordinate to){
		if(from == null){
			final HantoPiece piece = new HantoPieceImpl(onMove, pieceType);
			board.putPieceAt(piece, to);
		}
		else{
			board.movePiece(from, to);
		}
		if(pieceType == BUTTERFLY){
			setButterflyLocation(new HantoCoordinateImpl(to));
		}
	}
	
	/**
	 * increment the statistic
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
	 * @return MoveResult
	 */
	protected MoveResult checkResult(){
		
		checkWins();
		
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
	 * check if one side wins or end of game
	 */
	protected void checkWins(){
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
	
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		final Map<HantoCoordinateImpl,HantoPiece> temp = board.getBoard();
		String s = "";
		for(HantoCoordinateImpl c: temp.keySet()){
			s += "(" + c.getX() + ", " + c.getY() + ")" + 
		temp.get(c).getColor() + " " + temp.get(c).getType() + "\n";
		}
		System.out.println(s);
		return s;
	}
	
	/**
	 * place the Hanto Piece on Board without checking for testing
	 * @param pieceType
	 * @param player
	 * @param to
	 */
	public void placeHantoPieceOnBoard(HantoPieceType pieceType, HantoPlayerColor player,
			HantoCoordinate to){
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
	 * Set the turn number for testing
	 * @param num 
	 */
	public void setTurnNumber(int num) {
		moveCounter = num;
	}
	
	/**
	 * Set the current player color for testing
	 * @param Color for the current player
	 */
	public void setCurrentPlayerColor(HantoPlayerColor currentPlayerColor) {
		onMove = currentPlayerColor;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
}
