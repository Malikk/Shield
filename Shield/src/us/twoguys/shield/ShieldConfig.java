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
				if (region.getProtectionPluginName() == config.getString("Priority." + counter)){
					return region;
				}
			}
			
			counter++;
			
		}
		return null;
	}
}
