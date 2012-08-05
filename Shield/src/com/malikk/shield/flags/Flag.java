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
import org.bukkit.entity.Player;

import com.malikk.shield.regions.ShieldRegion;

/**
 * A flag object allows for lists of players to be assigned boolean values in certain regions.
 * @author Malikk
 * @see {@link #getName()}
 * @see {@link #getRegion()}
 * @see {@link #getPlayers()}
 * @see {@link #getValue()}
 * @see {@link #setPlayers(HashSet)}
 * @see {@link #setValue(boolean)}
 * @see {@link #addPlayer(Player)}
 * @see {@link #addPlayers(HashSet)}
 * @see {@link #removePlayer(Player)}
 * @see {@link #removePlayers(Player)}
 */
public class Flag implements Serializable{

	private static final long serialVersionUID = -2914461212107421563L;
	
	private String name;
	private ShieldRegion region;
	private HashSet<String> players;
	private boolean value;

	public Flag(String flag, ShieldRegion region, HashSet<String> players, boolean value){
		this.name = flag;
		this.region = region;
		this.players = players;
		this.value = value;
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
	 * @return {@link ShieldRegion} - region that the flag is set on
	 */
	public ShieldRegion getRegion(){
		return region;
	}
	
	/**
	 * Gets all the Players that the flag is assigned to
	 * 
	 * @param players - an HashSet<{@linkplain Player}>
	 */
	public HashSet<Player> getPlayers(){
		HashSet<Player> set = new HashSet<Player>();
		
		for (String p: getPlayerNames()){
			set.add(Bukkit.getPlayer(p));
		}
		
		return set;
	}
	
	/**
	 * Gets all the Players that the flag is assigned to, as Strings
	 * 
	 * @param players - an HashSet<{@linkplain Player}>
	 */
	public HashSet<String> getPlayerNames(){
		return players;
	}
	
	/**
	 * Gets the value that the flag will return for players in its ArrayList. For players not in the ArrayList, the opposite of this value will be returned.
	 * 
	 * @return Boolean value assigned to the flag
	 */
	public boolean getValue(){
		return value;
	}
	
	/**
	 * Sets the ArrayList<Player> for this flag to the ArrayList passed in. (Will completely overwrite the previous list)
	 * 
	 * @param players - an ArrayList<{@linkplain Player}>
	 */
	public void setPlayers(HashSet<String> players){
		this.players = players;
	}
	
	/**
	 * Sets the value of the flag.
	 * 
	 * @param value - Boolean value
	 */
	public void setValue(boolean value){
		this.value = value;
	}
	
	/**
	 * Adds a player to the flag's current ArrayList.
	 * 
	 * @param player
	 */
	public void addPlayer(Player player){
		this.players.add(player.getName());
	}
	
	/**
	 * Adds players to the flag's current ArrayList.
	 * 
	 * @param players - ArrayList
	 */
	public void addPlayers(HashSet<String> players){
		for (String p: players){
			this.players.add(p);
		}
	}
	
	/**
	 * Removes a player from the flag's current Set.
	 * 
	 * @param player
	 */
	public void removePlayer(Player player){
		this.players.remove(player.getName());
	}
	
	/**
	 * Removes players from the flag's current List.
	 * 
	 * @param players - ArrayList
	 */
	public void removePlayers(HashSet<String> players){
		for (String p: players){
			this.players.remove(p);
		}
	}
}
