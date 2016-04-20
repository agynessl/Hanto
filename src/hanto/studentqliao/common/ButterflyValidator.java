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
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * The implementation for ButterflyValidator
 * @version April 19, 2016
 */
public class ButterflyValidator extends MoveValidator{
	
	/**
	 * constructor for butterflyValidator
	 * @param id
	 */
	public ButterflyValidator(HantoGameID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		
		final HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		if(from == null){
			checkPutPiece(board, dest, onMove, type);
		}
		else{
			final HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
			checkWalk(board, origin, dest, onMove, type);
		}
		
	}
	
	
	
	/**
	 * validate the walk
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkWalk(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		
		checkButterflyPlayed(board, onMove, type);
		checkEmptyDestination(board, to);
		checkPieceOnBoard(board, from, onMove, type);
		checkMovable(board, from, to);
		checkDistance(from, to);
		checkConnected(board, from, to);
		
	}
	
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkDistance(HantoCoordinateImpl from,
			HantoCoordinateImpl to)throws HantoException{
		
		if(from.getDistance(to) != 1){
			throw new HantoException("The distance of walk should be 1");
		}
	}

	
	/**
	 * check if the piece can be moved
	 * @param board
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkMovable(HantoBoard board, HantoCoordinateImpl from,
			HantoCoordinateImpl to)throws HantoException{
		
		final List<HantoCoordinateImpl> n1 = from.getNeighbors();
		final List<HantoCoordinateImpl> n2 = to.getNeighbors();
		final List<HantoCoordinateImpl> common = new ArrayList <HantoCoordinateImpl> ();
		
		for (HantoCoordinateImpl c: n1){
			if(n2.contains(c) && board.checkEmpty(c)){
				common.add(c);
			}
		}
		
		if(common.size() == 0){
			throw new HantoException("The piece cannot be move to the position "
					+ "because two other pieces already at each side");
		}
	}
	

}
