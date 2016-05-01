/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentqliao.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;

import hanto.common.*;
import hanto.studentqliao.common.HantoCoordinateImpl;
import hanto.studentqliao.common.HantoGameBase;
import hanto.studentqliao.common.MoveValidator;

import java.util.*;

/**
 * Implementation of Beta Hanto
 * @version Apr 19, 2016
 * @author Qiaoyu Liao
 */
public class BetaHantoGame extends HantoGameBase
{
	private int MAX_BUTTERFLY = 1;
	private int MAX_SPARROW = 5;

	public BetaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst);
		gameVersion = HantoGameID.BETA_HANTO;
		MAX_TURN = 6;
		MAX_BUTTERFLY = 1;
		MAX_SPARROW = 5;
	}

	protected void validateMove(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException{
		
		if(to == null){
			throw new HantoException("need a valid destination for the piece");
		}
		
		if(from != null){
			throw new HantoException("beta need to be from null");
		}
		
		if(pieceType == null){
			throw new HantoException("need a valid piece type");
		}
		
		if(pieceType != BUTTERFLY) {
			checkButterflyMovesByFourthMove(); 
		}
		
		
		if(isFirstMove()){
			firstMoveValidator(pieceType, from, to);
		}
		else{
			
			final MoveValidator validator = getMoveValidator(pieceType);
			if(validator == null){
				throw new HantoException("piece type not in this game");
			}
			putValidator(pieceType, to);
		}

	}
	
	/**
	 * put validator for beta hanto
	 * @param pieceType
	 * @param to
	 * @throws HantoException
	 */
	protected void putValidator(HantoPieceType pieceType, HantoCoordinate to) throws HantoException{
		// check empty destination
		if(!board.checkEmpty(to)){
			 throw new HantoException("the destination already has a hanto piece");
		 }
		// check connected
		final Collection<HantoPiece> neighborsPiece = 
				board.getNeighborPieces(new HantoCoordinateImpl(to));
		
		if(neighborsPiece.size() == 0){
			throw new HantoException("The piece is not connected with any "
					+ "other pieces on the board");
		}
	
		// check piece number
		switch(pieceType){
		case BUTTERFLY:
			if(board.getPieceCount(pieceType, onMove) >= MAX_BUTTERFLY){
				throw new HantoException("exceed the butterfly pieces use");
			}
			break;
		case SPARROW:
			if(board.getPieceCount(pieceType, onMove) >= MAX_SPARROW){
				throw new HantoException("exceed the sparrow pieces use");
			}
			break;
		default:
			break;	
		}
			
	}

}
