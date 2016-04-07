package hanto.studentqliao.common;

import java.util.ArrayList;
import java.util.Collection;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class WalkValidator extends MoveValidator{

	@Override
	public void canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{
		pieceTypeChecker(type);
		if(to == null){
			throw new HantoException("need a valid destination for the piece");
		}
		
		HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		if(from == null){
			checkPutPiece(board,dest,onMove);
		}
		else{
			HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
			checkWalk(board,origin,dest,onMove,type);
		}
		
	}
	
	private void pieceTypeChecker(HantoPieceType type) throws HantoException{	
		if(type == null){
			throw new HantoException("Need a valid piece type");
		}
		switch(type){
		case BUTTERFLY:
			return;
		case SPARROW:
			return;
			default:
				throw new HantoException("Gamma Hanto only use Butterfly and Sparrow");
		}
	}
	
	public void checkWalk(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{
		
		checkEmptyDestination(board,to);
		checkConnected(board,from,to);
		checkDistance(from,to);
		
	}
	
	public void checkDistance(HantoCoordinateImpl from, HantoCoordinateImpl to)throws HantoException{
		if(from.getDistance(to) != 1){
			throw new HantoException("The distance of walk should be 1");
		}
	}
	

	public void checkMovale(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to)throws HantoException{
		ArrayList<HantoCoordinateImpl> n1 = from.getNeighbors();
		ArrayList<HantoCoordinateImpl> n2 = to.getNeighbors();
		ArrayList<HantoCoordinateImpl> common = new ArrayList <HantoCoordinateImpl> ();
		
		for (HantoCoordinateImpl c: n1){
			if(n2.contains(c) && board.checkEmpty(c)){
				common.add(c);
			}
		}
		
		if(common.size() == 0){
			throw new HantoException("The piece cannot be move to the position because two other pieces already at each side");
		}
	}
	

}
