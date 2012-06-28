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
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.malikk.shield.flags;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.malikk.shield.regions.ShieldRegion;


/**
 * A flag object allows for lists of players to be assigned boolean values in certain regions.
 * @author Malikk
 * @see {@link #getName()}
 * @see {@link #getRegion()}
 * @see {@link #getPlayers()}
 * @see {@link #getValue()}
 * @see {@link #setPlayers(ArrayList)}
 * @see {@link #setValue(boolean)}
 * @see {@link #addPlayer(Player)}
 * @see {@link #removePlayer(Player)}
 */
public class Flag implements Serializable{

	private static final long serialVersionUID = -2914461212107421563L;
	
	private String name;
	private ShieldRegion region;
	private ArrayList<Player> players;
	private boolean value;

	public Flag(String flag, ShieldRegion region, ArrayList<Player> players, boolean value){
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
	 * @param players - an ArrayList<{@linkplain Player}>
	 */
	public ArrayList<Player> getPlayers(){
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
	public void setPlayers(ArrayList<Player> players){
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
		this.players.add(player);
	}
	
	/**
	 * Removes a player from the flag's current ArrayList.
	 * 
	 * @param player
	 */
	public void removePlayer(Player player){
		this.players.remove(player);
	}
}
