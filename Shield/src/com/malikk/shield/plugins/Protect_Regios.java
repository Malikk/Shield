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
import net.jzx7.regios.util.RegiosConversions;
import net.jzx7.regiosapi.regions.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;

/**
 * Regios
 * @version v5.9.9 for CB 1.6.2
 */
public class Protect_Regios extends ProtectTemplate {

	private RegiosPlugin protect;

	public Protect_Regios(Shield instance){
		super(instance, ProtectInfo.REGIOS);
	}

	@Override
	public void init(){
		protect = (RegiosPlugin)plugin;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		if (protect.getRegions() != null){
			for (Region r: protect.getRegions()){
				regions.add(shield.rm.createShieldRegion(r.getName(), info.getProtectObject(), Bukkit.getWorld(r.getWorld().getName())));
			}
		}

		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		if (protect.getRegions(entity.getLocation()) != null){
			for (Region r: protect.getRegions(entity.getLocation())){
				regions.add(shield.rm.createShieldRegion(r.getName(), info.getProtectObject(), Bukkit.getWorld(r.getWorld().getName())));
			}
		}

		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();

		if (protect.getRegions(loc) != null){
			for (Region r: protect.getRegions(loc)){
				regions.add(shield.rm.createShieldRegion(r.getName(), info.getProtectObject(),  Bukkit.getWorld(r.getWorld().getName())));
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
		return (protect.getRegion(player) != null ? protect.getRegion(player).canBypassProtection(RegiosConversions.getRegiosPlayer(player)) : true);
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return (protect.getRegion(loc) != null ? protect.getRegion(loc).canBypassProtection(RegiosConversions.getRegiosPlayer(player)) : true);
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
