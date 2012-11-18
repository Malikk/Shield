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
import com.malikk.shield.groups.ShieldGroup;
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

	public void createFlag(String flag, ShieldRegion region, ShieldGroup group, boolean value) throws InvalidFlagException{

		if (!isValidFlag(flag)){
			throw new InvalidFlagException();
		}

		Flag f1 = new Flag(flag, region, group, value);

		for (Flag f2: flags){
			if (f1.getName().equalsIgnoreCase(f2.getName()) && plugin.rm.regionsAreEqual(f1.getRegion(), f2.getRegion())){
				consolidateFlags(f2, f1);
				return;
			}
		}

		flags.add(f1);
	}

	public void createFlag(String flag, ShieldRegion region, HashSet<String> players, boolean value) throws InvalidFlagException{
		ShieldGroup group = new ShieldGroup();
		group.addPlayerNames(players);
		createFlag(flag, region, group, value);
	}

	public void createFlag(String flag, ShieldRegion region, String player, boolean value) throws InvalidFlagException{
		HashSet<String> players = new HashSet<String>();
		players.add(player);
		createFlag(flag, region, players, value);
	}

	public boolean getFlagAndValue(Player player, String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException {

		Flag f = getFlag(flag, region);

		return getValue(player, f);
	}

	public boolean getValue(Player player, Flag f){
		boolean fValue = f.getValue();

		if (f.getShieldGroup().contains(player)){
			return fValue;
		}else{
			return !fValue;
		}
	}

	public Flag getFlag(String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException {

		//Check if flag is valid
		if (!isValidFlag(flag)){
			throw new InvalidFlagException();
		}

		for (Flag f: flags){
			if (f.getName().equalsIgnoreCase(flag) && plugin.rm.regionsAreEqual(f.getRegion(), region)){
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

	public void consolidateFlags(Flag f2, Flag f1){
		ShieldGroup g1 = f1.getShieldGroup();
		ShieldGroup g2 = f2.getShieldGroup();

		if (g1.getPlayerNames().size() == 0){
			return;
		}

		if (f1.getValue() == f2.getValue()){
			g2.addPlayerNames(g1.getPlayerNames());
		}else{
			g2.removePlayerNames(g1.getPlayerNames());
		}
	}

}
