package hanto.studentqliao.common;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class SparrowValidator extends MoveValidator{

	public SparrowValidator(HantoGameID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{
		//pieceTypeChecker(type);
		HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		if(from == null){
			checkPutPiece(board,dest,onMove,type);
		}
		else{
			HantoCoordinateImpl origin = new HantoCoordinateImpl(from);
			checkFly(board,origin,dest,onMove,type);
		}
		
	}
	
	/**
	 * 
	 * @param type
	 * @throws HantoException
	 */
	/**
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
	*/
	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkFly(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{	
		checkButterflyPlayed(board,onMove,type);
		checkEmptyDestination(board,to);
		checkPieceOnBoard(board,from,onMove,type);
		//checkMovable(board,from,to);
		//checkDistance(from,to);
		checkConnected(board,from,to);
		
	}
	
	/**
	 * 
	 * @param board
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkButterflyPlayed(HantoBoard board, HantoPlayerColor onMove, HantoPieceType type) throws HantoException{
		if(board.getPieceCount(HantoPieceType.BUTTERFLY,onMove) == 0){
			throw new HantoException("Please play butterfly before move the piece");
		}	
	}
	

	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkPieceOnBoard(HantoBoard board, HantoCoordinateImpl from,HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{
		if(board.getPieceAt(from) == null || board.getPieceAt(from).getColor() != onMove || board.getPieceAt(from).getType() != type){
			throw new HantoException ("no such piece on the given from coordinate");
		}
	}
	
	
}
