package hanto.studentqliao.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public abstract class MoveValidator {
	
	public abstract boolean canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor onMove, HantoPieceType type);
	
	//public abstract boolean 
	
	
	//check butterfly move by fourth
	public boolean checkButterflyMoveByFourth(){
		return false;
	}
	
	//check if the piece can be put
	// 1.empty destination 2. butterfly by fourth 3. adjacency 4.check butterfly
	public boolean checkPutPiece(HantoBoard board, HantoCoordinate to, HantoPlayerColor onMove, HantoPiece piece){
		return false;
	}
	
	public abstract boolean checkEmptyDestination();//check empty
	public boolean checkAdjacency() {
		// TODO Auto-generated method stub
		return false;
	}
	
	//check will the baord still connect after the move
	public boolean checkConnected(HantoBoard board, HantoCoordinate from, HantoCoordinate to){
		HantoBoard newboard = new HantoBoard(board);
		newboard.movePiece(from,to);
		return newboard.isConnected();
	}
	

}
