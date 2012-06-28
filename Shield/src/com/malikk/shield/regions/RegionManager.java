package us.twoguys.shield.regions;

import java.util.ArrayList;

import org.bukkit.World;

import us.twoguys.shield.Shield;
import us.twoguys.shield.exceptions.RegionNotFoundException;

public class RegionManager {

	Shield plugin;
	
	public RegionManager(Shield instance){
		plugin = instance;
	}
	
	//Always valid or Exception is thown
	public ShieldRegion getShieldRegion(String name, String protect) throws RegionNotFoundException{
		for (ShieldRegion region: plugin.pm.getRegions()){
			if (region.getName().equalsIgnoreCase(name) && region.getPluginName().equalsIgnoreCase(protect)){
				return region;
			}
		}
		
		//If no regions are found
		throw new RegionNotFoundException();
	}
	
	public ArrayList<ShieldRegion> getShieldRegions(String name) throws RegionNotFoundException{
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		for (ShieldRegion region: plugin.pm.getRegions()){
			if (region.getName().equalsIgnoreCase(name)){
				regions.add(region);
			}
		}
		
		if (regions.size() != 0){
			return regions;
		}else{
			throw new RegionNotFoundException();
		}
		
	}
	
	//Not always Valid
	public ShieldRegion createShieldRegion(String name, String protect, World world){
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
