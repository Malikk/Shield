package us.twoguys.shield;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.shield.plugins.*;

public class Shield extends JavaPlugin{
	
	private Logger log = Logger.getLogger("Minecraft");
	private ServicesManager sm;
	
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
		if (packageExists("com.sk89q.worldguard")) {
			Protection pro = new WorldGuard(this);
			sm.register(Protection.class, pro, this, ServicePriority.Normal);
			log.info(String.format("[%s] WorldGuard found: %s", getDescription().getName(), pro.isEnabled() ? "Loaded" : "Waiting"));
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
