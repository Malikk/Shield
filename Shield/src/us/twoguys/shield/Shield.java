package us.twoguys.shield;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import us.twoguys.shield.flags.*;
import us.twoguys.shield.plugins.*;
import us.twoguys.shield.regions.RegionManager;

public class Shield extends JavaPlugin{
	
	protected Logger log = Logger.getLogger("Minecraft");
	PluginDescriptionFile pdfile = null;
	
	private boolean foundPlugin = false;
	
	//Plugin Classes
	public static Protect_PreciousStones preciousStones = null;
	public static Protect_Regios regios = null;
	public static Protect_Residence residence = null;
	public static Protect_WorldGuard worldGuard = null;
	
	//Shield Classes
	public IncompatibilityHandler incompat = new IncompatibilityHandler(this);
	public FlagPersister flagPersister = new FlagPersister(this);
	public ShieldConfig config = new ShieldConfig(this);
	public ShieldPluginManager pm = new ShieldPluginManager(this);
	public FlagManager fm = new FlagManager(this);
	public RegionManager rm = new RegionManager(this);
	
	public void onEnable(){
		registerAPI();
		
		pdfile = this.getDescription();
		
		loadPlugins();
		
		config.loadConfig();
		
		//flagPersister.load();
		
		log("Enabled");
	}
	
	public void onDisable(){
		flagPersister.save();
		
		log("Disabled");
	}
	
	public void log(String msg){
		log.info("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logWarning(String msg){
		log.warning("[" + pdfile.getName() + "] " + msg);
	}
	
	public void logSevere(String msg){
		log.severe("[" + pdfile.getName() + "] " + msg);
	}
	
	private void registerAPI(){
		ShieldAPI api = new ShieldAPIManager(this);
		Bukkit.getServicesManager().register(ShieldAPI.class, api, this, ServicePriority.Normal);
	}
	
	public ShieldAPI getAPI(){
		RegisteredServiceProvider<ShieldAPI> provider = getServer().getServicesManager().getRegistration(us.twoguys.shield.ShieldAPI.class);
        ShieldAPI api = provider.getProvider();
        return api;
	}
	
	private void loadPlugins(){
		
		log("Scanning for supported protection plugins...");
		
		//Attempt to load PreciousStones
		if (packageExists("net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones")){
			preciousStones = new Protect_PreciousStones(this);
			log(String.format("Detected PreciousStones: %s", preciousStones.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Attempt to load Regios
		if (packageExists("couk.Adamki11s.Regios.Main.Regios")){
			regios = new Protect_Regios(this);
			log(String.format("Detected Regios: %s", regios.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Attempt to load Residence
		if (packageExists("com.bekvon.bukkit.residence.Residence")){
			residence = new Protect_Residence(this);
			log(String.format("Detected Residence: %s", residence.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		//Attempt to load WorldGuard
		if (packageExists("com.sk89q.worldguard.bukkit.WorldGuardPlugin")){
			worldGuard = new Protect_WorldGuard(this);
			log(String.format("Detected WorldGuard: %s", worldGuard.isEnabled() ? "Hooked" : "Waiting"));
		}
		
		if (foundPlugin == false){
			log("No supported protection plugins found.");
		}
	}
	
	private boolean packageExists(String...packages){
		try{
			for (String pkg : packages){
				Class.forName(pkg);
			}
			foundPlugin = true;
			return true;
		}catch (Exception e){
			return false;
		}
	}

}
