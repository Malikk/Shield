package com.malikk.shield;

import org.bukkit.configuration.file.FileConfiguration;

import com.malikk.shield.regions.ShieldRegion;


import java.util.ArrayList;

public class ShieldConfig {

	Shield plugin;
	private FileConfiguration config;
	
	public ShieldConfig(Shield instance){
		plugin = instance;
	}
	
	public void loadConfig(){
		config = plugin.getConfig();
		
		config.options().header("\n Shield v" + plugin.pdfile.getVersion() + "\n By Malikk \n ");
		
		config.addDefault("Priority.1", "WorldGuard");
		config.addDefault("Priority.2", "Residence");
		config.addDefault("Priority.3", "Regios");
		config.addDefault("Priority.4", "PreciousStones");
		
		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public ShieldRegion getHighestPriority(ArrayList<ShieldRegion> regions){
		int counter = 1;
		
		plugin.log("Amount of regions passed in: " + regions.size());
		if (regions.size() == 0){
			return null;
		}else if (regions.size() == 1){
			return regions.get(0);
		}
		
		while (config.get("Priority." + counter) != null){
			
			plugin.log("Priority " + counter + "-----------");
			
			for (ShieldRegion region: regions){
				
				plugin.log("Region name: " + region.getName());
				
				String name = region.getPluginName();
				
				if (name.equalsIgnoreCase(config.getString("Priority." + counter))){
					
					plugin.log("Is priority");
					
					if (name.equalsIgnoreCase("WorldGuard")){
						
						plugin.log("Priority is WorldGuard");
						
						ArrayList<ShieldRegion> worldGuardRegions = new ArrayList<ShieldRegion>();
						
						for (ShieldRegion region2: regions){
							
							if (region2.getPluginName().equalsIgnoreCase("WorldGuard")){
								worldGuardRegions.add(region2);
							}
						}
						
						if (worldGuardRegions.size() == 1){
							return region;
						}
						
						return plugin.worldGuard.getHighestPriority(worldGuardRegions);
					}else{
						return region;
					}
				}
			}
			
			counter++;
			
		}
		return null;
	}
}
