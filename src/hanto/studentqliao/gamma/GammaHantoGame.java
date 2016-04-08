/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Qiaoyu Liao
 *******************************************************************************/
package hanto.studentqliao.gamma;

import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static hanto.common.HantoPieceType.BUTTERFLY;


import java.util.Map;

import hanto.common.*;
import hanto.studentqliao.common.*;

/**
 * The implementation for Gamma Hanto Game
 * @version April 7, 2016
 */
public class GammaHantoGame implements HantoGame {

	private HantoBoard board;
	private MoveValidator walkValidator;
	
	private int moveCounter;
	private boolean gameOver,firstMove;
	private HantoPlayerColor onMove, movesFirst;
	private int MAX_TURN;
	
	private HantoCoordinateImpl blueButterflyCoor;
	private HantoCoordinateImpl redButterflyCoor;
	
	
	
	/**
	 * 
	 * @param movesFirst
	 */
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
	private void validateMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(pieceType != BUTTERFLY) {
			checkButterflyMovesByFourthMove(); 
		}
		if(firstMove){
			firstMoveValidator(pieceType, from, to);
		}
		else{
			MoveValidator validator = getMoveValidator(pieceType);
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
			firstMove = false;
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
	private void doMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException{
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
		switch(onMove){
		case BLUE:
			onMove = HantoPlayerColor.RED;
			break;
		case RED:
			onMove = HantoPlayerColor.BLUE;
			moveCounter++;
			break;
		}
	}
	
	/**
	 * check the result of the game
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
		Map<HantoCoordinateImpl,HantoPiece> temp = board.getBoard();
		String s = "";
		for(HantoCoordinateImpl c: temp.keySet()){
			s = s + "(" + c.getX() + ", " + c.getY() + ")" + temp.get(c).getColor() + " " + temp.get(c).getType() + "\n";
		}
		System.out.println(s);
		return s;
	}

}
