/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Qiaoyu Liao
 *******************************************************************************/

package hanto.studentqliao.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * The implementation for Hanto HantoBoard
 * @version April 7, 2016
 */
public class HantoBoard{

	private Map<HantoCoordinateImpl,HantoPiece> boardpieces;
	private final Hashtable <HantoPieceType, Integer> bluePieceCounter;
	private final Hashtable <HantoPieceType, Integer> redPieceCounter;
	
	
	/**
	 * Constructor for HantoBoard.
	 */
	public HantoBoard(){
		boardpieces = new Hashtable<HantoCoordinateImpl, HantoPiece>();
		bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
		redPieceCounter = new Hashtable<HantoPieceType, Integer>();
		for(HantoPieceType type: HantoPieceType.values()){
			redPieceCounter.put(type, 0);
			bluePieceCounter.put(type, 0);
		}
	}
	
	/**
	 * copy constructor
	 * @param board
	 */
	public HantoBoard(HantoBoard board){
		bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
		redPieceCounter = new Hashtable<HantoPieceType, Integer>();
		boardpieces = new Hashtable<HantoCoordinateImpl, HantoPiece>(board.boardpieces);
		for(HantoPieceType type: HantoPieceType.values()){
			redPieceCounter.put(type, board.getPieceCount(type, HantoPlayerColor.RED));
			bluePieceCounter.put(type, board.getPieceCount(type, HantoPlayerColor.BLUE));
		}
	}
	
	/**
	 * get the piece at specific coordinate
	 * @param coor
	
	 * @return HantoPiece at the location */
	public HantoPiece getPieceAt(HantoCoordinate coor){
		if( boardpieces.get(new HantoCoordinateImpl(coor)) == null){
			return null;
		}
		return boardpieces.get(new HantoCoordinateImpl(coor));
	}
	
	/**
	 * get the count of the piece
	 * @param type
	 * @param color
	
	 * @return integer for how many pieces that type has */
	public int getPieceCount(HantoPieceType type, HantoPlayerColor color){
		switch(color){
		case BLUE:
			return bluePieceCounter.get(type);
		case RED:
			return redPieceCounter.get(type);
			default:
				return 0;
		}
	}

	
	/**
	 * Get the pieces that occupy the neighbors
	 * @param coor
	 * @return List of hantopieces */
	public List<HantoPiece> getNeighborPieces(HantoCoordinateImpl coor){
		final List<HantoPiece> neighbors = new ArrayList<HantoPiece>();
		final List<HantoCoordinateImpl> neighborsCoor = coor.getNeighbors();
		
		for(HantoCoordinateImpl c: neighborsCoor){
			if(boardpieces.containsKey(c)){
				neighbors.add(boardpieces.get(c));
			}
		}
		
		return neighbors;
	}
	
	/**
	 * get the occupied neighbors coordinates
	 * @param coor
	
	 * @return get the neighbor coordinate that is occupied */
	public List<HantoCoordinateImpl> getOccupiedNeighbors(HantoCoordinateImpl coor){
		final List<HantoCoordinateImpl> neighbors = new ArrayList<HantoCoordinateImpl>();
		final List<HantoCoordinateImpl> neighborsCoor = coor.getNeighbors();
		
		final Iterator<HantoCoordinateImpl> i = neighborsCoor.iterator();
		while(i.hasNext()){
			HantoCoordinateImpl c = i.next();
			if(boardpieces.containsKey(c)){
				neighbors.add(c);
			}
		}
		
		return neighbors;
	}
	
	/**
	 * get the occupied neighbors coordinates
	 * @param coor
	
	 * @return get the neighbor coordinate that is occupied */
	public List<HantoCoordinateImpl> getEmptyNeighbors(HantoCoordinateImpl coor){
		final List<HantoCoordinateImpl> neighbors = new ArrayList<HantoCoordinateImpl>();
		final List<HantoCoordinateImpl> neighborsCoor = coor.getNeighbors();
		
		final Iterator<HantoCoordinateImpl> i = neighborsCoor.iterator();
		while(i.hasNext()){
			HantoCoordinateImpl c = i.next();
			if(!boardpieces.containsKey(c)){
				neighbors.add(c);
			}
		}
		
		return neighbors;
	}
	
	/**
	 * get the board
	 * @return the map of the board
	 */
	public Map<HantoCoordinateImpl,HantoPiece> getBoard(){
		final Map<HantoCoordinateImpl,HantoPiece> temp = boardpieces;
		String s = "";
		for(HantoCoordinateImpl c: temp.keySet()){
			s += "(" + c.getX() + ", " + c.getY() + ")" +
		temp.get(c).getColor() + " " + temp.get(c).getType() + "\n";
		}
		System.out.println(s);
		return boardpieces;
	}
	
	
	/** 
	 * put piece in both the board and the counter
	 * @param piece
	 * @param coor 
	 */
	public void putPieceAt(HantoPiece piece, HantoCoordinate coor){
		boardpieces.put(new HantoCoordinateImpl(coor), piece);
		switch(piece.getColor()){
		case BLUE:
				bluePieceCounter.replace(piece.getType(), 
						getPieceCount(piece.getType(), HantoPlayerColor.BLUE) + 1);
			break;
		case RED:
				redPieceCounter.replace(piece.getType(),
						getPieceCount(piece.getType(), HantoPlayerColor.RED) + 1);
			break;
			default:
		}
	}
	
	/**
	 * move piece from one coordinate to another
	 * @param from
	 * @param to
	 * */
	public void movePiece(HantoCoordinate from, HantoCoordinate to){
		final HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
		final HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		final HantoPiece moveable = getPieceAt(origin);
		boardpieces.put(dest, moveable);
		boardpieces.remove(origin);
	}
	
	/**
	 * check if the board pieces is connected
	
	 * @param coor HantoCoordinateImpl
	 * @return if the board is connected */
	public boolean isConnected(HantoCoordinateImpl coor){
		//TODO: is connected how to check if the 
		final Set<HantoCoordinateImpl> coorSet = boardpieces.keySet();
		if(coorSet.contains(coor)){
			final Set<HantoCoordinateImpl> visitedCoor = new HashSet<HantoCoordinateImpl>();
			visitedCoor.add(coor);
			int size = 0;
			
			do{
				size = visitedCoor.size();
				Set<HantoCoordinateImpl> temp = new HashSet<HantoCoordinateImpl>();
				synchronized(this){
					for(HantoCoordinateImpl c: visitedCoor){
						temp.addAll(getOccupiedNeighbors(c));
					}
				}
				visitedCoor.addAll(temp);
			}while(visitedCoor.size() > size);
				System.out.println(visitedCoor.size() + " " + coorSet.size());
				return visitedCoor.size() == coorSet.size();
			}
					
		return false;
	}
	
	/**
	 * get all occupied coordinates
	 * @return
	 */
	public Set<HantoCoordinateImpl> getAllCoor(){
		return boardpieces.keySet();
	}
	
	/**
	 * check if a coordinate is empty
	 * return true if empty and false if not
	 * @param coor
	
	 * @return boolean
	 */
	public boolean checkEmpty(HantoCoordinate coor){
		return !(boardpieces.containsKey(new HantoCoordinateImpl(coor)));
	}
	
}
