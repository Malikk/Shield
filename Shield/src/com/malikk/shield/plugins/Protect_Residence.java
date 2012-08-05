/*
 * Copyright 2012 Jordan Hobgood
 * 
 * This file is part of Shield.
 *
 * Shield is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shield is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with Shield.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.plugins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.malikk.shield.*;
import com.malikk.shield.regions.ShieldRegion;

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
				protect = (Residence) p;
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
	
	public boolean isEnabled(){
		return (protect == null ? false : true);
	}
	
	public String getPluginName(){
		return name;
	}
	
	public ArrayList<ShieldRegion> getRegions(){
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		try{
			for (String r: rmanager.getResidenceList()){
				regions.add(shield.rm.createShieldRegion(r, name, Bukkit.getWorld(rmanager.getByName(r).getWorld())));
			}
			
			return regions;
			
		}catch(Exception e){
			return regions;
		}
	}
	
	public ArrayList<ShieldRegion> getRegions(Entity entity){
		try{
			String [] s = {rmanager.getByLoc(entity.getLocation()).getName()};
			return getShieldRegions(s, entity.getWorld());
		}catch(Exception e){
			return new ArrayList<ShieldRegion>();
		}
	}
	
	public ArrayList<ShieldRegion> getRegions(Location loc){
		try{
			String [] s = {rmanager.getByLoc(loc).getName()};
			return getShieldRegions(s, loc.getWorld());
		}catch(Exception e){
			return new ArrayList<ShieldRegion>();
		}
	}
	
	public ArrayList<ShieldRegion> getShieldRegions(String[] names, World world){
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		try{
			for (String r: names){
				regions.add(shield.rm.createShieldRegion(r, name, world));
			}
			
			return regions;
			
		}catch(Exception e){
			return regions;
		}
	}

	public boolean isInRegion(Entity entity) {
		return (rmanager.getByLoc(entity.getLocation()) != null ? true : false);
	}

	public boolean isInRegion(Location loc) {
		return (rmanager.getByLoc(loc) != null ? true : false);
	}

	public boolean canBuild(Player player) {
		FlagPermissions flags = Residence.getPermsByLoc(player.getLocation());
		
		return flags.playerHas(player.getName(), player.getWorld().getName(), "build", true);
	}

	public boolean canBuild(Player player, Location loc) {
		FlagPermissions flags = Residence.getPermsByLoc(loc);
		
		return flags.playerHas(player.getName(), player.getWorld().getName(), "build", true);
	}

	public boolean canUse(Player player) {
		FlagPermissions flags = Residence.getPermsByLoc(player.getLocation());
		
		return flags.playerHas(player.getName(), player.getWorld().getName(), "use", true);
	}

	public boolean canUse(Player player, Location loc) {
		FlagPermissions flags = Residence.getPermsByLoc(loc);
		
		return flags.playerHas(player.getName(), player.getWorld().getName(), "use", true);
	}

	public boolean canOpen(Player player) {
		FlagPermissions flags = Residence.getPermsByLoc(player.getLocation());
		
		return flags.playerHas(player.getName(), player.getWorld().getName(), "container", true);
	}

	public boolean canOpen(Player player, Location loc) {
		FlagPermissions flags = Residence.getPermsByLoc(loc);
		
		return flags.playerHas(player.getName(), player.getWorld().getName(), "container", true);
	}
	
	//Region info Getters
	private ClaimedResidence getRegion(ShieldRegion region){
		return rmanager.getByName(region.getName());
	}
	
	@Override
	public Location getMaxLoc(ShieldRegion region) {
		//TODO Not sure if i want to keep working on this feature, since not all plugins use cuboid regions.
		return null;
	}

	@Override
	public Location getMinLoc(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}
}
