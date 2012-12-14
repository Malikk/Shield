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

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.exceptions.RegionNotFoundException;
import com.malikk.shield.regions.ShieldRegion;

public class ProtectionManager {

	Shield shield;
	public static HashSet<Protect> plugins = new HashSet<Protect>();

	public ProtectionManager(Shield instance){
		shield = instance;
	}

	public HashSet<ShieldRegion> getRegions() throws RegionNotFoundException{
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		for (Protect protect: plugins){
			regions.addAll(protect.getRegions());
		}

		return validateSet(regions);
	}

	public HashSet<ShieldRegion> getRegions(Entity entity) throws RegionNotFoundException{
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		for (Protect protect: plugins){
			regions.addAll(protect.getRegions(entity));
		}

		return validateSet(regions);
	}

	public HashSet<ShieldRegion> getRegions(Location loc) throws RegionNotFoundException{
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		for (Protect protect: plugins){
			regions.addAll(protect.getRegions(loc));
		}

		return validateSet(regions);
	}

	public boolean isInRegion(Entity entity) {
		for (Protect protect: plugins){
			if (protect.isInRegion(entity)){
				return true;
			}
		}

		return false;
	}

	public boolean isInRegion(Location loc) {
		for (Protect protect: plugins){
			if (protect.isInRegion(loc)){
				return true;
			}
		}

		return false;
	}

	public boolean canBuild(Player player) {
		for (Protect protect: plugins){
			if (!protect.canBuild(player)){
				return false;
			}
		}

		return true;
	}

	public boolean canBuild(Player player, Location loc) {
		for (Protect protect: plugins){
			if (!protect.canBuild(player, loc)){
				return false;
			}
		}

		return true;
	}

	public boolean canUse(Player player) {
		for (Protect protect: plugins){
			if (!protect.canUse(player)){
				return false;
			}
		}

		return true;
	}

	public boolean canUse(Player player, Location loc) {
		for (Protect protect: plugins){
			if (!protect.canUse(player, loc)){
				return false;
			}
		}

		return true;
	}

	public boolean canOpen(Player player) {
		for (Protect protect: plugins){
			if (!protect.canOpen(player)){
				return false;
			}
		}

		return true;
	}

	public boolean canOpen(Player player, Location loc) {
		for (Protect protect: plugins){
			if (!protect.canOpen(player, loc)){
				return false;
			}
		}

		return true;
	}

	/*
	 * Util
	 */
	public void addClassToInstantiatedSet(Protect protect){
		if (protect != null){
			plugins.add(protect);
		}
	}

	public HashSet<ShieldRegion> validateSet(HashSet<ShieldRegion> set) throws RegionNotFoundException{
		if (set.size() != 0){
			return set;
		}else{
			throw new RegionNotFoundException();
		}
	}

	public Protect getProtectObjectFromName(String plugin){
		for (Protect protect: plugins){
			if (protect.getPluginName().equalsIgnoreCase(plugin)){
				return protect;
			}
		}
		return null;
	}

}
