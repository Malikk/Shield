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

package com.malikk.shield.flags;

import java.io.Serializable;
import org.bukkit.Bukkit;
import com.malikk.shield.Shield;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;

/**
 * A flag object allows for sets of players to be assigned boolean values in certain regions.
 * @author Malikk
 * @see {@link #getName()}
 * @see {@link #getRegion()}
 * @see {@link #getPlayers()}
 * @see {@link #getValue()}
 * @see {@link #setPlayers(players)}
 * @see {@link #setValue(boolean)}
 * @see {@link #addPlayer(player)}
 * @see {@link #addPlayers(players)}
 * @see {@link #removePlayer(player)}
 * @see {@link #removePlayers(players)}
 * @see {@link #remove()}
 */
public class Flag implements Serializable{

	private static final long serialVersionUID = -8016185180582202784L;

	//Static
	public static Shield shield;

	//Flag Fields
	private String name;
	private ShieldGroup group;
	private boolean value;

	//Region Fields
	private String regionName, regionPlugin, world;

	public Flag(String flag, ShieldRegion region, ShieldGroup group, boolean value){
		this.name = flag;
		this.group = group;
		this.value = value;

		this.regionName = region.getName();
		this.regionPlugin = region.getPluginName();
		this.world = region.getWorld().getName();
	}

	/**
	 * Gets the name of the flag.
	 * 
	 * @return String - name
	 */
	public String getName(){
		return name;
	}

	/**
	 * Gets the ShieldRegion the flag is set at.
	 * 
	 * @return {@link ShieldRegion} that the flag is set on
	 */
	public ShieldRegion getRegion(){
		return shield.rm.createShieldRegion(regionName, shield.pm.getProtectObjectFromName(regionPlugin), Bukkit.getWorld(world));
	}

	/**
	 * Gets the value that the flag will return for players in its HashSet. For players not in the HashSet, the opposite of this value will be returned.
	 * 
	 * @return value - {@link Boolean} assigned to the flag
	 */
	public boolean getValue(){
		return value;
	}

	/**
	 * Sets the value of the flag.
	 * 
	 * @param value - {@link Boolean}
	 */
	public void setValue(boolean value){
		this.value = value;
	}

	/**
	 * Gets the ShieldGroup object containing the set of players assigned to this flag.
	 * @return {@link ShieldGroup}
	 */
	public ShieldGroup getShieldGroup(){
		return group;
	}

	/**
	 * Sets the ShieldGroup for this flag. Setting this will completely override the old object.
	 * @param group - new {@link ShieldGroup} for this flag
	 */
	public void setShieldGroup(ShieldGroup group){
		this.group = group;
	}

	/**
	 * Removes this flag completely
	 */
	public void remove(){
		shield.fm.flags.remove(this);
	}
}
