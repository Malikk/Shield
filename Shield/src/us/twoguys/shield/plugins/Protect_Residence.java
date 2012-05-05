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

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;

import us.twoguys.shield.*;

public class Protect_Residence implements Listener, Protect {
	
	Shield shield;
	
	private final String name = "Residence";
	private final String pack = "com.bekvon.bukkit.residence.Residence";
	private static int instanceCount = 0;
	private static Residence protect = null;
	
	//Managers
	ResidenceManager rmanager = Residence.getResidenceManager();
	
	public Protect_Residence(Shield instance){
		this.shield = instance;
		
		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);
		
		if (instanceCount == 0){
			//Load plugin if it was loaded before Shield
			if (protect == null) {
				Plugin p = shield.getServer().getPluginManager().getPlugin(name);
	            
				if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
					protect = (Residence) p;
					//shield.pm.addClassToInstantiatedPluginClassesArrayList(name);
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
				protect = (Residence) p;
				//shield.pm.addClassToInstantiatedPluginClassesArrayList(name);
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
	
	public boolean isEnabled(){
		return (protect == null ? false : true);
	}
	
	public String getPluginName(){
		return name;
	}

	public boolean isInRegion(Entity entity) {
		return (rmanager.getByLoc(entity.getLocation()) != null ? true : false);
	}

	public boolean isInRegion(Location loc) {
		return (rmanager.getByLoc(loc) != null ? true : false);
	}

	public boolean canBuild(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canBuild(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUse(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canUse(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canOpen(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canOpen(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}
}
