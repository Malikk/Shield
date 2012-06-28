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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.regions;

import org.bukkit.World;

/**
 * A ShieldRegion is simply an object which contains the association between the name of a region and the plugin is comes from. The purpose is to avoid conflicts with region names across protection plugins.
 * @author Malikk
 *
 * @see {@link #getName()}
 * @see {@link #getPluginName()}
 * @see {@link #getWorld()} 
 */
public class ShieldRegion {
	
	private String name, protect;
	private World world;
	
	public ShieldRegion (String name, String protect, World world){
		this.name = name;
		this.protect = protect;
		this.world = world;
	}
	
	/**
	 * Gets the name of the region.
	 * 
	 * @return String - name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the name of the plugin the region belongs to.
	 * 
	 * @return String - plugin name
	 */
	public String getPluginName(){
		return protect;
	}
	
	/**
	 * Gets the World the region is in.
	 * 
	 * @return World - world
	 */
	public World getWorld(){
		return world;
	}
}
