package us.twoguys.shield.plugins;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import couk.Adamki11s.Regios.API.RegiosAPI;
import couk.Adamki11s.Regios.Checks.Checks;
import couk.Adamki11s.Regios.Main.Regios;

import us.twoguys.shield.*;

public class Protect_Regios implements Listener, Protect {
	
	Shield shield;
	
	private final String name = "Regios";
	private final String pack = "couk.Adamki11s.Regios.Main.Regios";
	private static int instanceCount = 0;
	private static Regios protect = null;
	private static Checks checks = null;
	private static RegiosAPI api = null;
	
	public Protect_Regios(Shield instance){
		this.shield = instance;
		
		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);
		
		if (instanceCount == 0){
			//Load plugin if it was loaded before Shield
			if (protect == null) {
				Plugin p = shield.getServer().getPluginManager().getPlugin(name);
	            
				if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
					protect = (Regios) p;
					setupInterfaces();
					shield.pm.addClassToInstantiatedPluginClassesArrayList(name);
				}
			}
		}
		
		instanceCount++;
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (Regios) p;
				setupInterfaces();
				shield.pm.addClassToInstantiatedPluginClassesArrayList(name);
				shield.log(String.format("%s hooked.", name));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event) {
		if (protect != null) {
			if (event.getPlugin().getDescription().getName().equals(name)) {
				protect = null;
				shield.log(String.format("%s unhooked.", name));
			}
		}
	}
	
	private void setupInterfaces(){
		RegisteredServiceProvider<Checks> provider = shield.getServer().getServicesManager().getRegistration(Checks.class);
        if (provider != null) {
            checks = provider.getProvider();
        }
        
        api = new RegiosAPI();
	}
	
	public boolean isEnabled(){
		return (protect == null ? false : true);
	}
	
	public String getPluginName(){
		return name;
	}

	public boolean isInRegion(Entity entity) {
		//Regios currently can only check for Players, defaults false for other entity types
		if (entity instanceof Player){
			return (api.isInRegion((Player)entity) ? true : false);
		}else{
			return false;
		}
	}

	public boolean isInRegion(Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canBuild(Player player) {
		if (api == null){
        	shield.log("api is null");
        }
        
        if (checks == null){
        	shield.log("checks is null");
        }
        
		//return (checks.canBuild(player) ? true : false);
        return true;
	}

	public boolean canBuild(Player player, Location loc) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean canUse(Player player) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean canUse(Player player, Location loc) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean canOpen(Player player) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean canOpen(Player player, Location loc) {
		// TODO Auto-generated method stub
		return true;
	}
}
