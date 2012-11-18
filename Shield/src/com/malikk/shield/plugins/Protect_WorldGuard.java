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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * WorldGuard
 * @version v5.6.5 for CB 1.4.2-R0.2
 */
public class Protect_WorldGuard implements Listener, Protect {

	Shield shield;

	private final String name = "WorldGuard";
	private final String pack = "com.sk89q.worldguard.bukkit.WorldGuardPlugin";
	private static WorldGuardPlugin protect = null;

	public Protect_WorldGuard(Shield instance){
		this.shield = instance;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		//Load plugin if it was loaded before Shield
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (WorldGuardPlugin) p;
				shield.pm.addClassToInstantiatedSet(shield.worldGuard);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (WorldGuardPlugin) p;
				shield.pm.addClassToInstantiatedSet(shield.worldGuard);
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

	public ShieldRegion getHighestPriority(HashSet<ShieldRegion> regions){

		HashMap<ShieldRegion, Integer> priorities = new HashMap<ShieldRegion, Integer>();

		for (ShieldRegion r: regions){
			RegionManager rm = protect.getRegionManager(r.getWorld());

			shield.log(r.getName());

			ProtectedRegion region = rm.getRegionExact(r.getName());

			priorities.put(r, region.getPriority());
		}

		Collection<Integer> values = priorities.values();
		int highest = Collections.max(values);

		for (ShieldRegion r: regions){
			if (priorities.get(r) == highest){
				return r;
			}
		}

		return null;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();
		List<World> worlds = Bukkit.getServer().getWorlds();

		for (World world: worlds){
			for (String s: protect.getRegionManager(world).getRegions().keySet()){
				regions.add(shield.rm.createShieldRegion(s, shield.worldGuard, world));
			}
		}

		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity){
		return getShieldRegions(getAppRegionSet(entity), entity.getWorld());
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc){
		return getShieldRegions(getAppRegionSet(loc), loc.getWorld());
	}

	public HashSet<ShieldRegion> getShieldRegions(ApplicableRegionSet app, World world){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		for (ProtectedRegion region: app){
			regions.add(shield.rm.createShieldRegion(region.getId(), shield.worldGuard, world));
		}

		return regions;
	}

	@Override
	public boolean isInRegion(Location loc) {
		return (getAppRegionSet(loc).size() > 0 ? true : false);
	}

	@Override
	public boolean isInRegion(Entity entity) {
		return isInRegion(entity.getLocation());
	}

	public String getRegionOccupiedBy(Entity entity) {
		ApplicableRegionSet regionSet = getAppRegionSet(entity);
		ProtectedRegion priority = null;

		for (ProtectedRegion region: regionSet){
			if (priority == null){
				priority = region;
			}else if (region.getPriority() > priority.getPriority()){
				priority = region;
			}
		}

		return (priority == null ? null : priority.getId());
	}

	@Override
	public boolean canBuild(Player player) {
		return protect.canBuild(player, player.getLocation());
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return protect.canBuild(player, loc);
	}

	@Override
	public boolean canUse(Player player){
		ApplicableRegionSet regionSet = getAppRegionSet(player);
		LocalPlayer lPlayer = protect.wrapPlayer(player);

		if (regionSet.size() == 0){return true;}

		return (regionSet.allows(DefaultFlag.USE, lPlayer) ? true : false);
	}

	@Override
	public boolean canUse(Player player, Location loc){
		ApplicableRegionSet regionSet = getAppRegionSet(loc);
		LocalPlayer lPlayer = protect.wrapPlayer(player);

		if (regionSet.size() == 0){return true;}

		return (regionSet.allows(DefaultFlag.USE, lPlayer) ? true : false);
	}

	@Override
	public boolean canOpen(Player player){
		ApplicableRegionSet regionSet = getAppRegionSet(player);
		LocalPlayer lPlayer = protect.wrapPlayer(player);

		if (regionSet.size() == 0){return true;}

		return (regionSet.allows(DefaultFlag.CHEST_ACCESS, lPlayer) ? true : false);
	}

	@Override
	public boolean canOpen(Player player, Location loc){
		ApplicableRegionSet regionSet = getAppRegionSet(loc);
		LocalPlayer lPlayer = protect.wrapPlayer(player);

		if (regionSet.size() == 0){return true;}

		return (regionSet.allows(DefaultFlag.CHEST_ACCESS, lPlayer) ? true : false);
	}

	public ApplicableRegionSet getAppRegionSet(Entity entity){
		return protect.getRegionManager(entity.getWorld()).getApplicableRegions(entity.getLocation());
	}

	public ApplicableRegionSet getAppRegionSet(Location loc){
		return protect.getRegionManager(loc.getWorld()).getApplicableRegions(loc);
	}

	//Region Info Getters
	private ProtectedRegion getRegion(ShieldRegion region){
		return protect.getRegionManager(region.getWorld()).getRegionExact(region.getName());
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc){
		Vector vec = new Vector(loc.getX(), loc.getY(), loc.getZ());
		return getRegion(region).contains(vec);
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region) {
		DefaultDomain dd = getRegion(region).getOwners();
		ShieldGroup group = new ShieldGroup();
		group.addPlayerNames(dd.getPlayers());
		return group;
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region) {
		DefaultDomain dd = getRegion(region).getMembers();
		ShieldGroup group = new ShieldGroup();
		group.addPlayerNames(dd.getPlayers());
		return group;
	}
}
