package hanto.studentqliao.common;

import java.util.ArrayList;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public abstract class MoveValidator {
	int MAX_BUTTERFLY = 1;
	int MAX_SPARROW = 6;
	
	public abstract void canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor onMove, HantoPieceType type) throws HantoException;
	   
	//public abstract boolean 
	
	//check if the piece can be put
	// 1.empty destination 2. butterfly by fourth 3. adjacency 4.check butterfly
	public void checkPutPiece(HantoBoard board, HantoCoordinateImpl to, HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		checkEmptyDestination(board,to);
		checkAdjacency(board,to,onMove);
		checkPieceNum(board,onMove, type);
	}
	
	/**
	 * check empty destination
	 * @param board
	 * @param to
	 * @throws HantoException
	 */
	public void checkEmptyDestination(HantoBoard board, HantoCoordinateImpl to) throws HantoException{
		 if(!board.checkEmpty(to)){
			 throw new HantoException("the destination already has a hanto piece");
		 }
	}
	
	/**
	 * put check same color adjacency
	 * @param board
	 * @param to
	 * @param onMove
	 * @throws HantoException
	 */
	public void checkAdjacency(HantoBoard board, HantoCoordinateImpl to, HantoPlayerColor onMove) throws HantoException {
		boolean sameFlag = false, oppoFlag = false;
		ArrayList<HantoPiece> neighbors = board.getNeighborPieces(to);
		for(HantoPiece p: neighbors){
			if(p.getColor() == onMove){
				sameFlag = true;
			}
			else{
				oppoFlag = true;
			}
		}
		
		if(!(sameFlag&&!oppoFlag)){
			throw new HantoException("The adjacent hex do not have a same color piece or taken by the other color");
		}		
	}
	
	/**
	 * 
	 * @param board
	 * @param onMove
	 * @param type
	 * @throws HantoException 
	 */
	public void checkPieceNum(HantoBoard board, HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		switch(type){
		case BUTTERFLY:
			if(board.getPieceCount(type,onMove) >= MAX_BUTTERFLY){
				throw new HantoException("exceed the butterfly pieces use");
			}
			break;
		case SPARROW:
			if(board.getPieceCount(type,onMove) >= MAX_SPARROW){
				throw new HantoException("exceed the sparroe pieces use");
			}
			break;
			default:
				return;
		}
	}
	/**
	 * check if the board still connected after the move
	 * @param board
	 * @param from
	 * @param to
	 * @return
	 * @throws HantoException 
	 */
	public void checkConnected(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to) throws HantoException{
		HantoBoard newboard = new HantoBoard(board);
		newboard.movePiece(from,to);
		newboard.getBoard();
		if( !newboard.isConnected(to)){
			throw new HantoException("The pieces will not be connected after the move");
		}
	}
	
	

}
