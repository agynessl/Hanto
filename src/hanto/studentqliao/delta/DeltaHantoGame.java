/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentqliao.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentqliao.common.WalkValidator;
import hanto.studentqliao.common.RunValidator;
import hanto.studentqliao.common.HantoGameBase;
import hanto.studentqliao.common.MoveValidator;
import hanto.studentqliao.common.FlyValidator;

/**
 * Implementation of Delta Hanto Game
 * @author Qiaoyu Liao
 * @version Apr 19, 2016
 */
public class DeltaHantoGame extends HantoGameBase{

	/**
	 * constructor for delta hanto
	 * @param movesFirst
	 */
	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		MoveValidator mv = null;
		if(type == HantoPieceType.BUTTERFLY){
			mv = new WalkValidator(HantoGameID.DELTA_HANTO);
		}
		else if(type == HantoPieceType.CRAB){
			mv = new RunValidator(HantoGameID.DELTA_HANTO);
		}
		else if(type == HantoPieceType.SPARROW){
			mv = new FlyValidator(HantoGameID.DELTA_HANTO);
		}
		return mv;
	}

	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		//check resign
		if(pieceType == null && from == null && to == null){
			gameOver = true;
			return onMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		
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

}
