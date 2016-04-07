package hanto.studentqliao.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class WalkValidator extends MoveValidator{

	
	


	@Override
	public boolean canMove(HantoBoard board, HantoCoordinate from, HantoCoordinate to, HantoPlayerColor onMove,
			HantoPieceType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkEmptyDestination() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkButterflyMoveByFourth() {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}
