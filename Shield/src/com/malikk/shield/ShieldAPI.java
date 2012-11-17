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

package com.malikk.shield;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.malikk.shield.exceptions.*;
import com.malikk.shield.flags.Flag;
import com.malikk.shield.groups.ShieldGroup;
import com.malikk.shield.regions.ShieldRegion;

/**
 * This is the interface for the main Shield API. It can be used to get all of Shield's Methods, aside from the methods within the {@link Flag} and {@link ShieldRegion} objects.
 * 
 */
public interface ShieldAPI {

	/*
	 * Plugin Methods
	 */

	/**
	 * Gets the ShieldRegion by the passed in name and plugin.
	 * 
	 * @param name the name of the desired region
	 * @param pluginName the name of the plugin the region belongs to
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 * @throws RegionNotFoundException
	 */
	public ShieldRegion getShieldRegion(String name, String pluginName) throws RegionNotFoundException;

	/**
	 * Gets the highest priority ShieldRegion by the passed in name
	 * 
	 * @param name the name of the desired region
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 * @throws RegionNotFoundException
	 */
	public ShieldRegion getPriorityRegion(String name) throws RegionNotFoundException;

	/**
	 * Gets the region with the highest priority, according to the plugin order in the config file.
	 * 
	 * @param regions HashSet of regions
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 */
	public ShieldRegion getPriorityRegion(HashSet<ShieldRegion> regions);

	/**
	 * Gets the highest priority region at the location of the specified entity.
	 * @param entity the entity whose location you want to check
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 * @throws RegionNotFoundException
	 */
	public ShieldRegion getPriorityRegion(Entity entity) throws RegionNotFoundException;

	/**
	 * Gets the highest priority region at the specified location.
	 * @param location  the location you want to check
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 * @throws RegionNotFoundException
	 */
	public ShieldRegion getPriorityRegion(Location location) throws RegionNotFoundException;

	/**
	 * Gets all regions from all plugins
	 * 
	 * @return HashSet<{@link ShieldRegion}>
	 * @throws RegionNotFoundException
	 */
	public HashSet<ShieldRegion> getRegions() throws RegionNotFoundException;

	/**
	 * Gets all the regions by the passed in name. This will only ever return more than one region if there are multiple regions with the same name.
	 * 
	 * @param name the name of the desired region(s)
	 * @return HashSet<{@link ShieldRegion}>
	 * @throws RegionNotFoundException
	 */
	public HashSet<ShieldRegion> getRegions(String name) throws RegionNotFoundException;

	/**
	 * Gets all the regions that the Entity is in
	 * 
	 * @param entity the entity whose location you want to check
	 * @return HashSet<{@link ShieldRegion}>
	 * @throws RegionNotFoundException
	 */
	public HashSet<ShieldRegion> getRegions(Entity entity) throws RegionNotFoundException;

	/**
	 * Gets all the regions that the Location is in
	 * 
	 * @param location the location you want to check
	 * @return HashSet<{@link ShieldRegion}>
	 * @throws RegionNotFoundException
	 */
	public HashSet<ShieldRegion> getRegions(Location location) throws RegionNotFoundException;

	/**
	 * Checks whether or not the entity is in any regions
	 * @param entity Entity to be checked
	 * @return Boolean
	 */
	public boolean isInRegion(Entity entity);

	/**
	 * Checks if the location is in any regions
	 * @param location Location to be checked
	 * @return Boolean
	 */
	public boolean isInRegion(Location location);

	/**
	 * Checks if the player can build where they are standing
	 * @param player Player to be checked
	 * @return Boolean
	 */
	public boolean canBuild(Player player);

	/**
	 * Checks if the player can build at the specified location
	 * @param player Player to be checked
	 * @param location Location to check at
	 * @return Boolean
	 */
	public boolean canBuild(Player player, Location location);

	/**
	 * Checks if the player can use doors, buttons, levers, etc at their current location
	 * @param player Player to be checked
	 * @return Boolean
	 */
	public boolean canUse(Player player);

	/**
	 * Checks if the player can use doors, buttons, levers, etc at the specified location
	 * @param player Player to be checked
	 * @param location Location to check at
	 * @return Boolean
	 */
	public boolean canUse(Player player, Location location);

	/**
	 * Checks if the player can open chests, furnaces, and dispensers at their current location
	 * @param player Player to be checked
	 * @return Boolean
	 */
	public boolean canOpen(Player player);

	/**
	 * Checks if the player can open chests, furnaces, and dispensers at the specified location
	 * @param player Player to be checked
	 * @param location Location to check at
	 * @return Boolean
	 */
	public boolean canOpen(Player player, Location location);

	/*
	 * Custom Flag Methods
	 */

	/**
	 * Adds a flag to the valid flag list
	 * 
	 * @param flag flag name, String
	 */
	public void addValidFlag(String flag);

	/**
	 * Checks if a flag is in the valid flag list
	 * 
	 * @param flag flag name, String
	 * @return Boolean
	 */
	public boolean isValidFlag(String flag);

	/**
	 * Sets a Custom flag for a ShieldGroup
	 * @param flag String flag name
	 * @param region ShieldRegion the flag will be set to
	 * @param group ShieldGroup of players that the value will be applied to
	 * @param value Boolean value that is applied
	 * @throws InvalidFlagException
	 */
	public void setFlag(String flag, ShieldRegion region, ShieldGroup group, boolean value) throws InvalidFlagException;

	/**
	 * Sets a Custom flag for multiple Players
	 * 
	 * @param flag flag name, String
	 * @param region {@link ShieldRegion}
	 * @param players  HashSet of Strings that the value is applied to (can be empty)
	 * @param value Boolean value that is applied
	 * @throws InvalidFlagException
	 */
	public void setFlag(String flag, ShieldRegion region, HashSet<String> players, boolean value) throws InvalidFlagException;

	/**
	 * Sets a Custom flag for a single Player
	 * 
	 * @param flag flag name, String
	 * @param region {@link ShieldRegion}
	 * @param player  String name, that the value is applied to
	 * @param value Boolean value that is applied
	 * @throws InvalidFlagException
	 */
	public void setFlag(String flag, ShieldRegion region, String player, boolean value) throws InvalidFlagException;

	/**
	 * Checks the value of a flag for a specific Player.
	 * <p>
	 * If the flag's Player list contains the player passed in, then this will return the flag's value. If not, it will return the opposite of the flags value.
	 * @param player player
	 * @param flag flag name, String
	 * @param region {@link ShieldRegion}
	 * @return Boolean
	 * @throws FlagNotFoundException
	 * @throws InvalidFlagException
	 */
	public boolean getFlagValue(Player player, String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException;

	/**
	 * Checks the value of a flag for a specific Player.
	 * <p>
	 * If the flag's Player list contains the player passed in, then this will return the flag's value. If not, it will return the opposite of the flags value.
	 * @param player player
	 * @param flag {@link Flag} object
	 * @return Boolean
	 */
	public boolean getFlagValue(Player player, Flag flag);

	/**
	 * Gets the flag object for the name and region
	 * @param flag flag name, String
	 * @param region {@link ShieldRegion}
	 * @return {@link Flag}
	 * @throws FlagNotFoundException
	 * @throws InvalidFlagException
	 */
	public Flag getFlag(String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException;

	/**
	 * Deletes the flag object for the name and region entirely
	 * @param flag String name of the flag
	 * @param region ShieldRegion object
	 * @throws FlagNotFoundException
	 * @throws InvalidFlagException
	 */
	public void removeFlag(String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException;
}
