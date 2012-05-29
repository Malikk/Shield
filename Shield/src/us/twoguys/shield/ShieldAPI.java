package us.twoguys.shield;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import us.twoguys.shield.flags.Flag;

/**
 * The main API
 * 
 */
public interface ShieldAPI {

	/*
	 * Plugin Methods
	 */
	
	/**
	 * @return a list of all found regions
	 */
	public ArrayList<String> getRegions();
	
	/**
	 * Gets the regions that the Entity is in
	 * 
	 * @param entity
	 * @return region name, or null
	 */
	public ArrayList<String> getRegions(Entity entity);
	
	/**
	 * Gets the regions that the Location is in
	 * 
	 * @param entity
	 * @return region name, or null
	 */
	public ArrayList<String> getRegions(Location loc);
	
	/**
	 * Checks whether or not the player is in any regions
	 * @param player
	 * @return true or false
	 */
	public boolean isInRegion(Entity entity);
	
	/**
	 * Checks if the location is in any region
	 * @param loc
	 * @return true or false
	 */
	public boolean isInRegion(Location loc);
	
	/**
	 * Checks whether or not the player owns the region they are in
	 * @param player
	 * @return true or false
	 */
	//public boolean isOwner(Player player);
	
	/**
	 * Checks if the player can build where they are standing
	 * @param player
	 * @return true or false
	 */
	public boolean canBuild(Player player);
	
	/**
	 * Checks if the player can build at the specified location
	 * @param player
	 * @param loc
	 * @return true or false
	 */
	public boolean canBuild(Player player, Location loc);
	
	/**
	 * Checks if the player can use doors, buttons, levers, etc at their current location
	 * @param player
	 * @return true or false
	 */
	public boolean canUse(Player player);
	
	/**
	 * Checks if the player can use doors, buttons, levers, etc at the specified location
	 * @param player
	 * @param loc
	 * @return true or false
	 */
	public boolean canUse(Player player, Location loc);
	
	/**
	 * Checks if the player can open chests, furnaces, and dispensers at their current location
	 * @param player
	 * @return true or false
	 */
	public boolean canOpen(Player player);
	
	/**
	 * Checks if the player can open chests, furnaces, and dispensers at the specified location
	 * @param player
	 * @param loc
	 * @return true or false
	 */
	public boolean canOpen(Player player, Location loc);
	
	/*
	 * Custom Flag Methods
	 */
	
	/**
	 * Adds a flag to the valid flag list
	 * 
	 * @param flag name
	 */
	public void addValidFlag(String flag);
	
	/**
	 * Checks if a flag is in the valid flag list
	 * 
	 * @param flag name
	 * @return true or false
	 */
	public boolean isValidFlag(String flag);
	
	/**
	 * Creates a Custom flag
	 * 
	 * @param flag name
	 * @param region name
	 * @param players that the value is applied to (can be null)
	 * @param value that is applied
	 */
	public void createFlag(String flag, String region, ArrayList<Player> players, boolean value);
	
	/**
	 * Checks the value of a flag for a specific Player
	 * @param player
	 * @param flag name
	 * @param region name
	 * @return true or false
	 */
	public boolean hasFlag(Player player, String flag, String region);
	
	/**
	 * Gets the flag object for the name and region
	 * @param flag name
	 * @param region name
	 * @return Flag
	 */
	public Flag getFlag(String flag, String region);
}
