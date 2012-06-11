package us.twoguys.shield;

import org.bukkit.configuration.file.FileConfiguration;

import us.twoguys.shield.regions.ShieldRegion;

import java.util.ArrayList;

public class ShieldConfig {

	Shield plugin;
	private FileConfiguration config;
	
	public ShieldConfig(Shield instance){
		plugin = instance;
		config = plugin.getConfig();
	}
	
	public void loadConfig(){
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
		
		while (config.get("Priority." + counter) != null){
			for (ShieldRegion region: regions){
				
				String name = region.getPluginName();
				
				if (name.equalsIgnoreCase(config.getString("Priority." + counter))){
					
					if (name.equalsIgnoreCase("WorldGuard")){
						
						ArrayList<ShieldRegion> worldGuardRegions = new ArrayList<ShieldRegion>();
						
						for (ShieldRegion region2: regions){
							
							if (name.equalsIgnoreCase("WorldGuard")){
								worldGuardRegions.add(region2);
							}
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
