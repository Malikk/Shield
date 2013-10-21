/*
 * Copyright 2012 Jordan Hobgood
 * This file is part of Shield.
 * Shield is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Shield is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Shield. If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.plugins;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.turt2live.antishare.AntiShare;
import com.turt2live.antishare.PermissionNodes;
import com.turt2live.antishare.PermissionNodes.PermissionPackage;
import com.turt2live.antishare.manager.RegionManager;
import com.turt2live.antishare.regions.Region;
import com.turt2live.antishare.util.ASMaterialList;
import com.turt2live.antishare.util.ASUtils;

/**
 * AntiShare
 * 
 * @version v5.5.0 for CB 1.6.2
 */
public class Protect_AntiShare extends ProtectTemplate{

	private AntiShare protect = null;
	private RegionManager regionManager = null;

	/*
	 * Generic Note:
	 * 
	 * AntiShare blocks actions based on where the player is situated.
	 * Any action from the region outwards is blocked, as well as any
	 * action from outside to inside the action.
	 * 
	 * For example: Standing outside the region trying to place inside
	 * is not allowed.
	 */

	public Protect_AntiShare(Shield instance){
		super(instance, ProtectInfo.ANTISHARE);
	}

	@Override
	public void init(){
		protect = (AntiShare) plugin;
		regionManager = (RegionManager) protect.getRegionManager();
		shield.pm.addClassToInstantiatedSet(info.getProtectObject());
	}

	@Override
	public HashSet<ShieldRegion> getRegions(){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();
		for(Region region : regionManager.getAllRegions()){
			ShieldRegion sregion = shield.rm.createShieldRegion(region.getName(), info.getProtectObject(), Bukkit.getWorld(region.getWorldName()));
			regions.add(sregion);
		}
		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity){
		return getRegions(entity.getLocation());
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc){
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();
		Region region = regionManager.getRegion(loc);
		if(region==null){
			return regions;
		}
		ShieldRegion sregion = shield.rm.createShieldRegion(region.getName(), info.getProtectObject(), Bukkit.getWorld(region.getWorldName()));
		regions.add(sregion);
		return regions;
	}

	@Override
	public boolean isInRegion(Entity entity){
		return isInRegion(entity.getLocation());
	}

	@Override
	public boolean isInRegion(Location loc){
		return regionManager.isRegion(loc);
	}

	@Override
	public boolean canBuild(Player player){
		return canBuild(player, player.getLocation());
	}

	@Override
	public boolean canBuild(Player player, Location loc){
		if (!(isBlocked(player, loc, PermissionNodes.PACK_BLOCK_PLACE)) || !(isBlocked(player, loc, PermissionNodes.PACK_BLOCK_BREAK)))
			return true;
		else
			return false;
	}
	
	private boolean isBlocked(Player player, Location loc, PermissionPackage pack)
	{
		return ASUtils.isBlocked(player, loc.getBlock(), new ASMaterialList(new ArrayList<String>()), pack).illegal;
	}

	@Override
	public boolean canUse(Player player){
		return canUse(player, player.getLocation());
	}

	@Override
	public boolean canUse(Player player, Location loc){
		if (!(isBlocked(player, loc, PermissionNodes.PACK_USE)))
			return true;
		else
			return false;
	}

	@Override
	public boolean canOpen(Player player){
		return canOpen(player, player.getLocation());
	}

	@Override
	public boolean canOpen(Player player, Location loc){
		return canUse(player, loc);
	}

	//Region info Getters
	@Override
	public boolean contains(ShieldRegion region, Location loc){
		Region asregion = regionManager.getRegion(region.getName());
		if(asregion==null){
			return false;
		}
		return asregion.getCuboid().isContained(loc);
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region){
		Region asregion = regionManager.getRegion(region.getName());
		if(asregion==null){
			return new ShieldGroup();
		}
		// It should be noted that the owner returned here is the creator of the region, not the
		// "owner" of the region in a traditional sense
		return new ShieldGroup(new Player[]{protect.getServer().getPlayer(asregion.getOwner())});
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region){
		return getOwners(region); // No member support
	}

}
