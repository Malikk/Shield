package com.malikk.shield.flags;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.exceptions.FlagNotFoundException;
import com.malikk.shield.exceptions.InvalidFlagException;
import com.malikk.shield.exceptions.InvalidRegionException;
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
	
	public void createFlag(String flag, ShieldRegion region, ArrayList<Player> players, boolean value){
		Flag f = new Flag(flag, region, players, value);
		
		for (Flag f1: flags){
			if (f1.getName().equalsIgnoreCase(flag) && plugin.rm.regionsAreEqual(region, f1.getRegion())){
				flags.remove(f1);
			}
		}
		
		flags.add(f);
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
		if (!plugin.pm.isValidRegion(region)){
			throw new InvalidRegionException();
		}
		
		for (Flag f: flags){
			if (f.getName().equalsIgnoreCase(flag) && f.getRegion() == region){
				return f;
			}
		}
		
		throw new FlagNotFoundException();
	}
	
	
}
