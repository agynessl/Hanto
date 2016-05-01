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
public class FlyValidator extends MoveValidator{

	/**
	 * constructor
	 * @param id
	 */
	public FlyValidator(HantoGameID id) {
		super(id);
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
	@Override
	public void checkMove(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to,
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		checkButterflyPlayed(board, onMove, type);
		checkEmptyDestination(board, to);		
		checkPieceOnBoard(board, from, onMove, type);
		checkConnected(board, from, to);
		if(game == HantoGameID.EPSILON_HANTO){
			checkDistance(from,to);
		}
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkDistance(HantoCoordinateImpl from,
			HantoCoordinateImpl to)throws HantoException{
		
		if(from.getDistance(to) > 4){
			throw new HantoException("The distance of fly should be less than 4");
		}
	}
	

	
}
