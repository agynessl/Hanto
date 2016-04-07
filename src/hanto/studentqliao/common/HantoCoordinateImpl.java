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
import java.util.Collection;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

/**
 * The implementation for Beta Hanto HantoCoordinate
 * @version Mar 29, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	private final int x, y;
	
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

	/**
	 * return the neighbor of this coordinate in the specified direction
	 * @param direction
	 * @return HantoCoordinateImpl
	 * @throws HantoException
	 */
	public HantoCoordinateImpl getNeighbor(Direction direction){
		int outx = 0, outy = 0;
		switch(direction){
		case North: 
			outx = x;
			outy = y + 1;
		break;
		case NorthWest: 
			outx = x + 1;
			outy = y;
		break;
		case SouthWest: 
			outx = x + 1;
			outy = y - 1;
		break;
		case South: 
			outx = x;
			outy = y - 1;
		break;
		case SouthEast: 
			outx = x - 1;
			outy = y;
		break;
		case NorthEast: 
			outx = x - 1;
			outy = y + 1;
		break;
		
		}
		return new HantoCoordinateImpl(outx, outy);
	}
	
	/**
	 * return the neighbors of this coordinate in the specified direction
	 * @param direction
	 * @return HantoCoordinateImpl
	 * @throws HantoException
	 */
	public ArrayList<HantoCoordinateImpl> getNeighbors(){
		ArrayList<HantoCoordinateImpl> neighbors = new ArrayList<HantoCoordinateImpl>();
		for(Direction d: Direction.values()){
			neighbors.add(getNeighbor(d));
		}
		return neighbors;
	}

}
