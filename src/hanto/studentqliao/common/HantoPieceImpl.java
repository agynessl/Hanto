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

import hanto.common.*;

/**
 * Implementation of the HantoPiece.
 * @version Mar 2,2016
 */
public class HantoPieceImpl implements HantoPiece
{
	private final HantoPlayerColor color;
	private final HantoPieceType type;
	
	/**
	 * Deafault constructor
	 * @param color the piece color
	 * @param type the piece type
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type)
	{
		this.color = color;
		this.type = type;
	}
	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor()
	{
		return color;
	}

	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType()
	{
		return type;
	}
	
	
		
}
