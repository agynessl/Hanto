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

/**
 * Description
 * 
 * @version Sep 21, 2014
 */
public interface HantoTestGame extends HantoGame {
	/**
	 * A data structure used in setting up the the initial configuration of a game for testing.
	 * 
	 * @version Sep 21, 2014
	 */
	class PieceLocationPair {
		public final HantoPlayerColor player;
		public final HantoPieceType pieceType;
		public final HantoCoordinate location;

		/**
		 * Default constructor
		 * 
		 * @param player the player color
		 * @param pieceType the piece type
		 * @param location the coordinate where the piece is at the beginning of the test
		 */
		public PieceLocationPair(HantoPlayerColor player, HantoPieceType pieceType,
				HantoCoordinate location) {
			this.player = player;
			this.pieceType = pieceType;
			this.location = location;
		}
	}

	/**
	 * Initialize the pieces on the board. This will put the pieces on the board as well as updating
	 * the store of pieces that are off the board.
	 * 
	 * @param initialPieces an array of initial pieces
	 */
	void initializeBoard(PieceLocationPair[] initialPieces);

	/**
	 * Set the current turn number (whole turn number) beginning at 1
	 * 
	 * @param turnNumber
	 */
	void setTurnNumber(int turnNumber);

	/**
	 * Set the player on the move. This is the player who will next call makeMove().
	 * 
	 * @param player the player who will make the next move
	 */
	void setPlayerMoving(HantoPlayerColor player);
}