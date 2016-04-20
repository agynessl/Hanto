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
import java.util.List;

import hanto.common.HantoCoordinate;

/**
 * The implementation for All Hanto HantoCoordinate
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
	
	/**
	 */
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
		return x * 37 + y;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof HantoCoordinate){
			return x == ((HantoCoordinate)obj).getX() && y == ((HantoCoordinate)obj).getY();
		}
		else{
			return false;
		}
	}

	/**
	 * return the neighbor of this coordinate in the specified direction
	 * @param direction	
	 * @return HantoCoordinateImpl */
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
	 * @return HantoCoordinateImpl
	 */
	public List<HantoCoordinateImpl> getNeighbors(){
		final List<HantoCoordinateImpl> neighbors = new ArrayList<HantoCoordinateImpl>();
		for(Direction d: Direction.values()){
			neighbors.add(getNeighbor(d));
		}
		return neighbors;
	}
	
	/**
	 * Method getDistance.
	 * @param coor HantoCoordinate
	 * @return integer for the distance
	 */
	public int getDistance(HantoCoordinate coor){
		final int z1 = 0 - x - y;
		final int z2 = 0 - coor.getX() - coor.getY();

		return (Math.abs(x - coor.getX()) + Math.abs(y - coor.getY()) + Math.abs(z1 - z2)) / 2;
	}

}
