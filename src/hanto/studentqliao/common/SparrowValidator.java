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




import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Implementation for Sparrow Validator
 * @author Qiaoyu Liao
 * @version Apr 19, 2016
 */
public class SparrowValidator extends MoveValidator{

	/**
	 * constructor
	 * @param id
	 */
	public SparrowValidator(HantoGameID id) {
		super(id);
	}

	@Override
	public void canMove(HantoBoard board, HantoCoordinate from, 
			HantoCoordinate to, HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		final HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		if(from == null){
			checkPutPiece(board, dest, onMove, type);
		}
		else{
			final HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
			checkFly(board, origin, dest, onMove, type);
		}
		
	}
	
	
	/**
	 * check if the piece can fly
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkFly(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		checkButterflyPlayed(board, onMove, type);
		checkEmptyDestination(board, to);
		checkPieceOnBoard(board, from, onMove, type);
		checkConnected(board, from, to);
		
	}
	

	
}
