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
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.FPerm;

/**
 * Factions <br>
 * TODO: support outposts
 * 
 * @author IDragonfire
 * @version 1.0
 */
public class Protect_Factions extends ProtectTemplate {

    protected Factions protect;

    public Protect_Factions(Shield instance) {
        super(instance, "Faction", "com.massivecraft.factions");
    }

    @Override
    public boolean canBuild(Player player, Location loc) {
        return FPerm.BUILD.has(FPlayers.i.get(player.getName()), new FLocation(
                loc));
    }

    @Override
    public boolean canOpen(Player player, Location loc) {
        FPlayer fplayer = FPlayers.i.get(player.getName());
        FLocation flocation = new FLocation(loc);
        Material type = loc.getBlock().getType();
        if (type == Material.WOOD_DOOR || type == Material.IRON_DOOR) {
            return FPerm.DOOR.has(fplayer, flocation);
        }
        if (type == Material.STONE_BUTTON) {
            FPerm.BUTTON.has(fplayer, flocation);
        }
        if (type == Material.LEVER) {
            return FPerm.LEVER.has(fplayer, flocation);
        }
        return FPerm.DOOR.has(fplayer, flocation)
                || FPerm.BUTTON.has(fplayer, flocation)
                || FPerm.LEVER.has(fplayer, flocation);
    }

    @Override
    public boolean canUse(Player player, Location loc) {
        return FPerm.CONTAINER.has(FPlayers.i.get(player.getName()),
                new FLocation(loc));
    }

    @Override
    public boolean contains(ShieldRegion region, Location loc) {
        Faction in = Board.getFactionAt(new FLocation(loc));
        if (in == null) {
            return false;
        }
        Faction out = Factions.i.get(region.getName());
        if (out == null) {
            return false;
        }
        return in.getId().equals(out.getId());
    }

    @Override
    public ShieldGroup getMembers(ShieldRegion region) {
        ShieldGroup group = region.getMembers();
        Faction faction = Factions.i.get(region.getName());
        if (faction != null) {
            for (FPlayer member : faction.getFPlayers()) {
                group.addPlayer(member.getName());
            }
        }
        return group;
    }

    @Override
    public ShieldGroup getOwners(ShieldRegion region) {
        ShieldGroup group = region.getOwners();
        Faction faction = Factions.i.get(region.getName());
        if (faction != null) {
            group.addPlayer(faction.getFPlayerLeader().getName());
        }
        return group;
    }

    @Override
    public HashSet<ShieldRegion> getRegions() {
        HashSet<ShieldRegion> regios = new HashSet<ShieldRegion>();
        ArrayList<Faction> factionList = new ArrayList<Faction>(Factions.i
                .get());
        for (Faction faction : factionList) {
            addFactionToRegion(faction, regios);
        }
        return regios;
    }

    @Override
    public HashSet<ShieldRegion> getRegions(Location loc) {
        HashSet<ShieldRegion> regios = new HashSet<ShieldRegion>();
        Faction faction = Board.getFactionAt(new FLocation(loc));
        if (faction != null) {
            addFactionToRegion(faction, regios);
        }
        return regios;
    }

    private void addFactionToRegion(Faction faction,
            HashSet<ShieldRegion> region) {
        region.add(new ShieldRegion(shield, faction.getId(), this, faction
                .getHome().getWorld()));
    }
}