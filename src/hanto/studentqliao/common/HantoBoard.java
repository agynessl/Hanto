package hanto.studentqliao.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoBoard{

	private Hashtable <HantoCoordinateImpl, HantoPiece> boardpieces = new Hashtable<HantoCoordinateImpl, HantoPiece>();
	private Hashtable <HantoPieceType, Integer> bluePieceCounter = new Hashtable<HantoPieceType, Integer>();
	private Hashtable <HantoPieceType, Integer> redPieceCounter = new Hashtable<HantoPieceType, Integer>();
	
	
	public HantoBoard(){
		
	}
	
	/**
	 * copy constructor
	 * @param board
	 */
	public HantoBoard(HantoBoard board){
		boardpieces = board.getBoard();
		bluePieceCounter = board.getCounter(HantoPlayerColor.BLUE);
		redPieceCounter = board.getCounter(HantoPlayerColor.RED);
	}
	
	/**
	 * get the piece at specific coordinate
	 * @param coor
	 * @return
	 */
	public HantoPiece getPieceAt(HantoCoordinate coor){
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
			
			default: return 0;
		}
			 
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
				bluePieceCounter.replace(piece.getType(), getPieceCount(piece.getType(), HantoPlayerColor.BLUE));
			}
		case RED:
			if(!redPieceCounter.containsKey(piece.getType())){
				redPieceCounter.put(piece.getType(), 1);
			}
			else{
				redPieceCounter.replace(piece.getType(), getPieceCount(piece.getType(), HantoPlayerColor.RED));
			}
		}
	}
	
	/**
	 * move piece from one coordinate to another
	 * @param from
	 * @param to
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to){
		HantoPiece move = boardpieces.get(from);
		boardpieces.put(new HantoCoordinateImpl(to), move);
		boardpieces.remove(new HantoCoordinateImpl(from));
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
			
			for(HantoCoordinateImpl c: visitedCoor){
				visitedCoor.addAll(getOccupiedNeighbors(c));
			}
			return visitedCoor.size() == boardpieces.size();
		}
		
		return false;
	}
	
	
	/**
	 * check if a coordinate is empty
	 * @param coor
	 * @return
	 */
	public boolean checkEmpty(HantoCoordinate coor){
		return boardpieces.containsKey(new HantoCoordinateImpl(coor));
	}
	
}
