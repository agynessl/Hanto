package hanto.studentqliao.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoBoard{

	private Hashtable <HantoCoordinateImpl, HantoPiece> boardpieces = new Hashtable<HantoCoordinateImpl, HantoPiece>();
	private Hashtable <HantoPieceType, Integer> bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
	private Hashtable <HantoPieceType, Integer> redPieceCounter = new Hashtable<HantoPieceType, Integer>();
	
	
	public HantoBoard(){
		
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
		boardpieces = new Hashtable<HantoCoordinateImpl, HantoPiece>(board.boardpieces);
		for(HantoPieceType type: HantoPieceType.values()){
			redPieceCounter.put(type, board.getPieceCount(type, HantoPlayerColor.RED));
			bluePieceCounter.put(type, board.getPieceCount(type, HantoPlayerColor.BLUE));
		}
	}
	
	/**
	 * get the piece at specific coordinate
	 * @param coor
	 * @return
	 * @throws HantoException 
	 */
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
	 * @return
	 */
	public int getPieceCount(HantoPieceType type, HantoPlayerColor color){
		switch(color){
		case BLUE:
			return bluePieceCounter.get(type);
		case RED:
			return redPieceCounter.get(type);
		}
		return 0;
			 
	}

	
	/**
	 * 
	 * @param coor
	 * @return
	 */
	public ArrayList<HantoPiece> getNeighborPieces(HantoCoordinateImpl coor){
		ArrayList<HantoPiece> neighbors = new ArrayList<HantoPiece>();
		ArrayList<HantoCoordinateImpl> neighborsCoor = coor.getNeighbors();
		
		for(HantoCoordinateImpl c: neighborsCoor){
			if(boardpieces.containsKey(c)){
				neighbors.add(boardpieces.get(c));
			}
		}
		
		return neighbors;
	}
	
	/**
	 * 
	 * @param coor
	 * @return
	 */
	public ArrayList<HantoCoordinateImpl> getOccupiedNeighbors(HantoCoordinateImpl coor){
		ArrayList<HantoCoordinateImpl> neighbors = new ArrayList<HantoCoordinateImpl>();
		ArrayList<HantoCoordinateImpl> neighborsCoor = coor.getNeighbors();
		
		for(HantoCoordinateImpl c: neighborsCoor){
			if(boardpieces.containsKey(c)){
				neighbors.add(c);
			}
		}
		
		return neighbors;
	}
	
	/**
	 * get the board
	 * @return
	 */
	public Hashtable<HantoCoordinateImpl, HantoPiece> getBoard(){
		Hashtable<HantoCoordinateImpl, HantoPiece> temp = boardpieces;
		String s = "";
		for(HantoCoordinateImpl c: temp.keySet()){
			s = s + "(" + c.getX() + ", " + c.getY() + ")" + temp.get(c).getColor() + " " + temp.get(c).getType() + "\n";
		}
		System.out.println(s);
		return boardpieces;
	}
	
	/**
	 * get the counter
	 * @param color
	 * @return
	 */
	public Hashtable <HantoPieceType, Integer> getCounter(HantoPlayerColor color){
		switch(color){
		case BLUE:
			return bluePieceCounter;
		case RED:
			return redPieceCounter;
			
			default: return null;
		}
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
			if(!bluePieceCounter.containsKey(piece.getType())){
				bluePieceCounter.put(piece.getType(), 1);
			}
			else{
				bluePieceCounter.replace(piece.getType(), getPieceCount(piece.getType(), HantoPlayerColor.BLUE) + 1);
			}
			break;
		case RED:
			if(!redPieceCounter.containsKey(piece.getType())){
				redPieceCounter.put(piece.getType(), 1);
			}
			else{
				redPieceCounter.replace(piece.getType(), getPieceCount(piece.getType(), HantoPlayerColor.RED) + 1);
			}
			break;
		}
	}
	
	/**
	 * move piece from one coordinate to another
	 * @param from
	 * @param to
	 * @throws HantoException 
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException {
		HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
		HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		HantoPiece moveable = getPieceAt(origin);
		boardpieces.put(dest, moveable);
		boardpieces.remove(origin);
	}
	
	/**
	 * check if the board pieces is connected
	 * @return
	 */
	public boolean isConnected(HantoCoordinateImpl coor){
		//TODO: is connected how to check if the 
		Set<HantoCoordinateImpl> coorSet = boardpieces.keySet();
		if(coorSet.contains(coor)){
			Set<HantoCoordinateImpl> visitedCoor = new HashSet<HantoCoordinateImpl>();
			visitedCoor.add(coor);
			int size = 0;
			
			do{
				size = visitedCoor.size();
				synchronized(this){
					for(HantoCoordinateImpl c: visitedCoor){
						visitedCoor.addAll(getOccupiedNeighbors(c));
					}
				}
			}while(visitedCoor.size() > size);
				System.out.println(visitedCoor.size() + " " + coorSet.size());
				return visitedCoor.size() == coorSet.size();
			}
			
		
		return false;
	}
	
	
	/**
	 * check if a coordinate is empty
	 * return true if empty and false if not
	 * @param coor
	 * @return
	 */
	public boolean checkEmpty(HantoCoordinate coor){
		return !(boardpieces.containsKey(new HantoCoordinateImpl(coor)));
	}
	
}
