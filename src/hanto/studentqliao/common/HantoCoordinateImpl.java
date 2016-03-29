/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentqliao.common;

import java.util.ArrayList;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

/**
 * The implementation for my version of Hanto.
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	final private int x, y;
	
	/**
	 * The only constructor.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * @param coordinate an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate)
	{
		this(coordinate.getX(), coordinate.getY());
	}
	
	public enum Direction{
		North,
		NorthEast,
		SouthEast,
		South,
		SouthWest,
		NorthWest
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 17;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HantoCoordinateImpl)) {
			return false;
		}
		final HantoCoordinateImpl other = (HantoCoordinateImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	public HantoCoordinateImpl getNeighbor(Direction direction) throws HantoException{
		int outx = 0, outy = 0;
		switch(direction){
		case North: outx = this.x; outy = this.y + 1; break;
		case NorthWest: outx = this.x + 1; outy = this.y; break;
		case SouthWest: outx = this.x + 1; outy = this.y - 1; break;
		case South: outx = this.x; outy = this.y - 1; break;
		case SouthEast: outx = this.x - 1; outy = this.y; break;
		case NorthEast: outx = this.x - 1; outy = this.y + 1; break;
		default: throw new HantoException("Enumeration reached the end");
		}
		return new HantoCoordinateImpl(outx,outy);
	}

}
