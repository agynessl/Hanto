package hanto.studentqliao.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentqliao.common.ButterflyValidator;
import hanto.studentqliao.common.CrabValidator;
import hanto.studentqliao.common.HantoGameBase;
import hanto.studentqliao.common.MoveValidator;
import hanto.studentqliao.common.SparrowValidator;

public class DeltaHantoGame extends HantoGameBase{

	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MoveValidator getMoveValidator(HantoPieceType type) {
		MoveValidator mv = null;
		if(type == HantoPieceType.BUTTERFLY){
			mv = new ButterflyValidator(HantoGameID.DELTA_HANTO);
		}
		else if(type == HantoPieceType.CRAB){
			mv = new CrabValidator(HantoGameID.DELTA_HANTO);			
		}
		else if(type == HantoPieceType.SPARROW){
			mv = new SparrowValidator(HantoGameID.DELTA_HANTO);
		}
		return mv;
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		if(pieceType == null && from == null && to == null){
			return onMove == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		}
		//game end check
		checkGameEnd();		
		//move check
		validateMove(pieceType,from,to);
		//make the move
		doMove(pieceType,from,to);
		//increment the count
		incrementMove();
		//return result
		return checkResult();
	}

}
