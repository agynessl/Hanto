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

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * The implementation for Gamma Hanto WalkValidator
 * @version April 7, 2016
 */
public class WalkValidator extends MoveValidator{

	@Override
	public void canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{
		pieceTypeChecker(type);
		if(to == null){
			throw new HantoException("need a valid destination for the piece");
		}
		
		HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		if(from == null){
			checkPutPiece(board,dest,onMove,type);
		}
		else{
			HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
			checkWalk(board,origin,dest,onMove,type);
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
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkWalk(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{	
		checkButterflyPlayed(board,onMove,type);
		checkEmptyDestination(board,to);
		checkPieceOnBoard(board,from,onMove,type);
		checkMovable(board,from,to);
		checkDistance(from,to);
		checkConnected(board,from,to);
		
	}
	
	/**
	 * 
	 * @param board
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkButterflyPlayed(HantoBoard board, HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		if(board.getPieceCount(HantoPieceType.BUTTERFLY,onMove) == 0){
			throw new HantoException("Please play butterfly before move the piece");
		}	
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkDistance(HantoCoordinateImpl from, HantoCoordinateImpl to)throws HantoException{
		if(from.getDistance(to) != 1){
			throw new HantoException("The distance of walk should be 1");
		}
	}
	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkPieceOnBoard(HantoBoard board, HantoCoordinateImpl from,HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{
		if(board.getPieceAt(from) == null || board.getPieceAt(from).getColor() != onMove || board.getPieceAt(from).getType() != type){
			throw new HantoException ("no such piece on the given from coordinate");
		}
	}
	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkMovable(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to)throws HantoException{
		List<HantoCoordinateImpl> n1 = from.getNeighbors();
		List<HantoCoordinateImpl> n2 = to.getNeighbors();
		List<HantoCoordinateImpl> common = new ArrayList <HantoCoordinateImpl> ();
		
		for (HantoCoordinateImpl c: n1){
			if(n2.contains(c) && board.checkEmpty(c)){
				common.add(c);
			}
		}
		
		if(common.size() == 0){
			throw new HantoException("The piece cannot be move to the position because two other pieces already at each side");
		}
	}
	

}
