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
import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;

/**
 * Residence
 * @version v2.6.6 for CB 1.4.2-R0.2
 */
public class Protect_Residence implements Listener, Protect {

	Shield shield;

	private final String name = "Residence";
	private final String pack = "com.bekvon.bukkit.residence.Residence";
	private static Residence protect = null;

	//Managers
	private static ResidenceManager rmanager = null;

	public Protect_Residence(Shield instance){
		this.shield = instance;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		//Load plugin if it was loaded before Shield
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (Residence) p;
				rmanager = Residence.getResidenceManager();
				shield.pm.addClassToInstantiatedSet(shield.residence);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event) {
		if (protect == null) {
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if (p != null && p.isEnabled() && p.getClass().getName().equals(pack)) {
				protect = (Residence) p;
				rmanager = Residence.getResidenceManager();
				shield.pm.addClassToInstantiatedSet(shield.residence);
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

		try{
			for (String r: rmanager.getResidenceList()){
				regions.add(shield.rm.createShieldRegion(r, shield.residence, Bukkit.getWorld(rmanager.getByName(r).getWorld())));
			}

			return regions;

		}catch(Exception e){
			return regions;
		}
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity){
		try{
			String [] s = {rmanager.getByLoc(entity.getLocation()).getName()};
			return getShieldRegions(s, entity.getWorld());
		}catch(Exception e){
			return new HashSet<ShieldRegion>();
		}
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc){
		try{
			String [] s = {rmanager.getByLoc(loc).getName()};
			return getShieldRegions(s, loc.getWorld());
		}catch(Exception e){
			return new HashSet<ShieldRegion>();
		}
	}

	public HashSet<ShieldRegion> getShieldRegions(String[] names, World world){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		try{
			for (String r: names){
				regions.add(shield.rm.createShieldRegion(r, shield.residence, world));
			}

			return regions;

		}catch(Exception e){
			return regions;
		}
	}

	@Override
	public boolean isInRegion(Entity entity) {
		return (rmanager.getByLoc(entity.getLocation()) != null ? true : false);
	}

	@Override
	public boolean isInRegion(Location loc) {
		return (rmanager.getByLoc(loc) != null ? true : false);
	}

	@Override
	public boolean canBuild(Player player) {
		FlagPermissions flags = Residence.getPermsByLoc(player.getLocation());

		return flags.playerHas(player.getName(), player.getWorld().getName(), "build", true);
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		FlagPermissions flags = Residence.getPermsByLoc(loc);

		return flags.playerHas(player.getName(), player.getWorld().getName(), "build", true);
	}

	@Override
	public boolean canUse(Player player) {
		FlagPermissions flags = Residence.getPermsByLoc(player.getLocation());

		return flags.playerHas(player.getName(), player.getWorld().getName(), "use", true);
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		FlagPermissions flags = Residence.getPermsByLoc(loc);

		return flags.playerHas(player.getName(), player.getWorld().getName(), "use", true);
	}

	@Override
	public boolean canOpen(Player player) {
		FlagPermissions flags = Residence.getPermsByLoc(player.getLocation());

		return flags.playerHas(player.getName(), player.getWorld().getName(), "container", true);
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		FlagPermissions flags = Residence.getPermsByLoc(loc);

		return flags.playerHas(player.getName(), player.getWorld().getName(), "container", true);
	}

	//Region info Getters
	private ClaimedResidence getRegion(ShieldRegion region){
		return rmanager.getByName(region.getName());
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		return getRegion(region).containsLoc(loc);
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region) {
		return new ShieldGroup(getRegion(region).getOwner());
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region) {
		//TODO Does Residence have a member function?
		return new ShieldGroup();
	}
}
