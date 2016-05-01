/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentqliao.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentqliao.HantoGameFactory;
import hanto.studentqliao.tournament.HantoPlayer;
import hanto.tournament.HantoMoveRecord;

import org.junit.Before;
import org.junit.Test;

public class HantoPlayerTest {
	private HantoPlayer p1;
	private HantoPlayer p2;
	private HantoGame game;

	@Before
	public void setup() {
		game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO,
				HantoPlayerColor.BLUE);
		p1 = new HantoPlayer();
		p2 = new HantoPlayer();
		p1.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		p2.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, false);
	}
	
	@Test
	public void testPlay1() throws HantoException{
		MoveResult result;
		HantoMoveRecord record;
		record = p1.makeMove(null);
		
		for (int i = 0; i < 100; i++) {
			result = game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
			if (result != MoveResult.OK) {
				return;
			}
			switch (i % 2) {
			case 0:
				record = p2.makeMove(record);
				break;
			case 1:
				record = p1.makeMove(record);
				break;
			}
				
		}		
	}
	
	@Test
	public void testPlay2() throws HantoException{
		MoveResult result;
		HantoMoveRecord record;
		record = p1.makeMove(null);
		
		for (int i = 0; i < 100; i++) {
			result = game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
			if (result != MoveResult.OK) {
				return;
			}
			switch (i % 2) {
			case 0:
				record = p2.makeMove(record);
				break;
			case 1:
				record = p1.makeMove(record);
				break;
			}
			
		}
	}

}