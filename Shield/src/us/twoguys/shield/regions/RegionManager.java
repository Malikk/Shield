package us.twoguys.shield.regions;

import java.util.ArrayList;

import org.bukkit.World;

import us.twoguys.shield.Shield;

public class RegionManager {

	Shield plugin;
	
	public RegionManager(Shield instance){
		plugin = instance;
	}
	
	public ShieldRegion createRegionObject(String name, String protect, World world){
		return new ShieldRegion(name, protect, world);
	}
	
	public ShieldRegion getHighestPriority(ArrayList<ShieldRegion> regions){
		//TODO
		return null;
	}
}
