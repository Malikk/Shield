package us.twoguys.shield.flags;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.entity.Player;

import us.twoguys.shield.Shield;
import us.twoguys.shield.exceptions.FlagNotFoundException;
import us.twoguys.shield.exceptions.InvalidFlagException;
import us.twoguys.shield.exceptions.InvalidRegionException;

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
	
	public void createFlag(String flag, String region, ArrayList<Player> players, boolean value){
		Flag f = new Flag(flag, region, players, value);
		flags.add(f);
	}
	
	public boolean hasFlag(Player player, String flag, String region){
		Flag f = null;
		
		try {
			f = getFlag(flag, region);
		} catch (FlagNotFoundException e) {
			e.printStackTrace();
		}
		
		boolean fValue = f.getValue();
		
		if (f.getPlayers().contains(player)){
			return fValue;
		}else{
			return !fValue;
		}
		
	}
	
	public Flag getFlag(String flag, String region) throws FlagNotFoundException{
		
		//Check if flag is valid
		if (!isValidFlag(flag)){
			
			try {
				throw new InvalidFlagException();
			} catch (InvalidFlagException e) {
				e.printStackTrace();
			}
		}
		
		//Check if Region is valid
		if (!plugin.pm.isValidRegion(region)){
			try {
				throw new InvalidRegionException();
			} catch (InvalidRegionException e) {
				e.printStackTrace();
			}
		}
		
		for (Flag f: flags){
			if (f.getName().equalsIgnoreCase(flag) && f.getRegion().equalsIgnoreCase(region)){
				return f;
			}
		}
		
		throw new FlagNotFoundException();
	}
	
	
}
