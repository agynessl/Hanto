/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentqliao.epsilon;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;


import hanto.studentqliao.common.HantoBoard;
import hanto.studentqliao.common.HantoCoordinateImpl;
import hanto.studentqliao.common.HantoGameBase;
import hanto.studentqliao.common.HantoPieceImpl;



import hanto.tournament.HantoMoveRecord;

/**
 * @version Apr 30, 2016
 * @author Qiaoyu Liao
 */
public class EpsilonHantoGame extends HantoGameBase {

	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		gameVersion = HantoGameID.EPSILON_HANTO;
	}
	
	/**
	 * check if still moves left
	 * @throws HantoException
	 */
	@Override
	protected void checkResign() throws HantoException{
		if(findAllValidMoves(onMove).size() != 0){
			throw new HantoPrematureResignationException();
		}
	}
	
	
	/**
	 * find all the valid move in this game
	 * @param thisMove
	 * @return collection of valid moves
	 */
	public Collection<HantoMoveRecord> findAllValidMoves(HantoPlayerColor thisMove){
		final Collection<HantoMoveRecord> availableMove = new LinkedList<HantoMoveRecord>();
		final Set<HantoCoordinateImpl> availableCoor = new HashSet<HantoCoordinateImpl>();
			
		//find the origin put move
		if(moveCounter == 1 && thisMove == movesFirst){
			availableMove.add(new HantoMoveRecord(HantoPieceType.BUTTERFLY,
					null, new HantoCoordinateImpl(0, 0)));
			availableMove.add(new HantoMoveRecord(HantoPieceType.CRAB,
					null, new HantoCoordinateImpl(0, 0)));
			availableMove.add(new HantoMoveRecord(HantoPieceType.SPARROW,
					null, new HantoCoordinateImpl(0, 0)));
			availableMove.add(new HantoMoveRecord(HantoPieceType.HORSE,
					null, new HantoCoordinateImpl(0, 0)));
			return availableMove;
		}
		
		//find all adjacent place
		for (HantoCoordinateImpl coord: board.getAllCoor()){
			availableCoor.addAll(board.getEmptyNeighbors(coord));
		}
		
		//find all the put move
		for(HantoPieceType type: HantoPieceType.values()){
			for(HantoCoordinateImpl to: availableCoor){
				try{
					validateMove(type, null, to);	
					availableMove.add(new HantoMoveRecord(type, null, to));
				}catch(Exception e){
					continue;
				}				
			}
		}
		
		//find all the move from one to another
		for(HantoCoordinateImpl from: board.getAllCoor()){
			HantoPiece piece = board.getPieceAt(from);
			if(piece.getColor() == thisMove){
				for(HantoCoordinateImpl to: availableCoor){
					try{
						validateMove(piece.getType(),from,to);
						availableMove.add(new HantoMoveRecord(piece.getType(), from, to));
					}catch(Exception e){
						continue;
					}
				}
			}
		}
		
		// return all that found
		return availableMove;
	}
	
	
	
	/**
	 * give rating of this move for compare
	 * @param move
	 * @param myColor
	 * @return double rating for the move
	 */
	public double MoveRating(HantoMoveRecord move, HantoPlayerColor myColor){
		double rating = 0;
		HantoCoordinateImpl myButterfly, oButterfly;
		
		//if butterfly not played yet
		if(redButterflyCoor == null || blueButterflyCoor == null){
			if(move.getFrom() == null){
				return Math.random() * 1000;
			}else{
				return Math.random() * -1000;
			}
		}
		
		//if all played, set the butterfly
		if(myColor == HantoPlayerColor.BLUE){
			myButterfly = blueButterflyCoor;
			oButterfly = redButterflyCoor;
		}else{
			myButterfly = redButterflyCoor;
			oButterfly = blueButterflyCoor;
		}
	
		
		//setup new board
		final HantoBoard after = new HantoBoard(board);
		if(move.getFrom() == null){
			final HantoPiece piece = new HantoPieceImpl(onMove, move.getPiece());
			after.putPieceAt(piece, move.getTo());
		}
		else{
			after.movePiece(move.getFrom(), move.getTo());
			if(myButterfly.equals(move.getFrom())){
				myButterfly = new HantoCoordinateImpl (move.getTo());
			}
		}
		
		final Collection<HantoCoordinateImpl> willSurroundedMe = 
				after.getOccupiedNeighbors(new HantoCoordinateImpl(myButterfly));
		final Collection<HantoCoordinateImpl> currentSurroundedMe = 
				board.getOccupiedNeighbors(new HantoCoordinateImpl(myButterfly));
		
		final Collection<HantoCoordinateImpl> willSurroundedO = 
				after.getOccupiedNeighbors(new HantoCoordinateImpl(oButterfly));
		final Collection<HantoCoordinateImpl> currentSurroundedO = 
				board.getOccupiedNeighbors(new HantoCoordinateImpl(oButterfly));
		
		double scaleme = currentSurroundedMe.size() / 3;
		
		//if can remove surrounding for my butterfly
		rating += ( currentSurroundedMe.size() - willSurroundedMe.size() ) * 100 * scaleme;
		//if can add surrounding for opponent butterfly
		rating += ( willSurroundedO.size() - currentSurroundedO.size() ) * 150;
		
		//give a higher chance for put a piece on board
		if(move.getFrom() == null){
			rating += 50 + Math.random() * 100;
			return rating;
		}
		
		//if nothing changed for opponent butterfly
		if( ( willSurroundedO.size() - currentSurroundedO.size() ) == 0){
				rating += ( oButterfly.getDistance(move.getFrom()) - oButterfly.getDistance(move.getTo()) ) * 3 * Math.random();
		}
	
		return rating;
	}

}
