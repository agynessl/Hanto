/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentqliao.tournament;

import java.util.Collection;

import hanto.common.*;
import hanto.studentqliao.HantoGameFactory;
import hanto.studentqliao.common.HantoCoordinateImpl;
import hanto.studentqliao.epsilon.EpsilonHantoGame;
import hanto.tournament.*;

/**
 * Description
 * @version Apr 20, 2016
 */
public class HantoPlayer implements HantoGamePlayer
{
	HantoPlayerColor myColor;
	EpsilonHantoGame game;

	/*
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst)
	{
		this.myColor = myColor;
		if(doIMoveFirst){
			game = (EpsilonHantoGame) HantoGameFactory.getInstance().makeHantoGame(version, myColor);
		}else{
			final HantoPlayerColor movesFirst;
			movesFirst = (myColor == HantoPlayerColor.BLUE) ? 
					HantoPlayerColor.RED : HantoPlayerColor.BLUE;
			game = (EpsilonHantoGame) HantoGameFactory.getInstance().makeHantoGame(version, movesFirst);
		}
	}

	/*
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		//if we move first
		if (opponentsMove == null) {
			try {
				game.makeMove(HantoPieceType.BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
			} catch (HantoException e) {
				System.out.println("Not able to play origin move");
			}
			return new HantoMoveRecord(HantoPieceType.BUTTERFLY,
					null, new HantoCoordinateImpl(0,0));
		}
		
		else{
			// or try opponents move
			try {
				game.makeMove(opponentsMove.getPiece(),
						opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				System.out.println("Opponent move not valid");
			}
			
			final Collection<HantoMoveRecord> allValidMoves = game.findAllValidMoves(myColor);
			
			HantoMoveRecord bestMove = new HantoMoveRecord(null, null, null);
			
			
			double bestMoveRating = -20000;
			for (HantoMoveRecord move : allValidMoves) {
				double rating = game.MoveRating(move, myColor);
				if (bestMoveRating < rating) {
					bestMoveRating = rating;
					bestMove = move;
				}
			}
			try {
				game.makeMove(bestMove.getPiece(), bestMove.getFrom(),
						bestMove.getTo());
			} catch (HantoException e) {
				System.out.println("This move not valid");
			}
			return bestMove;
		}
		
	}


}
