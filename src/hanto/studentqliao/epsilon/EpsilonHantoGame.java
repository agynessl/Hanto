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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentqliao.common.FlyValidator;
import hanto.studentqliao.common.HantoBoard;
import hanto.studentqliao.common.HantoCoordinateImpl;
import hanto.studentqliao.common.HantoGameBase;
import hanto.studentqliao.common.HantoPieceImpl;
import hanto.studentqliao.common.JumpValidator;
import hanto.studentqliao.common.MoveValidator;
import hanto.studentqliao.common.WalkValidator;
import hanto.tournament.HantoMoveRecord;

/**
 * @version Apr 30, 2016
 * @author Qiaoyu Liao
 */
public class EpsilonHantoGame extends HantoGameBase {

	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		//check resign
		if(pieceType == null && from == null && to == null){
			checkResign();
			return onMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}		
		//game end check
		checkGameEnd();
		//move check
		validateMove(pieceType, from, to);
		//make the move
		doMove(pieceType, from, to);
		//increment the count
		incrementMove();
		//return result
		return checkResult();
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		MoveValidator mv = null;
		if(type == HantoPieceType.BUTTERFLY){
			mv = new WalkValidator(HantoGameID.EPSILON_HANTO);
		}
		else if(type == HantoPieceType.CRAB){
			mv = new WalkValidator(HantoGameID.EPSILON_HANTO);
		}
		else if(type == HantoPieceType.SPARROW){
			mv = new FlyValidator(HantoGameID.EPSILON_HANTO);
		}
		else if(type == HantoPieceType.HORSE){
			mv = new JumpValidator(HantoGameID.EPSILON_HANTO);
		}
		return mv;
	}
	
	/**
	 * check if still moves left
	 * @throws HantoException
	 */
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
		
		Set<HantoCoordinateImpl> availableCoor = new HashSet<HantoCoordinateImpl>();
		Collection<HantoMoveRecord> possibleMove = new LinkedList<HantoMoveRecord>();
		
		//find the origin put move
		if(moveCounter == 1 && thisMove == movesFirst){
			possibleMove.add(new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, new HantoCoordinateImpl(0, 0)));
			possibleMove.add(new HantoMoveRecord(HantoPieceType.CRAB, null, new HantoCoordinateImpl(0, 0)));
			possibleMove.add(new HantoMoveRecord(HantoPieceType.SPARROW, null, new HantoCoordinateImpl(0, 0)));
			possibleMove.add(new HantoMoveRecord(HantoPieceType.HORSE, null, new HantoCoordinateImpl(0, 0)));
			return possibleMove;
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
					possibleMove.add(new HantoMoveRecord(type, null, to));
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
						possibleMove.add(new HantoMoveRecord(piece.getType(), from, to));
					}catch(Exception e){
						continue;
					}
				}
			}
		}
		
		// return all that found
		return possibleMove;
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
		HantoBoard after = new HantoBoard(board);
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
		
		Collection<HantoCoordinateImpl> willSurroundedMe = after.getOccupiedNeighbors(new HantoCoordinateImpl(myButterfly));
		Collection<HantoCoordinateImpl> currentSurroundedMe = board.getOccupiedNeighbors(new HantoCoordinateImpl(myButterfly));
		
		Collection<HantoCoordinateImpl> willSurroundedO = after.getOccupiedNeighbors(new HantoCoordinateImpl(oButterfly));
		Collection<HantoCoordinateImpl> currentSurroundedO = board.getOccupiedNeighbors(new HantoCoordinateImpl(oButterfly));
		
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
