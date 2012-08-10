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
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.malikk.shield.Shield;
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
 */
public class Flag implements Serializable{

	private static final long serialVersionUID = -8016185180582202784L;
	
	//Static
	public static Shield shield;
	
	//Flag Fields
	private String name;
	private HashSet<String> players;
	private boolean value;

	//Region Fields
	private String regionName, regionPlugin, world;
	
	public Flag(String flag, ShieldRegion region, HashSet<String> players, boolean value){
		this.name = flag;
		this.players = players;
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
	 * Gets all the Players that the flag is assigned to
	 * 
	 * @param players - HashSet<{@linkplain Player}>
	 */
	public HashSet<Player> getPlayers(){
		HashSet<Player> set = new HashSet<Player>();
		
		for (String p: getPlayerNames()){
			set.add(Bukkit.getPlayer(p));
		}
		
		return set;
	}
	
	/**
	 * Gets all the Player's names that the flag is assigned to
	 * 
	 * @param players - HashSet<{@linkplain String}>
	 */
	public HashSet<String> getPlayerNames(){
		return players;
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
	 * Sets the HashSet of player names for this flag to the one passed in. (Will completely overwrite the previous Set)
	 * 
	 * @param players - HashSet<{@linkplain String}>
	 */
	public void setPlayers(HashSet<String> players){
		this.players = players;
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
	 * Adds a player to the flag's current HashSet.
	 * 
	 * @param player - {@link Player}
	 */
	public void addPlayer(Player player){
		this.players.add(player.getName());
	}
	
	/**
	 * Adds players to the flag's current HashSet.
	 * 
	 * @param players - HashSet<{@linkplain String}>
	 */
	public void addPlayers(HashSet<String> players){
		for (String p: players){
			this.players.add(p);
		}
	}
	
	/**
	 * Removes a player from the flag's current Set.
	 * 
	 * @param player - {@link Player}
	 */
	public void removePlayer(Player player){
		this.players.remove(player.getName());
	}
	
	/**
	 * Removes players from the flag's current Set.
	 * 
	 * @param players - HashSet<{@linkplain String}>
	 */
	public void removePlayers(HashSet<String> players){
		for (String p: players){
			this.players.remove(p);
		}
	}
}
