/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.common;

import hanto.studentqliao.delta.DeltaHantoTestGame;

public class HantoTestGameFactory {
	private static final HantoTestGameFactory instance = new HantoTestGameFactory();

	/**
	 * Default private descriptor.
	 */
	private HantoTestGameFactory() {
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static HantoTestGameFactory getInstance() {
		return instance;
	}

	/**
	 * Create the specified Hanto game version with the Blue player moving
	 * first.
	 * 
	 * @param gameId
	 *            the version desired.
	 * @return the game instance
	 */
	public HantoTestGame makeHantoTestGame(HantoGameID gameId) {
		return makeHantoTestGame(gameId, HantoPlayerColor.BLUE);
	}

	/**
	 * Factory method that returns the appropriately configured Hanto game.
	 * 
	 * @param gameId
	 *            the version desired.
	 * @param movesFirst
	 *            the player color that moves first
	 * @return the game instance
	 */
	public HantoTestGame makeHantoTestGame(HantoGameID gameId,
			HantoPlayerColor movesFirst) {
		HantoTestGame game = null;
		switch (gameId) {
		case DELTA_HANTO:
			game = new DeltaHantoTestGame(movesFirst);
			break;
		default:
			break;
		}
		return game;
	}

}
