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

import java.util.ArrayList;

import org.bukkit.World;

import com.malikk.shield.Shield;
import com.malikk.shield.exceptions.RegionNotFoundException;


public class RegionManager {

	Shield plugin;
	
	public RegionManager(Shield instance){
		plugin = instance;
	}
	
	//Always valid or Exception is thown
	public ShieldRegion getShieldRegion(String name, String protect) throws RegionNotFoundException{
		for (ShieldRegion region: plugin.pm.getRegions()){
			if (region.getName().equalsIgnoreCase(name) && region.getPluginName().equalsIgnoreCase(protect)){
				return region;
			}
		}
		
		//If no regions are found
		throw new RegionNotFoundException();
	}
	
	public ArrayList<ShieldRegion> getShieldRegions(String name) throws RegionNotFoundException{
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		for (ShieldRegion region: plugin.pm.getRegions()){
			if (region.getName().equalsIgnoreCase(name)){
				regions.add(region);
			}
		}
		
		if (regions.size() != 0){
			return regions;
		}else{
			throw new RegionNotFoundException();
		}
		
	}
	
	//Not always Valid
	public ShieldRegion createShieldRegion(String name, String protect, World world){
		return new ShieldRegion(name, protect, world);
	}
	
	public boolean regionsAreEqual(ShieldRegion region1, ShieldRegion region2){
		if (region1.getName() == region2.getName() && region1.getPluginName() == region2.getPluginName()){
			return true;
		}else{
			return false;
		}
	}
}
