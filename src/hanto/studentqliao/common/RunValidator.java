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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Implementation for crab validator
 * @author Qiaoyu Liao
 * @version Apr 19, 2016
 */
public class RunValidator extends MoveValidator {
	List<HantoCoordinateImpl> movable;
	
	/**
	 * constructor for crab validator
	 * @param id
	 */
	public RunValidator(HantoGameID id){
		super(id);
		movable = new ArrayList<HantoCoordinateImpl>();
	}	
	
	/**
	 * check if the crab can walk three distance to the destination
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	@Override
	public void checkMove(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to, 
			HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		int distanceCount = 1;
		checkButterflyPlayed(board, onMove, type);
		checkEmptyDestination(board, to);
		checkPieceOnBoard(board, from, onMove, type);

		movable.add(from);
		while(distanceCount < 4){
			List<HantoCoordinateImpl> temp = new ArrayList<HantoCoordinateImpl>();
			for(HantoCoordinateImpl c: movable){
				temp.addAll(getMovable(board, from, c));
			}
			movable.addAll(temp);
			
			for(HantoCoordinateImpl c:movable){
				if(c.equals(to)){
					return;
				}
			}
			distanceCount++;
		}
		
		throw new HantoException("Cannot walk three steps to the destination");
	}
	
		
	/**
	 * get the available coordinate for move
	 * @param board
	 * @param from
	 * @param c
	 * @return List<HantoCoordinateImpl> list of movable coordinates
	 */
	public List<HantoCoordinateImpl> getMovable(HantoBoard board,
			HantoCoordinateImpl from, HantoCoordinateImpl c) {
			final List<HantoCoordinateImpl> empty = board.getEmptyNeighbors(c);
			
			for(Iterator<HantoCoordinateImpl> iterator = empty.iterator(); iterator.hasNext();){
				HantoCoordinateImpl hc = iterator.next();
				//first check connected
				HantoBoard newboard = new HantoBoard(board);
				newboard.movePiece(from, hc);
				newboard.getBoard();
				if( !newboard.isConnected(hc)){
					iterator.remove();
				}
				//then check movable
				List<HantoCoordinateImpl> n1 = c.getNeighbors();
				List<HantoCoordinateImpl> n2 = hc.getNeighbors();
				List<HantoCoordinateImpl> common = new ArrayList <HantoCoordinateImpl> ();
				
				for (HantoCoordinateImpl hci: n1){
					if(n2.contains(hci) && board.checkEmpty(hci)){
						common.add(hci);
					}
				}
				
				if(common.size() == 0){
					iterator.remove();
				}
				
			}
			
			return empty;
	}

}
