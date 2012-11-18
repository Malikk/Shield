package com.malikk.shield.groups;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * A ShieldGroup is an object that holds a HashSet of player names. It is used anytime Shield needs to return a group of players. You can also create a new ShieldGroup and pass it in to Shield.
 * @author Malikk
 *
 */
public class ShieldGroup implements Serializable{

	private static final long serialVersionUID = -8079598050769223607L;

	private HashSet<String> names = new HashSet<String>();

	/**
	 * Creates a new ShieldGroup with no players
	 */
	public ShieldGroup(){}

	/**
	 * Creates a new ShieldGroup containing the Players in the Set
	 * @param players Set of Player objects
	 */
	public ShieldGroup(Set<Player> players){
		addPlayers(players);
	}

	/**
	 * Creates a new ShieldGroup containing the Players in the List
	 * @param players List of Player objects
	 */
	public ShieldGroup(List<Player> players){
		addPlayers(players);
	}

	/**
	 * Creates a new ShieldGroup containg the Players in the Array
	 * @param names Array of Player objects
	 */
	public ShieldGroup(Player[] players) {
		addPlayers(players);
	}

	/**
	 * Creates a new ShieldGroup containing the player
	 * @param player Player object
	 */
	public ShieldGroup(Player player){
		addPlayer(player);
	}

	/**
	 * Creates a new ShieldGroup containing the player
	 * @param name name of a Player
	 */
	public ShieldGroup(String name){
		addPlayer(name);
	}

	/**
	 * Adds a Player to the group
	 * @param player Player object to be added
	 * @return true if the group did not already contain this player
	 */
	public boolean addPlayer(Player player){
		return names.add(player.getName());
	}

	/**
	 * Adds a Player to the group
	 * @param name the name of the player to be added
	 * @return true if the group did not already contain this player
	 */
	public boolean addPlayer(String name){
		return names.add(name);
	}

	/**
	 * Removes a Player from the group
	 * @param player Player object to be removed
	 * @return true if the group contained this player
	 */
	public boolean removePlayer(Player player){
		return names.remove(player.getName());
	}

	/**
	 * Removes a Player from the group
	 * @param name the name of the player to be removed
	 * @return true if the group contained this player
	 */
	public boolean removePlayer(String name){
		return names.remove(name);
	}

	/**
	 * Adds the Set of Players to the group
	 * @param players Set of Player objects
	 */
	public void addPlayers(Set<Player> players){
		for (Player p: players){
			names.add(p.getName());
		}
	}

	/**
	 * Adds the List of Players to the group
	 * @param players List of Player objects
	 */
	public void addPlayers(List<Player> players){
		Iterator<Player> it = players.iterator();

		while (it.hasNext()){
			names.add(it.next().getName());
		}
	}

	/**
	 * Adds the Players in the Array to the group
	 * @param players
	 */
	public void addPlayers(Player[] players){
		for (Player p: players){
			names.add(p.getName());
		}
	}

	/**
	 * Adds the Set of String names to the group.
	 * This method edits the same list as if you were to use Player objects.
	 * @param names Set of String names
	 */
	public void addPlayerNames(Set<String> names){
		for (String name: names){
			this.names.add(name);
		}
	}

	/**
	 * Adds the List of String names to the group.
	 * This method edits the same list as if you were to use Player objects.
	 * @param names List of names
	 */
	public void addPlayerNames(List<String> names){
		Iterator<String> it = names.iterator();

		while (it.hasNext()){
			this.names.add(it.next());
		}
	}

	/**
	 * Adds the Array of String names to the group.
	 * This method edits the same list as if you were to use Player objects.
	 * @param names Array of Player names
	 */
	public void addPlayerNames(String[] names){
		for (String name: names){
			this.names.add(name);
		}
	}

	/**
	 * Removes the Set of Players from the group
	 * @param players Set of Player objects
	 */
	public void removePlayers(Set<Player> players){
		for (Player p: players){
			this.names.remove(p.getName());
		}
	}

	/**
	 * Removes the List of Players from the group
	 * @param players List of Player objects
	 */
	public void removePlayers(List<Player> players){
		Iterator<Player> it = players.iterator();

		while (it.hasNext()){
			this.names.remove(it.next().getName());
		}
	}

	/**
	 * Removes the Set of String names from the group.
	 * This method edits the same list as if you were to use Player objects.
	 * @param names Set of String names
	 */
	public void removePlayerNames(Set<String> names){
		for (String name: names){
			this.names.remove(name);
		}
	}

	/**
	 * Removes the List of String names from the group.
	 * This method edits the same list as if you were to use Player objects.
	 * @param names List of String names
	 */
	public void removePlayerNames(List<String> names){
		Iterator<String> it = names.iterator();

		while (it.hasNext()){
			this.names.remove(it.next());
		}
	}

	/**
	 * Gets all the Players in the group, as Strings
	 * @return HashSet of String names
	 */
	public HashSet<String> getPlayerNames(){
		return names;
	}

	/**
	 * Gets all Players in the group who are online
	 * @return HashSet of Player objects
	 */
	public HashSet<Player> getOnlinePlayers(){
		HashSet<Player> players = new HashSet<Player>();

		for (String name: names){
			Player player = Bukkit.getPlayerExact(name);

			if (player != null){
				players.add(player);
			}
		}

		return players;
	}

	/**
	 * Checks whether the group contains a Player
	 * @param player Player object
	 * @return true if the group contained the Player
	 */
	public boolean contains(Player player){
		return (this.names.contains(player.getName()) ? true : false);
	}

	/**
	 * Checks whether the group contains a Player
	 * @param name Player's name
	 * @return true if the group contained the Player
	 */
	public boolean contains(String name){
		return (this.names.contains(name) ? true : false);
	}

}
