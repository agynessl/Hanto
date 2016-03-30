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
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.*;

import hanto.common.*;
import hanto.studentqliao.common.HantoCoordinateImpl;
import hanto.studentqliao.common.HantoCoordinateImpl.Direction;
import hanto.studentqliao.common.HantoPieceImpl;
import java.util.*;

/**
 * Implementation of Beta Hanto
 * @version Mar 29, 2016
 * @author Qiaoyu Liao
 */
public class BetaHantoGame implements HantoGame
{
	private boolean firstMove = true;
	private boolean gameOver= false;
	private boolean blueWin = false, redWin = false;
	private int moveCount = 0;
	
	private HantoCoordinateImpl blueButterflyHex = null, redButterflyHex = null;
	
	private final HashMap<Integer, HantoPlayerColor> hexTaken = new HashMap<Integer, HantoPlayerColor>();
	
	private final HantoPiece blueButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
	private final HantoPiece redButterfly = new HantoPieceImpl(RED, BUTTERFLY);
	private final HantoPiece blueSparrow = new HantoPieceImpl(BLUE, SPARROW);
	private final HantoPiece redSparrow = new HantoPieceImpl(RED, SPARROW);
	
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		//first check gameOver, HantoPieceType and from
		if (gameOver) {
			throw new HantoException("You cannot move after the game is finished");
		}
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("Only Butterflies and Sparrows are valid in Beta Hanto");
		}
		if(from != null){
			throw new HantoException("Cannot move the player in Beta Hanto");
		}

		final HantoCoordinateImpl destination = new HantoCoordinateImpl(to);
		
		//check if it is the first move
		if(firstMove){
			if(destination.getX() != 0 || destination.getY() != 0){
				throw new HantoException("Blue did not move the firstMove to origin");
			}
			
			if(pieceType == BUTTERFLY){
				blueButterflyHex = destination;
			}
			hexTaken.put(destination.hashCode(), BLUE);
			firstMove = false;
			moveCount++;
			
		}
		
		//check the rest
		else{
			//check if it is in an occupied spot
			if(hexTaken.containsKey(destination.hashCode())){
				throw new HantoException("The spot is already taken on the board");
			}
			//check if it is in the neighbor
			boolean neighborflag = false;
			for(Direction d: Direction.values()){
				if(hexTaken.containsKey(destination.getNeighbor(d).hashCode())){
					neighborflag = true;
				}
			}
			if(!neighborflag){
				throw new HantoException("Not play near an adjacent Hex");
			}
			
			//when play BLUE side
			if(moveCount % 2 == 0){
				//if it is a blue butterfly
				if(pieceType == BUTTERFLY){
					//if blue butterfly exist on board
					if(blueButterflyHex != null){
						throw new HantoException("Blue Butterfly already exist on the board");
					}
					else{
						blueButterflyHex = destination;
					}
				}
				// if it is a blue sparrow
				else{
					if(moveCount == 6 && blueButterflyHex == null){
						throw new HantoException("Blue Butterfly did not put into the borad");
					}
				}
				hexTaken.put(destination.hashCode(), BLUE);
			}
			
			//when play RED side
			else{
				//if it is a red butterfly
				if(pieceType == BUTTERFLY){
					//if red butterfly exist on board
					if(redButterflyHex != null){
						throw new HantoException("Red Butterfly already exist on the board");
					}
					else{
						redButterflyHex = destination;
					}
				}
				//if it is a red sparrow
				else{
					if(moveCount == 7 && redButterflyHex == null){
						throw new HantoException("Red Butterfly did not put into the borad");
					}
				}
				hexTaken.put(destination.hashCode(), RED);
			}
			
			//check the winning condition
			checkGameOver();
			moveCount++;
			//when all six turns finished
			if(moveCount == 12){
				gameOver = true;
			}
		}
		
		//get the move result
		final MoveResult moveResult = (gameOver && blueWin && redWin) ? DRAW : 
			(gameOver && blueWin) ? BLUE_WINS: 
				(gameOver && redWin) ? RED_WINS :
					gameOver ? DRAW:
						OK;
		return moveResult;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		final HantoCoordinateImpl coor = new HantoCoordinateImpl(where);
		return coor.equals(blueButterflyHex) ? blueButterfly
				: coor.equals(redButterflyHex) ? redButterfly
				: hexTaken.get(coor.hashCode()) == BLUE ? blueSparrow
				: hexTaken.get(coor.hashCode()) == RED ? redSparrow
				: null;
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		if(hexTaken.size() > 0){
			return Integer.toString(hexTaken.size());
		}
		else{
			return Integer.toString(0);
		}
	}
	

	/**
	 * helper function
	 * check if one side has win the game
	 * change the gameOver and winSide flag when someone win the game
	 * @throws HantoException
	 */
	public void checkGameOver() throws HantoException{
		//check if the blue butterfly is surrounded
		boolean winflag = true;
		if(blueButterflyHex != null){
			for(Direction d: Direction.values()){
				if(!hexTaken.containsKey(blueButterflyHex.getNeighbor(d).hashCode())){
					winflag = false;
					break;
				}
			}
			if(winflag){
				redWin = true;
				gameOver = true;
			}
		}
		//check if the red butterfly is surrounded	
		winflag = true;
		if(redButterflyHex != null){
			for(Direction d: Direction.values()){
				if(!hexTaken.containsKey(redButterflyHex.getNeighbor(d).hashCode())){
					winflag = false;
					break;
				}
			}
			if(winflag){
				blueWin = true;
				gameOver = true;
			}
		}
	}
	

}
