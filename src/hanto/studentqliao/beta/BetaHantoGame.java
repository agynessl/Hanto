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
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private boolean firstMove = true;
	private boolean gameOver= false;
	private boolean blueWin = false, redWin = false;
	private int moveCount = 0;
	
	private HantoCoordinateImpl blueButterflyHex = null, redButterflyHex = null;
	
	private HashMap<Integer, HantoPlayerColor> hexTaken = new HashMap<Integer, HantoPlayerColor>();
	
	private final HantoPiece blueButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
	private final HantoPiece redButterfly = new HantoPieceImpl(RED, BUTTERFLY);	
	private final HantoPiece blueSparrow = new HantoPieceImpl(BLUE,SPARROW);
	private final HantoPiece redSparrow = new HantoPieceImpl(RED,SPARROW);
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		if (gameOver) {
			throw new HantoException("You cannot move after the game is finished");
		}
		if (pieceType != BUTTERFLY || pieceType != SPARROW) {
			throw new HantoException("Only Butterflies and Sparrows are valid in Beta Hanto");
		}
		if(from != null){
			throw new HantoException("Cannot move the player in Beta Hanto");
		}

		final HantoCoordinateImpl destination = new HantoCoordinateImpl(to);
		
		//check the first move
		if(firstMove){
			if(destination.getX() != 0 || destination.getY() != 0){
				throw new HantoException("Blue did not move the firstMove to origin");
			}
			if(pieceType == BUTTERFLY){
				blueButterflyHex = destination;
			}
			hexTaken.put(destination.hashCode(),BLUE);
			moveCount++;
		}
		//check the rest.. should be 11 moves left
		else{
			//check first 4 step should have butterfly
			if(moveCount == 6){
				if(pieceType != BUTTERFLY && blueButterflyHex == null){
					throw new HantoException("Blue Butterfly did not put into the borad");
				}
			}
			else if(moveCount == 7){
				if(pieceType != BUTTERFLY && redButterflyHex == null){
					throw new HantoException("Red Butterfly did not put into the borad");
				}
			}
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
			//check if the butterfly already exist
			if(moveCount % 2 == 0){
				if(pieceType == BUTTERFLY && blueButterflyHex != null){
					throw new HantoException("Blue Butterfly already exist on the board");
				}
				hexTaken.put(destination.hashCode(),BLUE);
			}
			else{
				if(pieceType == BUTTERFLY && redButterflyHex != null){
					throw new HantoException("Red Butterfly already exist on the board");
				}
				hexTaken.put(destination.hashCode(),RED);
			}
				
			checkGameOver();
			moveCount++;				
		}
		
		if(moveCount == 12){
			gameOver = true;
		}
		firstMove = false;
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateAvailableMove(HantoCoordinateImpl to){
		
	}
	
	//check if one side has win the game, change the gameOver and winSide flag when someone win the game
	// winSide = 1 when blue wins and winSide = 2 when red wins
	public void checkGameOver() throws HantoException{
		
		boolean winflag = true;		
		if(blueButterflyHex != null){
			for(Direction d: Direction.values()){
				if(!hexTaken.containsKey(blueButterflyHex.getNeighbor(d).hashCode())){
					winflag = false;
					break;
				}
			}			
			if(winflag){
				blueWin = true;
				gameOver = true;
			}
		}
				
		winflag = true;		
		if(redButterflyHex != null){
			for(Direction d: Direction.values()){
				if(!hexTaken.containsKey(redButterflyHex.getNeighbor(d).hashCode())){
					winflag = false;
					break;
				}
			}
			if(winflag){
				redWin = true;
				gameOver = true;
			}
		}
	}
	

	public static void main(String[] args) {
		
	}

}
