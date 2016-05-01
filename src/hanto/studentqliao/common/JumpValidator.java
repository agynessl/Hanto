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

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @version Apr 30, 2016
 * @author Qiaoyu Liao
 */
public class JumpValidator extends MoveValidator {

	public JumpValidator(HantoGameID id) {
		super(id);
	}
	
	/**
	 * check if horse can jump
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	@Override
	public void checkMove(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException {		
		checkButterflyPlayed(board, onMove, type);
		checkEmptyDestination(board, to);
		checkPieceOnBoard(board, from, onMove, type);
		checkConnected(board, from, to);
		checkJumpPath(board, from, to);
	}
	
	/**
	 * check the path for jump, if it is in line and occupied
	 * @param board
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkJumpPath(HantoBoard board, HantoCoordinateImpl from,
			HantoCoordinateImpl to)throws HantoException{
		int dx = to.getX() - from.getX();
		int dy = to.getY() - from.getY();
		
		if(!(dx == -dy || dx == 0 || dy == 0 )){
			throw new HantoException("For Jump it has to be in a straight line");
		}
		
		int distance = from.getDistance(to);
		
		if(distance < 2){
			throw new HantoException("For Jump it has to jump over an occpied hex");
		}
		
		dx = dx/distance;
		dy = dy/distance;
		
		for(int i = 1; i < distance; i++){
			HantoCoordinateImpl temp = new HantoCoordinateImpl(i*dx + from.getX(), i*dy + from.getY());
			if(board.getPieceAt(temp) == null){
				throw new HantoException("The path in the jump is not occupied");
			}
		}
		
	}

}
