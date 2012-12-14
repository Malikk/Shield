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

package com.malikk.shield.regions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.plugins.Protect;

/**
 * A ShieldRegion is an object which represents a region in any given plugin. It stores the regions name, the plugin it belongs to, the world, and the Protect object.
 * @author Malikk
 *
 * @see {@link #getName()}
 * @see {@link #getPluginName()}
 * @see {@link #getWorld()}
 */
public class ShieldRegion {

	private Shield plugin;
	private String name;
	private World world;
	private Protect protect;

	public ShieldRegion (Shield instance, String name, Protect protect, World world){
		plugin = instance;

		plugin.log("Creating ShieldRegion");
		plugin.log(name);
		plugin.log(protect.getPluginName());
		plugin.log(world.getName());

		this.name = name;
		this.protect = protect;
		this.world = world;
	}

	/**
	 * Gets the name of the region.
	 * 
	 * @return String - name
	 */
	public String getName(){
		return name;
	}

	/**
	 * Gets the name of the plugin the region belongs to.
	 * 
	 * @return String - plugin name
	 */
	public String getPluginName(){
		return protect.getPluginName();
	}

	protected Protect getProtectObject(){
		return protect;
	}

	/**
	 * Gets the World the region is in.
	 * 
	 * @return World - world
	 */
	public World getWorld(){
		return world;
	}

	/**
	 * Checks if the ShieldRegion contains the Location
	 * @param loc Location to check
	 * @return true if the region contained the Location
	 */
	public boolean contains(Location loc){
		return plugin.rm.containsLoc(this, loc);
	}

	/**
	 * Checks if the Player is the Owner of the region.
	 * @param player Player to check
	 * @return true if the Player is the owner
	 */
	public boolean isOwner(Player player){
		ShieldGroup owners = protect.getOwners(this);
		return (owners.contains(player) ? true : false);
	}

	/**
	 * Checks if the Player is a member of the region.
	 * @param player Player to check
	 * @return true if the Player is a member
	 */
	public boolean isMember(Player player){
		ShieldGroup members = protect.getMembers(this);
		return (members.contains(player) ? true : false);
	}

	/**
	 * Gets the Owners of the region.
	 * @return ShieldGroup containing all of the owners
	 */
	public ShieldGroup getOwners(){
		return protect.getOwners(this);
	}

	/**
	 * Gets the Members of the region.
	 * @return ShieldGroup containing all of the members
	 */
	public ShieldGroup getMembers(){
		return protect.getMembers(this);
	}
}
