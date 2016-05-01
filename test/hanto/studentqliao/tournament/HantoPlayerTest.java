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

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentqliao.tournament.HantoPlayer;
import hanto.tournament.HantoMoveRecord;

import org.junit.Test;

public class HantoPlayerTest {
	@Test
	public void testPlay1(){
		HantoPlayer player1 = new HantoPlayer();
		HantoPlayer player2 = new HantoPlayer();
		
		player1.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		player2.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, false);
		
		HantoMoveRecord record;
		record = player1.makeMove(null);
		
		for (int i = 0; i < 100; i++) {
			switch (i % 2) {
			case 0:
				record = player2.makeMove(record);
				break;
			case 1:
				record = player1.makeMove(record);
				break;
			}
		}
	}
	@Test
	public void testPlay2(){
		HantoPlayer player1 = new HantoPlayer();
		HantoPlayer player2 = new HantoPlayer();
		
		player1.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, true);
		player2.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, false);
		
		HantoMoveRecord record;
		record = player1.makeMove(null);
		
		for (int i = 0; i < 100; i++) {
			switch (i % 2) {
			case 0:
				record = player2.makeMove(record);
				break;
			case 1:
				record = player1.makeMove(record);
				break;
			}
		}
	}

}