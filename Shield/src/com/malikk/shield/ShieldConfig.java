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

package com.malikk.shield;

import org.bukkit.configuration.file.FileConfiguration;

import com.malikk.shield.regions.ShieldRegion;

import java.util.HashSet;

public class ShieldConfig {

	Shield plugin;
	private FileConfiguration config;
	
	public ShieldConfig(Shield instance){
		plugin = instance;
	}
	
	public void loadConfig(){
		config = plugin.getConfig();
		
		config.options().header("\n Shield v" + plugin.getDescription().getVersion() + "\n By Malikk \n \n " +
				"AlertsEnabled: \n" +
				"     This section defines whether or not you are notified in the server console when a plugin using Shield calls a method that a protection plugin on your server does not support.\n \n" +
				"Priority: \n" +
				"     This section sets which plugins have greater priority in the event of overlapping regions.\n \n");
		
		config.addDefault("AlertsEnabled", true);
		
		config.addDefault("Priority.1", "WorldGuard");
		config.addDefault("Priority.2", "Residence");
		config.addDefault("Priority.3", "Regios");
		config.addDefault("Priority.4", "PreciousStones");
		
		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public ShieldRegion getHighestPriority(HashSet<ShieldRegion> regions){
		int counter = 1;
		
		//plugin.log("Amount of regions passed in: " + regions.size());
		if (regions.size() == 1){
			for (ShieldRegion region: regions){
				return region;
			}
		}
		
		while (config.get("Priority." + counter) != null){
			
			//plugin.log("Priority " + counter + "-----------");
			
			for (ShieldRegion region: regions){
				
				//plugin.log("Region name: " + region.getName());
				
				String name = region.getPluginName();
				
				if (name.equalsIgnoreCase(config.getString("Priority." + counter))){
					
					//plugin.log("Is priority");
					
					if (name.equalsIgnoreCase("WorldGuard")){
						
						//plugin.log("Priority is WorldGuard");
						
						HashSet<ShieldRegion> worldGuardRegions = new HashSet<ShieldRegion>();
						
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
	
	public boolean AlertsAreEnabled(){
		if (config.getBoolean("AlertsEnabled")){
			return true;
		}else{
			return false;
		}
	}
}
