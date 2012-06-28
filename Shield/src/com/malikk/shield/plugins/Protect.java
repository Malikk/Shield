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

package com.malikk.shield.plugins;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.regions.ShieldRegion;


public interface Protect {
	
	//List of methods that every protect class MUST have. Does NOT extend the actual Api.
	
	public boolean isEnabled();
	
	public String getPluginName();
	
	public ArrayList<ShieldRegion> getRegions();
	
	public ArrayList<ShieldRegion> getRegions(Entity entity);
	
	public ArrayList<ShieldRegion> getRegions(Location loc);
	
	public boolean isInRegion(Entity entity);
	
	public boolean isInRegion(Location loc);
	
	public boolean canBuild(Player player);
	
	public boolean canBuild(Player player, Location loc);
	
	public boolean canUse(Player player);
	
	public boolean canUse(Player player, Location loc);
	
	public boolean canOpen(Player player);
	
	public boolean canOpen(Player player, Location loc);
}
