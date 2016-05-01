/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.common;

/**
 * Exception that is only thrown when a player resigns while there is still a valid
 * move available for that player.
 * @version Oct 6, 2014
 */
public class HantoPrematureResignationException extends HantoException
{
	public HantoPrematureResignationException()
	{
		super("You resigned when you have a valid move available.");
	}
}
