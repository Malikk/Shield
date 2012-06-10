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

import couk.Adamki11s.Regios.API.RegiosAPI;
import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.Regions.Region;

import us.twoguys.shield.*;

public class Protect_Regios implements Listener, Protect {
	
	Shield shield;
	
	private final String name = "Regios";
	private final String pack = "couk.Adamki11s.Regios.Main.Regios";
	private static int instanceCount = 0;
	private static Regios protect = null;
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
					api = new RegiosAPI();
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
				api = new RegiosAPI();
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
				api = null;
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
	
	public ArrayList<String> getRegions(){
		ArrayList<String> names = new ArrayList<String>();
		
		for (Region r: api.getRegions()){
			names.add(r.getName());
		}
		
		return names;
	}
	
	public ArrayList<String> getRegions(Entity entity){
		ArrayList<String> names = new ArrayList<String>();
		
		for (Region r: api.getRegions(entity.getLocation())){
			names.add(r.getName());
		}
		
		return names;
	}
	
	public ArrayList<String> getRegions(Location loc){
		ArrayList<String> names = new ArrayList<String>();
		
		for (Region r: api.getRegions(loc)){
			names.add(r.getName());
		}
		
		return names;
	}

	public boolean isInRegion(Entity entity) {
		return api.isInRegion(entity.getLocation());
	}

	public boolean isInRegion(Location loc) {
		return api.isInRegion(loc);
	}

	public boolean canBuild(Player player) {
		return api.getRegion(player).canBuild(player);
	}

	public boolean canBuild(Player player, Location loc) {
		return api.getRegion(loc).canBuild(player);
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
