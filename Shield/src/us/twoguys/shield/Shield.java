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
	public ShieldPluginManager pm = new ShieldPluginManager(this);
	
	private boolean foundPlugin = false;
	
	//Plugin Classes
	public static Protect_PreciousStones preciousStones = null;
	public static Protect_Regios regios = null;
	public static Protect_WorldGuard worldGuard = null;
	
	public void onEnable(){
		registerAPI();
		
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
	
	private void registerAPI(){
		ShieldAPI api = new ShieldPluginManager(this);
		sm.register(ShieldAPI.class, api, this, ServicePriority.Normal);
	}
	
	private void loadPlugins(){
		//Try to load PreciousStones
		if (packageExists("net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones")){
			preciousStones = new Protect_PreciousStones(this);
			foundPlugin = true;
			log(String.format("Detected PreciousStones: %s", preciousStones.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Try to load Regios
		if (packageExists("couk.Adamki11s.Regios.Main.Regios")){
			regios = new Protect_Regios(this);
			log(String.format("Detected Regios: %s", regios.isEnabled() ? "Hooked" : "Waiting"));
			foundPlugin = true;
		}
		
		//Try to load WorldGuard
		if (packageExists("com.sk89q.worldguard.bukkit.WorldGuardPlugin")){
			worldGuard = new Protect_WorldGuard(this);
			log(String.format("Detected WorldGuard: %s", worldGuard.isEnabled() ? "Hooked" : "Waiting"));
			foundPlugin = true;
		}
		
		if (foundPlugin == false){
			log("No supported protection plugins found.");
		}
	}
	
	private static boolean packageExists(String...packages){
		try{
			for (String pkg : packages){
				Class.forName(pkg);
			}
			return true;
		}catch (Exception e){
			return false;
		}
	}

}
