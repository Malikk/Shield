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
import com.malikk.shield.exceptions.RegionNotFoundException;
import com.malikk.shield.plugins.invoker.CheckMethod;
import com.malikk.shield.plugins.invoker.MethodInvoker;
import com.malikk.shield.regions.ShieldRegion;

public class ProtectionManager implements Protect{
	
	Shield shield;
	
	public ProtectionManager(Shield instance){
		shield = instance;
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions() throws RegionNotFoundException{
		Object[] args = {};
		
		@SuppressWarnings("unchecked")
		ArrayList<ShieldRegion> outcomes = (ArrayList<ShieldRegion>) getOutcomes(CheckMethod.GET_REGIONS, args, null);
		
		return validateArrayList(outcomes);
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(Entity entity) throws RegionNotFoundException{
		Object[] args = {entity};
		
		@SuppressWarnings("unchecked")
		ArrayList<ShieldRegion> outcomes = (ArrayList<ShieldRegion>) getOutcomes(CheckMethod.GET_REGIONS, args, null);
		
		return validateArrayList(outcomes);
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(Location loc) throws RegionNotFoundException{
		Object[] args = {loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<ShieldRegion> outcomes = (ArrayList<ShieldRegion>) getOutcomes(CheckMethod.GET_REGIONS, args, null);
		
		return validateArrayList(outcomes);
	}
	
	/**
	 * Returns true if ANY Protect classes return true
	 */
	@Override
	public boolean isInRegion(Entity entity) {
		Object[] args = {entity};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.IS_IN_REGION, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.IS_IN_REGION, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CAN_BUILD, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CAN_BUILD, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CAN_USE, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CAN_USE, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CAN_OPEN, args, null);
		
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
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CAN_OPEN, args, null);
		
		for (boolean outcome: outcomes){
			if (outcome == false){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Location getMaxLoc(ShieldRegion region) {
		Object[] args = {region};
		return (Location) getOutcomes(CheckMethod.GET_MAX_LOC, args, region.getPluginName());
	}

	@Override
	public Location getMinLoc(ShieldRegion region) {
		Object[] args = {region};
		return (Location) getOutcomes(CheckMethod.GET_MIN_LOC, args, region.getPluginName());
	}

	/**
	 * Will only ever return one outcome, since plugin is always specified
	 */
	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		Object[] args = {region, loc};
		
		@SuppressWarnings("unchecked")
		ArrayList<Boolean> outcomes = (ArrayList<Boolean>) getOutcomes(CheckMethod.CONTAINS_LOC, args, region.getPluginName());
		
		return outcomes.get(0);
	}
	
	public void addClassToInstantiatedPluginClassesArrayList(String className){
		MethodInvoker.plugins.add("Protect_" + className);
	}
	
	public Object getOutcomes(CheckMethod check, Object[] args, String plugin){
		MethodInvoker invoker = new MethodInvoker(shield);
		return invoker.invoke(check, args, plugin);
	}
	
	public ArrayList<ShieldRegion> validateArrayList(ArrayList<ShieldRegion> outcomes) throws RegionNotFoundException{
		if (outcomes.size() != 0){
			return outcomes;
		}else{
			throw new RegionNotFoundException();
		}
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
	
	@Override
	public String getVersion() {
		// Dont use
		return null;
	}

}
