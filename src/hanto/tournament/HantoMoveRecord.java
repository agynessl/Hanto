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
 * This class is a data structure that records the move by a
 * player in a Hanto game. It is used for tournament play with AI players.
 * 
 * @author gpollice
 * @version Feb 20, 2013
 */
public class HantoMoveRecord
{
	private final HantoPieceType piece;
	private final HantoCoordinate from;
	private final HantoCoordinate to;
	
	/**
	 * Constructor. If the parameters are null, then this indicates a <em>resign</em>
	 * move.
	 * @param piece the Hanto piece type that moved
	 * @param from the source hex
	 * @param to the source hex
	 */
	public HantoMoveRecord(HantoPieceType piece, HantoCoordinate from, HantoCoordinate to)
	{
		this.piece = piece;
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the piece
	 */
	public HantoPieceType getPiece()
	{
		return piece;
	}

	/**
	 * @return the from
	 */
	public HantoCoordinate getFrom()
	{
		return from;
	}

	/**
	 * @return the to
	 */
	public HantoCoordinate getTo()
	{
		return to;
	}
}
