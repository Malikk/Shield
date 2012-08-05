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

package com.malikk.shield.flags;

import java.util.HashSet;

import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.exceptions.*;
import com.malikk.shield.regions.ShieldRegion;

public class FlagManager {

	Shield plugin;
	
	HashSet<Flag> flags = new HashSet<Flag>();
	HashSet<String> validFlags = new HashSet<String>();
	
	public FlagManager(Shield instance){
		plugin = instance;
	}
	
	public void addValidFlag(String flag){
		if (!validFlags.contains(flag)){
			validFlags.add(flag);
		}
	}
	
	public boolean isValidFlag(String flag){
		return (validFlags.contains(flag) ? true : false);
	}
	
	@SuppressWarnings("unchecked")
	public void createFlag(String flag, ShieldRegion region, HashSet<Player> players, boolean value){
		Flag f = new Flag(flag, region, convertPlayersToNames(players), value);
		
		for (Flag f1: (HashSet<Flag>)flags.clone()){
			if (f1.getName().equalsIgnoreCase(flag) && plugin.rm.regionsAreEqual(region, f1.getRegion())){
				consolidateFlags(f, f1);
				return;
			}
		}
		
		flags.add(f);
	}
	
	public void createFlag(String flag, ShieldRegion region, Player player, boolean value){
		HashSet<Player> players = new HashSet<Player>();
		players.add(player);
		createFlag(flag, region, players, value);
	}
	
	public boolean getFlagAndValue(Player player, String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException, InvalidRegionException{
		
		Flag f = getFlag(flag, region);
		
		return getValue(player, f);
	}
	
	public boolean getValue(Player player, Flag f){
		boolean fValue = f.getValue();
		
		if (f.getPlayers().contains(player)){
			return fValue;
		}else{
			return !fValue;
		}
	}
	
	public Flag getFlag(String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException, InvalidRegionException{
		
		//Check if flag is valid
		if (!isValidFlag(flag)){
			throw new InvalidFlagException();
		}
		
		//Check if Region is valid
		if (!plugin.rm.isValidRegion(region)){
			throw new InvalidRegionException();
		}
		
		for (Flag f: flags){
			if (f.getName().equalsIgnoreCase(flag) && f.getRegion() == region){
				return f;
			}
		}
		
		throw new FlagNotFoundException();
	}
	
	public HashSet<String> convertPlayersToNames(HashSet<Player> players){
		HashSet<String> set = new HashSet<String>();
		
		for (Player p: players){
			set.add(p.getName());
		}
		
		return set;
	}
	
	public void consolidateFlags(Flag f1, Flag f2){
		if (f1.getValue() == f2.getValue()){
			f1.addPlayers(f2.getPlayerNames());
		}else{
			f1.removePlayers(f2.getPlayerNames());
		}
	}
}
