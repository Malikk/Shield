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

import java.util.HashSet;

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

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.palmergames.bukkit.towny.Towny;

/**
 * Towny
 * @version v0.82.0.0 for CB 1.3.2-R3.0
 */
public class Protect_Towny implements Listener, Protect {

	Shield shield;

	private final String name = "Towny";
	private final String pack = "com.palmergames.bukkit.towny.Towny";
	private static Towny protect = null;

	public Protect_Towny(Shield instance){
		this.shield = instance;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		//Load plugin if it was loaded before Shield
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (Towny) p;
				//shield.pm.addClassToInstantiatedSet(this);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (Towny) p;
				//shield.pm.addClassToInstantiatedSet(this);
				shield.log(String.format("Hooked %s v" + getVersion(), name));
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

	@Override
	public boolean isEnabled(){
		return (protect == null ? false : true);
	}

	@Override
	public String getPluginName(){
		return name;
	}

	@Override
	public String getVersion(){
		return protect.getDescription().getVersion();
	}

	@Override
	public HashSet<ShieldRegion> getRegions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInRegion(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInRegion(Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuild(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUse(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	//Region info Getters
	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

}

