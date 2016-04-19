package hanto.studentqliao.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class CrabValidator extends MoveValidator {
	List<HantoCoordinateImpl> movable;
	
	public CrabValidator(HantoGameID id){
		super(id);
		movable = new ArrayList<HantoCoordinateImpl>();
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
			checkWalk(board,origin,dest,onMove,type);
		}
		
	}
	
	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param to
	 * @param onMove
	 * @param type
	 * @throws HantoException
	 */
	public void checkWalk(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to, HantoPlayerColor onMove,
			HantoPieceType type) throws HantoException{	
		int distanceCount = 1;
		checkButterflyPlayed(board,onMove,type);
		checkEmptyDestination(board,to);
		checkPieceOnBoard(board,from,onMove,type);
		
		movable.add(from);
		while(distanceCount < 4){
			List<HantoCoordinateImpl> temp = new ArrayList<HantoCoordinateImpl>();
			for(HantoCoordinateImpl c: movable){
				temp.addAll(getMovable(board,from, c));				
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
		
		/**
		checkMovable(board,from,to);
		checkDistance(from,to);
		checkConnected(board,from,to);
		*/
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
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	/**
	public void checkDistance(HantoCoordinateImpl from, HantoCoordinateImpl to)throws HantoException{
		if(from.getDistance(to) != 1){
			throw new HantoException("The distance of walk should be 1");
		}
	}
	*/
	
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

	
	/**
	 * 
	 * @param board
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	/**
	public void checkMovable(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl to)throws HantoException{
		List<HantoCoordinateImpl> n1 = from.getNeighbors();
		List<HantoCoordinateImpl> n2 = to.getNeighbors();
		List<HantoCoordinateImpl> common = new ArrayList <HantoCoordinateImpl> ();
		
		for (HantoCoordinateImpl c: n1){
			if(n2.contains(c) && board.checkEmpty(c)){
				common.add(c);
			}
		}
		
		if(common.size() == 0){
			throw new HantoException("The piece cannot be move to the position because two other pieces already at each side");
		}
	}
	*/
	
	public List<HantoCoordinateImpl> getMovable(HantoBoard board, HantoCoordinateImpl from, HantoCoordinateImpl c) throws HantoException{
			List<HantoCoordinateImpl> empty = board.getEmptyNeighbors(c);
			
			for(Iterator<HantoCoordinateImpl> iterator = empty.iterator(); iterator.hasNext();){
				HantoCoordinateImpl hc = iterator.next();
				//first check connected
				HantoBoard newboard = new HantoBoard(board);
				newboard.movePiece(from,hc);
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
