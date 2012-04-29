package us.twoguys.shield;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.shield.plugins.*;

public class Shield extends JavaPlugin{
	
	private Logger log = Logger.getLogger("Minecraft");
	private ServicesManager sm = Bukkit.getServicesManager();
	
	public void onEnable(){
		loadPlugins();
		
		log("Enabled");
	}
	
	public void onDisable(){
		log("Disabled");
	}
	
	public void log(String msg){
		PluginDescriptionFile pdfile = this.getDescription();	
		log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logSevere(String msg){
		PluginDescriptionFile pdfile = this.getDescription();	
		log.severe("[" + pdfile.getName() + "] " + msg);
	}
	
	private void loadPlugins(){
		//Attempt to load WorldGuard
		if (packageExists("com.sk89q.worldguard.bukkit.WorldGuardPlugin")) {
			Protection protect = new Protect_WorldGuard(this);
			sm.register(Protection.class, protect, this, ServicePriority.Normal);
			log(String.format("WorldGuard found: %s", protect.isEnabled() ? "Loaded" : "Waiting"));
		}else{
			log("No supported protection plugins found.");
		}
	}
	
	private static boolean packageExists(String...packages) {
		try {
			for (String pkg : packages) {
				Class.forName(pkg);
			}
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
