/*
 * Copyright 2012 Jordan Hobgood
 * 
 * This file is part of Shield.
 *
 * Shield is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shield is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with Shield.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.exceptions;

/**
 * This exception is thrown when no regions could be found for the particular search
 * <p>
 * In the catch block, you should set the default action to take when this occurs.
 * @author Malikk
 *
 */
public class RegionNotFoundException extends Exception{

	private static final long serialVersionUID = -5506011525478825730L;

	public RegionNotFoundException(){
		super();
	}
}
