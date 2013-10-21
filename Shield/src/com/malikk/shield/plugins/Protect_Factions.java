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
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.massivecraft.factions.FPerm;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.FactionColls;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.mcore.ps.PS;

/**
 * Factions <br>
 * TODO: support outposts
 * 
 * @author IDragonfire
 * @version 2.1.0
 */
public class Protect_Factions extends ProtectTemplate {

	protected Factions protect;

	public Protect_Factions(Shield instance) {
		super(instance, ProtectInfo.FACTIONS);
	}

	@Override
	public void init(){
		protect = (Factions) plugin;
		// TODO remove once support is complete
		shield.logWarning("Factions support is currently incomplete");
	}

	@Override
	public boolean canBuild(Player player, Location loc) {
		return FPerm.BUILD.has(UPlayer.get(player.getName()), PS.valueOf(loc), false);
	}

	@Override
	public boolean canOpen(Player player, Location loc) {
		UPlayer uplayer = UPlayer.get(player.getName());
		
		PS ps = PS.valueOf(loc);
		
		Material type = loc.getBlock().getType();
		if (type == Material.WOOD_DOOR || type == Material.IRON_DOOR) {
			return FPerm.DOOR.has(uplayer, ps, false);
		}
		if (type == Material.STONE_BUTTON) {
			return FPerm.BUTTON.has(uplayer, ps, false);
		}
		if (type == Material.LEVER) {
			return FPerm.LEVER.has(uplayer, ps, false);
		}
		return FPerm.DOOR.has(uplayer, ps, false)
				|| FPerm.BUTTON.has(uplayer, ps, false)
				|| FPerm.LEVER.has(uplayer, ps, false);
	}

	@Override
	public boolean canUse(Player player, Location loc) {
		return FPerm.CONTAINER.has(UPlayer.get(player.getName()), PS.valueOf(loc), false);
	}

	@Override
	public boolean contains(ShieldRegion region, Location loc) {
		PS ps = PS.valueOf(loc);
		Faction in = BoardColls.get().getFactionAt(ps);
		if (in == null)
			return false;

		Faction out = null;
		for (Faction f : FactionColls.get().getForWorld(ps.getWorld()).getAll())
			if (f.getId().equals(region.getName()))
			{
				out = f;
				break;
			}
		
		if (out == null) {
			return false;
		}
		return in.getId().equals(out.getId());
	}

	@Override
	public ShieldGroup getMembers(ShieldRegion region) {
		ShieldGroup group = region.getMembers();
		Faction faction = Faction.get(region.getName());
		if (faction != null) {
			for (UPlayer member : faction.getUPlayers()) {
				group.addPlayer(member.getName());
			}
		}
		return group;
	}

	@Override
	public ShieldGroup getOwners(ShieldRegion region) {
		ShieldGroup group = region.getOwners();
		Faction faction = Faction.get(region.getName());
		if (faction != null) {
			group.addPlayer(faction.getLeader().getName());
		}
		return group;
	}

	@Override
	public HashSet<ShieldRegion> getRegions() {
		HashSet<ShieldRegion> regios = new HashSet<ShieldRegion>();
		List<FactionColl> data = FactionColls.get().getColls();
		
		for (FactionColl col : data)
			for (Faction faction : col.getAll())
				addFactionToRegion(faction, regios);
			
		return regios;
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Location loc) {
		HashSet<ShieldRegion> regios = new HashSet<ShieldRegion>();
		Faction faction = BoardColls.get().getFactionAt(PS.valueOf(loc));
		if (faction != null) {
			addFactionToRegion(faction, regios);
		}
		return regios;
	}

	private void addFactionToRegion(Faction faction,
			HashSet<ShieldRegion> region) {
		region.add(new ShieldRegion(shield, faction.getId(), this, 
				faction.getHome().asBukkitWorld()));
		
	}

	@Override
	public HashSet<ShieldRegion> getRegions(Entity entity) {
		return getRegions(entity.getLocation());
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
	public boolean canUse(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canOpen(Player player) {
		// TODO Auto-generated method stub
		return false;
	}
}