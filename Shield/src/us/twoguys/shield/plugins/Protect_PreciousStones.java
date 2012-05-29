package us.twoguys.shield.plugins;

import java.util.ArrayList;

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

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;

import us.twoguys.shield.*;

public class Protect_PreciousStones implements Listener, Protect {
	
	Shield shield;
	
	private final String name = "PreciousStones";
	private final String pack = "net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones";
	private static int instanceCount = 0;
	private static PreciousStones protect = null;
	
	public Protect_PreciousStones(Shield instance){
		this.shield = instance;
		
		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);
		
		if (instanceCount == 0){
			//Load plugin if it was loaded before Shield
			if (protect == null) {
				Plugin p = shield.getServer().getPluginManager().getPlugin(name);
	            
				if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
					protect = (PreciousStones) p;
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
				protect = (PreciousStones) p;
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
	
	public ArrayList<String> getRegions() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getRegions(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getRegions(Location loc) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isInRegion(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isInRegion(Location loc) {
		// TODO Auto-generated method stub
		return false;
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

