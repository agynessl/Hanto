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



import hanto.common.HantoGameID;

import hanto.common.HantoPlayerColor;



import hanto.studentqliao.common.HantoGameBase;



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
		gameVersion = HantoGameID.DELTA_HANTO;
	}

}
