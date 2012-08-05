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

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.regions.ShieldRegion;

public class PluginManager implements Protect{
	
	Shield shield;
	
	public PluginManager(Shield instance){
		shield = instance;
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(){
		Object[] args = {};
		
		@SuppressWarnings("unchecked")
		ArrayList<ShieldRegion> outcomes = (ArrayList<ShieldRegion>) getOutcomes("getRegions", "ArrayList<Region>", args, null);
		
		return outcomes;
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(Entity entity){
		Object[] args = {entity};
		
		@SuppressWarnings("unchecked")
		ArrayList<ShieldRegion> outcomes = (ArrayList<ShieldRegion>) getOutcomes("getRegions", "ArrayList<Region>", args, null);
		
		return outcomes;
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(Location loc){
		Object[] args = {loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<ShieldRegion> outcomes = (ArrayList<ShieldRegion>) getOutcomes("getRegions", "ArrayList<Region>", args, null);
		
		return outcomes;
	}
	
	/**
	 * Returns true if ANY Protect classes return true
	 */
	@Override
	public boolean isInRegion(Entity entity) {
		Object[] args = {entity};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("isInRegion", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == true){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if ANY Protect classes return true
	 */
	@Override
	public boolean isInRegion(Location loc) {
		Object[] args = {loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("isInRegion", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == true){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Only returns true if EVERY Protect class returns true
	 */
	@Override
	public boolean canBuild(Player player) {
		Object[] args = {player};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canBuild", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canBuild(Player player, Location loc) {
		Object[] args = {player, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canBuild", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canUse(Player player) {
		Object[] args = {player};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canUse", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canUse(Player player, Location loc) {
		Object[] args = {player, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canUse", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canOpen(Player player) {
		Object[] args = {player};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canOpen", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}

	/**
	 * Only returns true is EVERY Protect class returns true
	 */
	@Override
	public boolean canOpen(Player player, Location loc) {
		Object[] args = {player, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes("canOpen", "boolean", args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}
	
	public void addClassToInstantiatedPluginClassesArrayList(String className){
		MethodInvoker.plugins.add("Protect_" + className);
	}
	
	public ArrayList<?> getOutcomes(String method, String outcomeType, Object[] args, String plugin){
		MethodInvoker invoker = new MethodInvoker(shield);
		return invoker.invoke(method, outcomeType, args, plugin);
	}

	@Override
	public boolean isEnabled() {
		// Dont use
		return false;
	}

	@Override
	public String getPluginName() {
		// Dont use
		return null;
	}

}
