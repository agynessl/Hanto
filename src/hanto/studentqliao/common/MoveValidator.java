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
package hanto.studentqliao.common;

import java.util.List;


import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * The implementation for Hanto abstract MoveValidator
 * @version April 19, 2016
 */
public abstract class MoveValidator {
	protected int MAX_BUTTERFLY = 0;
	protected int MAX_SPARROW = 0;
	protected int MAX_CRAB = 0;
	protected int MAX_HORSE = 0;
	protected HantoGameID game;
	
	/**
	 * constructor for move validator
	 * @param id
	 */
	protected MoveValidator(HantoGameID id){
		switch(id){
		case BETA_HANTO:
			game = HantoGameID.BETA_HANTO;
			MAX_BUTTERFLY = 1;
			MAX_SPARROW = 5;
			break;
		case GAMMA_HANTO:
			game = HantoGameID.GAMMA_HANTO;
			MAX_BUTTERFLY = 1;
			MAX_SPARROW = 5;
			break;
		case DELTA_HANTO:
			game = HantoGameID.DELTA_HANTO;
			MAX_BUTTERFLY = 1;
			MAX_SPARROW = 4;
			MAX_CRAB = 4;
			break;
		case EPSILON_HANTO:
			game = HantoGameID.EPSILON_HANTO;
			MAX_BUTTERFLY = 1;
			MAX_SPARROW = 2;
			MAX_CRAB = 6;
			MAX_HORSE = 4;
			break;
		}
	}
	/**
	 * abstract canMove
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
			final HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
			if(from == null){
				checkPutPiece(board, dest, onMove, type);
			}
			else{
				final HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
					checkMove(board, origin, dest, onMove, type);		
			}			
	}

	/**
	 * abstract class for each move
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public abstract void checkMove(HantoBoard board, HantoCoordinateImpl from,
			HantoCoordinateImpl to, 
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException;
	   

	/**
	 * check if the butterfly is played when moving pieces
	 * @param board
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkButterflyPlayed(HantoBoard board, 
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		if(board.getPieceCount(HantoPieceType.BUTTERFLY, onMove) == 0){
			throw new HantoException("Please play butterfly before move the piece");
		}
	}
	
	
	
	/**
	 * check if the moving piece is on the board
	 * @param board
	 * @param from
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */

	public void checkPieceOnBoard(HantoBoard board, HantoCoordinateImpl from,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		
		if(board.getPieceAt(from) == null || board.getPieceAt(from).getColor() != onMove ||
				board.getPieceAt(from).getType() != type){
			throw new HantoException ("no such piece on the given from coordinate");
		}
	}

	/**
	 * check to put pieces
	 * @param board
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkPutPiece(HantoBoard board, HantoCoordinateImpl to,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		
		checkEmptyDestination(board, to);
		checkAdjacency(board, to, onMove);
		checkPieceNum(board, onMove, type);
	}
	
	/**
	 * check empty destination
	 * @param board
	 * @param to
	 * @throws HantoException
	 */
	public void checkEmptyDestination(HantoBoard board,
			HantoCoordinateImpl to) throws HantoException{
		
		 if(!board.checkEmpty(to)){
			 throw new HantoException("the destination already has a hanto piece");
		 }
	}
	
	/**
	 * put check same color adjacency
	 * @param board
	 * @param to
	 * @param onMove
	 * @throws HantoException
	 */
	public void checkAdjacency(HantoBoard board,
			HantoCoordinateImpl to, HantoPlayerColor onMove) throws HantoException {
		
		boolean sameFlag = false, oppoFlag = false;
		final List<HantoPiece> neighbors = board.getNeighborPieces(to);
		for(HantoPiece p: neighbors){
			if(p.getColor() == onMove){
				sameFlag = true;
			}
			else{
				oppoFlag = true;
			}
		}
		
		if(!(sameFlag && !oppoFlag)){
			throw new HantoException(
					"The adjacent hex do not have a same color piece or taken by the other color");
		}
	}
	
	/**
	 * check if the pieces reach the maximum number
	 * @param board
	 * @param onMove
	 * @param type
	 * @throws HantoException 
	 */
	public void checkPieceNum(HantoBoard board,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		
		switch(type){
		case BUTTERFLY:
			if(board.getPieceCount(type, onMove) >= MAX_BUTTERFLY){
				throw new HantoException("exceed the butterfly pieces use");
			}
			break;
		case SPARROW:
			if(board.getPieceCount(type, onMove) >= MAX_SPARROW){
				throw new HantoException("exceed the sparrow pieces use");
			}
			break;
		case CRAB:
			if(board.getPieceCount(type, onMove) >= MAX_CRAB){
				throw new HantoException("exceed the crab pieces use");
			}
			break;
		case HORSE:
			if(board.getPieceCount(type, onMove) >= MAX_HORSE){
				throw new HantoException("exceed the horse pieces use");
			}
			default:
				return;
		}
	}
	/**
	 * check if the board still connected after the move
	 * @param board
	 * @param from
	 * @param to
	 * @throws HantoException 
	 */
	public void checkConnected(HantoBoard board, 
			HantoCoordinateImpl from, HantoCoordinateImpl to) throws HantoException{
		
		final HantoBoard newboard = new HantoBoard(board);
		newboard.movePiece(from, to);
		newboard.getBoard();
		if( !newboard.isConnected(to)){
			throw new HantoException("The pieces will not be connected after the move");
		}
	}
	
	

}
