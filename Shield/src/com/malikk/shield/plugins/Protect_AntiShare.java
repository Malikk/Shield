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

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import com.turt2live.antishare.AntiShare;
import com.turt2live.antishare.Systems.Manager;
import com.turt2live.antishare.manager.RegionManager;
import com.turt2live.antishare.permissions.PermissionNodes;
import com.turt2live.antishare.regions.Region;

/**
 * AntiShare
 * 
 * @version v9.5.0 for CB 1.4.2
 */
public class Protect_AntiShare implements Listener, Protect {

	Shield shield;

	private final String name = "AntiShare";
	private final String pack = "com.turt2live.antishare.AntiShare";
	private static AntiShare protect = null;
	private static RegionManager regionManager = null;
	
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
		this.shield = instance;

		PluginManager pm = shield.getServer().getPluginManager();
		pm.registerEvents(this, shield);

		//Load plugin if it was loaded before Shield
		if(protect == null){
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if(p != null && p.isEnabled() && p.getClass().getName().equals(pack)){
				protect = (AntiShare) p;
				if(protect.getSystemsManager().isEnabled(Manager.REGION)){
				    regionManager = (RegionManager) protect.getSystemsManager().getManager(Manager.REGION);
				}
			}
		}
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent event){
		if(protect == null){
			Plugin p = shield.getServer().getPluginManager().getPlugin(name);

			if(p != null && p.isEnabled() && p.getClass().getName().equals(pack)){
				protect = (AntiShare) p;
				shield.log(String.format("Hooked %s v" + getVersion(), name));
                if(protect.getSystemsManager().isEnabled(Manager.REGION)){
                    regionManager = (RegionManager) protect.getSystemsManager().getManager(Manager.REGION);
                }
			}
		}
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent event){
		if(protect != null){
			if(event.getPlugin().getDescription().getName().equals(name)){
				protect = null;
				regionManager = null;
				shield.log(String.format("%s unhooked.", name));
			}
		}
	}

	@Override
	public boolean isEnabled(){
		return protect != null && regionManager != null;
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
		for(Region region : regionManager.getAllRegions()){
		    ShieldRegion sregion = shield.rm.createShieldRegion(region.getName(), shield.antishare, Bukkit.getWorld(region.getWorldName()));
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
        ShieldRegion sregion = shield.rm.createShieldRegion(region.getName(), shield.antishare, Bukkit.getWorld(region.getWorldName()));
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
	    Region region = regionManager.getRegion(player.getLocation());
        Region playerRegion = regionManager.getRegion(player.getLocation());
        if(protect.getPermissions().has(player, PermissionNodes.REGION_PLACE) 
                || protect.getPermissions().has(player, PermissionNodes.REGION_BREAK)){
            return true;
        }
        return region == playerRegion;
	}

	@Override
	public boolean canUse(Player player){
		return canUse(player, player.getLocation());
	}

	@Override
	public boolean canUse(Player player, Location loc){
        Region region = regionManager.getRegion(loc);
        Region playerRegion = regionManager.getRegion(player.getLocation());
        if(protect.getPermissions().has(player, PermissionNodes.REGION_USE)){
            return true;
        }
        return region == playerRegion;
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
