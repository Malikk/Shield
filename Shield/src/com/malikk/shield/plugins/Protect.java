package com.malikk.shield.plugins;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.regions.ShieldRegion;


public interface Protect {
	
	//List of methods that every protect class MUST have. Does NOT extend the actual Api.
	
	public boolean isEnabled();
	
	public String getPluginName();
	
	public ArrayList<ShieldRegion> getRegions();
	
	public ArrayList<ShieldRegion> getRegions(Entity entity);
	
	public ArrayList<ShieldRegion> getRegions(Location loc);
	
	public boolean isInRegion(Entity entity);
	
	public boolean isInRegion(Location loc);
	
	public boolean canBuild(Player player);
	
	public boolean canBuild(Player player, Location loc);
	
	public boolean canUse(Player player);
	
	public boolean canUse(Player player, Location loc);
	
	public boolean canOpen(Player player);
	
	public boolean canOpen(Player player, Location loc);
}
