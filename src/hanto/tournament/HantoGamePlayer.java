/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.tournament;

import hanto.common.*;

/**
 * <p>
 * This interface describes the behavior required of an AI player for a
 * version of Hanto. Students will implement this interface in a class called
 * &lt;version&gt;HantoPlayer that is in the <b>hanto.studentx.tournament</b> package.
 * </p><p>
 * For example, for a Delta Hanto game player, the student will implement a class called
 * <code>DeltaHantoPlayer</code>.
 * </p>
 * @author gpollice
 * @version Feb 20, 2013
 */
public interface HantoGamePlayer
{
	/**
	 * This method must be called first after the player is constructed. It tells the
	 * player what version of the game to play, what the player's color is, and whether the
	 * player moves first.
	 * @param version the specific Hanto game to play
	 * @param myColor the color for this player
	 * @param doIMoveFirst true if the player will make the first move
	 */
	void startGame(HantoGameID version, HantoPlayerColor myColor, boolean doIMoveFirst);
	
	/**
	 * Make the player's next move.
	 * @param opponentsMove this is the result of the opponent's last move, in response
	 * 	to your last move. This will be null if you are making the first move of the game.
	 * @return your move
	 */
	HantoMoveRecord makeMove(HantoMoveRecord opponentsMove);
}
