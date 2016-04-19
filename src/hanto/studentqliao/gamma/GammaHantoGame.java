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
public class GammaHantoGame extends HantoGameBase {
	
	//MoveValidator walkValidator;
	
	
	/**
	 * 
	 * @param movesFirst
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst){
		onMove = this.movesFirst = movesFirst;
		board = new HantoBoard();
		moveCounter = 1;
		gameOver = false;
		firstMove = true;
		blueButterflyCoor = redButterflyCoor = null;
		//walkValidator = new ButterflyValidator(HantoGameID.GAMMA_HANTO);
		MAX_TURN = 20;
	}



	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		MoveValidator mv = null;
		if(type == HantoPieceType.BUTTERFLY || type == HantoPieceType.SPARROW){
			mv = new ButterflyValidator(HantoGameID.GAMMA_HANTO);
		}
		return mv;
	}
	
	
	

}
