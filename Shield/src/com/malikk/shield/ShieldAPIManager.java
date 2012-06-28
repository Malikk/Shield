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

package com.malikk.shield;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.exceptions.FlagNotFoundException;
import com.malikk.shield.exceptions.InvalidFlagException;
import com.malikk.shield.exceptions.InvalidRegionException;
import com.malikk.shield.exceptions.RegionNotFoundException;
import com.malikk.shield.flags.Flag;
import com.malikk.shield.regions.ShieldRegion;


public class ShieldAPIManager implements ShieldAPI{
	
	Shield plugin;
	
	public ShieldAPIManager(Shield instance){
		plugin = instance;
	}
	
	/*
	 * Plugin Methods
	 */
	
	@Override
	public ShieldRegion getShieldRegion(String name, String pluginName) throws RegionNotFoundException {
		return plugin.rm.getShieldRegion(name, pluginName);
	}

	@Override
	public ShieldRegion getPriorityRegion() {
		return plugin.config.getHighestPriority(getRegions());
	}

	@Override
	public ShieldRegion getPriorityRegion(String name) throws RegionNotFoundException {
		return plugin.config.getHighestPriority(plugin.rm.getShieldRegions(name));
	}

	@Override
	public ShieldRegion getPriorityRegion(ArrayList<ShieldRegion> regions){
		return plugin.config.getHighestPriority(regions);
	}
	
	@Override
	public ShieldRegion getPriorityRegion(Entity entity){
		return plugin.config.getHighestPriority(getRegions(entity));
	}
	
	@Override
	public ShieldRegion getPriorityRegion(Location loc){
		return plugin.config.getHighestPriority(getRegions(loc));
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(){
		return plugin.pm.getRegions();
	}
	
	@Override
	public ArrayList<ShieldRegion> getRegions(String name) throws RegionNotFoundException {
		return plugin.rm.getShieldRegions(name);
	}

	@Override
	public ArrayList<ShieldRegion> getRegions(Entity entity) {
		return plugin.pm.getRegions(entity);
	}

	@Override
	public ArrayList<ShieldRegion> getRegions(Location loc) {
		return plugin.pm.getRegions(loc);
	}

	@Override
	public boolean isInRegion(Entity entity) {
		return plugin.pm.isInRegion(entity);
	}

	@Override
	public boolean isInRegion(Location loc) {
		return plugin.pm.isInRegion(loc);
	}
	
	@Override
	public boolean canBuild(Player player) {
		return plugin.pm.canBuild(player);
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return plugin.pm.canBuild(player, loc);
	}

	@Override
	public boolean canUse(Player player) {
		return plugin.pm.canUse(player);
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		return plugin.pm.canUse(player, loc);
	}

	@Override
	public boolean canOpen(Player player) {
		return plugin.pm.canOpen(player);
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		return plugin.pm.canOpen(player, loc);
	}

	/*
	 * Flag Methods
	 */
	
	@Override
	public void addValidFlag(String flag){
		plugin.fm.addValidFlag(flag);
	}
	
	@Override 
	public boolean isValidFlag(String flag){
		return plugin.fm.isValidFlag(flag);
	}

	@Override
	public void setFlag(String flag, ShieldRegion region, ArrayList<Player> players, boolean value) {
		plugin.fm.createFlag(flag, region, players, value);
	}

	@Override
	public boolean getFlagValue(Player player, String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException, InvalidRegionException {
		return plugin.fm.getFlagAndValue(player, flag, region);
	}
	
	@Override
	public boolean getFlagValue(Player player, Flag flag){
		return plugin.fm.getValue(player, flag);
	}

	@Override
	public Flag getFlag(String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException, InvalidRegionException {
		return plugin.fm.getFlag(flag, region);
	}

}
