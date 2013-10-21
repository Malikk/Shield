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
import org.bukkit.plugin.Plugin;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.object.TownyWorld;

/*
 * Towny api might not require us to use our Shield API. (ShieldRegion) Its got location methods for fetching town blocks.
 */
/**
 * Towny
 * @version v0.82.0.0 for CB 1.3.2-R3.0
 */
public class Protect_Towny extends ProtectTemplate {

	private Towny protect = null;

	private enum AccessType {
		Resident, Ally, Outsider, Owner, None;
	}
	
	public Protect_Towny(Shield instance){
		super(instance, ProtectInfo.TOWNY);
	}

	@Override
	public void init(){
		//Leave this out until the class is finished
		//shield.pm.addClassToInstantiatedSet(shield.towny);
		protect = (Towny) plugin;
	}

	@Override
	protected void hook(Plugin p){
		//Overriding template hook method and leaving blank. This is so the class isn't used while it's not finished.
		shield.logWarning("There is currently no support for Towny. It only has a placeholder class and is hooked.");
	}

	@Override
	public HashSet<ShieldRegion> getRegions() {
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();
		
		for (TownyWorld w : protect.getTownyUniverse().getWorldMap().values())
		{
			if (!w.isUsingTowny())
				continue;
			
			for (TownBlock tb : w.getTownBlocks())
				regions.add(craftShieldRegion(tb));
		}
		
		return regions;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity) {
		return getRegions(entity.getLocation());
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc) {
		HashSet<ShieldRegion> regions = new HashSet<ShieldRegion>();
		
		regions.add(craftShieldRegion(TownyUniverse.getTownBlock(loc)));
		
		return regions;
	}

	@Override
	public boolean isInRegion(Entity entity) {
		return true;
	}

	@Override
	public boolean isInRegion(Location loc) {
		return true;
	}

	@Override
	public boolean canBuild(Player player) {
		Location loc = player.getLocation();
		
		return canBuild(player, loc);
	}

	private TownBlock getTownBlock(Location loc) {
		return TownyUniverse.getTownBlock(loc);
	}
	
	private TownBlock getTownBlock(ShieldRegion sr) {
		String[] raw = sr.getName().split(":");
		String[] coords = raw[1].split(",");
		
		int x = Integer.parseInt(coords[0]),
			z = Integer.parseInt(coords[1]);
		
		try {
			return protect.getTownyUniverse().getWorldMap().get(raw[0]).getTownBlock(x, z);
		} catch (NotRegisteredException e) {
			return null;
		}
	}

	private ShieldRegion getHighestRegion(HashSet<ShieldRegion> regions) {
		if (regions.size() > 0)
			return regions.toArray(new ShieldRegion[regions.size()])[0];
		else
			return null;
	}
	
	private AccessType getType(Resident user, TownBlock tb)
	{
		//FIXME: Optimize
		Town town = null;
		Nation nation = null;
		Resident owner = null;
		
		try { town = tb.getTown(); } catch (NotRegisteredException e) { }
		if (town != null)
			try { nation = town.getNation(); } catch (NotRegisteredException e) { }
		try { owner = tb.getResident(); } catch (NotRegisteredException e) { }
		
		if (town == null)
			return AccessType.None;
		
		if (tb.isOwner(user))
			return AccessType.Owner;
		
		if (owner != null)
			if (owner.getFriends().contains(user))
				return AccessType.Resident;
			else if (town.hasResident(user))
				return AccessType.Ally;
			else
				return AccessType.Outsider;
		else if (town.hasResident(user))
			return AccessType.Resident;
		else if (nation != null && nation.hasResident(user))
			return AccessType.Ally;
		else
			return AccessType.Outsider;
		
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		TownBlock tb = getTownBlock(loc);
		
		if (tb == null)
			return false; 
		
		Resident builder = null;
		
		try {
			builder = TownyUniverse.getDataSource().getResident(player.getName());
		} catch (NotRegisteredException e) {
			return false;
		}
		
		AccessType type = getType(builder, tb);
		
		switch (type) {
			case Owner:
				return true;
			case Resident:
				return tb.getPermissions().residentBuild;
			case Ally:
				return tb.getPermissions().allyBuild;
			case Outsider:
				return tb.getPermissions().outsiderBuild;
			case None:
			default:
				return tb.getWorld().getUnclaimedZoneBuild();
		}
		
	}

	

	@Override
	public boolean canUse(Player player) {
		return canUse(player, player.getLocation());
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		if (!TownySettings.getItemUseIds().contains(loc.getBlock().getTypeId()))
			return true;
		
		TownBlock tb = getTownBlock(loc);
		
		Resident user = null;
	
		try {
			user = TownyUniverse.getDataSource().getResident(player.getName());
		} catch (NotRegisteredException e) {
			return false; 
		}
		
		AccessType type = getType(user, tb);
		
		/*
		 * FIXME: Improper Implementation.
		 * This should be using a canSwitch method in Protect.
		 * This may change in future for the better.
		 */
		switch (type) {
		case Owner:
			return true;
		case Resident:
			return tb.getPermissions().residentItemUse || tb.getPermissions().residentSwitch; 
		case Ally:
			return tb.getPermissions().allyItemUse || tb.getPermissions().allySwitch;
		case Outsider:
			return tb.getPermissions().outsiderItemUse || tb.getPermissions().outsiderSwitch;
		case None:
		default:
			return tb.getWorld().getUnclaimedZoneItemUse() || tb.getWorld().getUnclaimedZoneSwitch();
		}
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
	
	private ShieldRegion craftShieldRegion(TownBlock tb)
	{
		return shield.rm.createShieldRegion(tb.toString(), this, tb.getWorldCoord().getBukkitWorld());
	}

}

