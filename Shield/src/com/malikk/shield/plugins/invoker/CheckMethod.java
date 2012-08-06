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

package com.malikk.shield.plugins.invoker;

public enum CheckMethod {
	GET_REGIONS("getRegions", OutcomeType.REGION_ARRAY),
	IS_IN_REGION("isInRegion", OutcomeType.BOOLEAN_ARRAY),
	CAN_BUILD("canBuild", OutcomeType.BOOLEAN_ARRAY),
	CAN_OPEN("canOpen", OutcomeType.BOOLEAN_ARRAY),
	CAN_USE("canUse", OutcomeType.BOOLEAN_ARRAY),
	GET_MAX_LOC("getMaxLoc", OutcomeType.LOCATION),
	GET_MIN_LOC("getMinLoc", OutcomeType.LOCATION),
	CONTAINS_LOC("contains", OutcomeType.BOOLEAN_ARRAY);
	
	private String name = null;
	private OutcomeType outcome = null;
	
	CheckMethod(String name, OutcomeType outcome){
		this.name = name;
		this.outcome = outcome;
	}
	
	public String getName(){
		return name;
	}
	
	public OutcomeType getType(){
		return outcome;
	}
}
