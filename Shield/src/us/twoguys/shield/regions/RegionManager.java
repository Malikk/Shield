package us.twoguys.shield.regions;

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
	
	public boolean regionsAreEqual(ShieldRegion region1, ShieldRegion region2){
		if (region1.getName() == region2.getName() && region1.getPluginName() == region2.getPluginName()){
			return true;
		}else{
			return false;
		}
	}
}
