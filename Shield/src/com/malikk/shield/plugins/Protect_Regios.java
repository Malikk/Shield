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

import net.jzx7.regios.RegiosPlugin;
import net.jzx7.regiosapi.regions.Region;

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

/**
 * Regios
 * @version v5.9.3 for CB 1.4.2-R0.2
 */
public class Protect_Regios implements Listener, Protect {

	Shield shield;

	private final String name = "Regios";
	private final String pack = "net.jzx7.regios.RegiosPlugin";
	private static RegiosPlugin protect = null;

	public Protect_Regios(Shield instance){
		this.shield = instance;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		//Load plugin if it was loaded before Shield
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (RegiosPlugin) p;
				shield.pm.addClassToInstantiatedSet(shield.regios);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (RegiosPlugin) p;
				shield.pm.addClassToInstantiatedSet(shield.regios);
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
	public HashSet<ShieldRegion> getRegions(){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		if (protect.getRegions() != null){
			for (Region r: protect.getRegions()){
				regions.add(shield.rm.createShieldRegion(r.getName(), shield.regios, r.getWorld()));
			}
		}

		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		if (protect.getRegions(entity.getLocation()) != null){
			for (Region r: protect.getRegions(entity.getLocation())){
				regions.add(shield.rm.createShieldRegion(r.getName(), shield.regios, r.getWorld()));
			}
		}

		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		if (protect.getRegions(loc) != null){
			for (Region r: protect.getRegions(loc)){
				regions.add(shield.rm.createShieldRegion(r.getName(), shield.regios, r.getWorld()));
			}
		}

		return regions;
	}

	@Override
	public boolean isInRegion(Entity entity) {
		return protect.isInRegion(entity.getLocation());
	}

	@Override
	public boolean isInRegion(Location loc) {
		return protect.isInRegion(loc);
	}

	@Override
	public boolean canBuild(Player player) {
		return (protect.getRegion(player) != null ? protect.getRegion(player).canBypassProtection(player) : true);
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return (protect.getRegion(loc) != null ? protect.getRegion(loc).canBypassProtection(player) : true);
	}

	@Override
	public boolean canUse(Player player) {
		return canBuild(player);
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		return canBuild(player, loc);
	}

	@Override
	public boolean canOpen(Player player) {
		return canBuild(player);
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		return canBuild(player, loc);
	}

	//Region info Getters
	private Region getRegion(ShieldRegion region){
		return protect.getRegion(region.getName());
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		return (protect.getRegion(loc) != null ? true : false);
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region) {
		return new ShieldGroup(getRegion(region).getOwner());
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region) {
		ShieldGroup group = new ShieldGroup();
		group.addPlayerNames(getRegion(region).getSubOwners());
		return group;
	}
}
