package us.twoguys.shield;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Shield extends JavaPlugin{
	
	//Logger
	private Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable(){
		
	}
	
	public void onDisable(){
		
	}
	
	public void log(String msg){
		PluginDescriptionFile pdfile = this.getDescription();	
		log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logSevere(String msg){
		PluginDescriptionFile pdfile = this.getDescription();	
		log.severe("[" + pdfile.getName() + "] " + msg);
	}

}
