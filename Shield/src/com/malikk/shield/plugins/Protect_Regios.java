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

import com.malikk.shield.*;
import com.malikk.shield.regions.ShieldRegion;

import couk.Adamki11s.Regios.API.RegiosAPI;
import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.Regions.Region;

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
				shield.log(String.format("Hooked %s v" + getVersion(), name));
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
	
	public String getVersion(){
		return protect.getDescription().getVersion();
	}
	
	public ArrayList<ShieldRegion> getRegions(){
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		if (api.getRegions() != null){
			for (Region r: api.getRegions()){
				regions.add(shield.rm.createShieldRegion(r.getName(), name, r.getWorld()));
			}
		}
		
		return regions;
	}
	
	public ArrayList<ShieldRegion> getRegions(Entity entity){
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		if (api.getRegions(entity.getLocation()) != null){
			for (Region r: api.getRegions(entity.getLocation())){
				regions.add(shield.rm.createShieldRegion(r.getName(), name, r.getWorld()));
			}
		}
		
		return regions;
	}
	
	public ArrayList<ShieldRegion> getRegions(Location loc){
		ArrayList<ShieldRegion> regions = new ArrayList<ShieldRegion>();
		
		if (api.getRegions(loc) != null){
			for (Region r: api.getRegions(loc)){
				regions.add(shield.rm.createShieldRegion(r.getName(), name, r.getWorld()));
			}
		}
		
		return regions;
	}

	public boolean isInRegion(Entity entity) {
		return api.isInRegion(entity.getLocation());
	}

	public boolean isInRegion(Location loc) {
		return api.isInRegion(loc);
	}

	public boolean canBuild(Player player) {
		return api.getRegion(player).canBypassProtection(player);
	}

	public boolean canBuild(Player player, Location loc) {
		return api.getRegion(loc).canBypassProtection(player);
	}

	public boolean canUse(Player player) {
		return api.getRegion(player).canBypassProtection(player);
	}

	public boolean canUse(Player player, Location loc) {
		return api.getRegion(loc).canBypassProtection(player);
	}

	public boolean canOpen(Player player) {
		return api.getRegion(player).canBypassProtection(player);
	}

	public boolean canOpen(Player player, Location loc) {
		return api.getRegion(loc).canBypassProtection(player);
	}

	//Region info Getters
	
	private Region getRegion(ShieldRegion region){
		return api.getRegion(region.getName());
	}
	
	@Override
	public Location getMaxLoc(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getMinLoc(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		return (api.getRegion(loc) != null ? true : false);
	}
}
