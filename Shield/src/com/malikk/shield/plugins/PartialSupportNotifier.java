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

package com.malikk.shield.plugins;

import java.util.ArrayList;

import com.malikk.shield.Shield;

public class PartialSupportNotifier {

	Shield plugin;
	
	ArrayList<String> known = new ArrayList<String>();
	
	public PartialSupportNotifier(Shield instance){
		plugin = instance;
	}
	
	public void incompatible(String name, String method, String lookingFor, String instead){
		String warning = name + "." + method;
		
		if (!known.contains(warning) && plugin.config.AlertsAreEnabled()){
			plugin.logWarning(String.format("%s API does not support '%s' for %s. Instead, %s", name, method, lookingFor, instead));
			known.add(warning);
		}
		
	}
	
}
