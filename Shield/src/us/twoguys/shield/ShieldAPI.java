package us.twoguys.shield;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import us.twoguys.shield.exceptions.FlagNotFoundException;
import us.twoguys.shield.exceptions.InvalidFlagException;
import us.twoguys.shield.exceptions.InvalidRegionException;
import us.twoguys.shield.flags.Flag;
import us.twoguys.shield.regions.ShieldRegion;

/**
 * This is the interface for the main Shield API. It can be used to get all of Shield's Methods, aside from the methods within the {@link Flag} and {@link ShieldRegion} objects.
 * 
 */
public interface ShieldAPI {

	/*
	 * Plugin Methods
	 */
	
	/**
	 * Gets the region with the highest priority, according to the plugin order in the config file.
	 * 
	 * @param regions - ArrayList of regions
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 */
	public ShieldRegion getPriorityRegion(ArrayList<ShieldRegion> regions);
	
	/**
	 * Gets the highest priority region at the location of the specific entity. 
	 * @param entity 
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 */
	public ShieldRegion getPriorityRegion(Entity entity);
	
	/**
	 * Gets the highest priority region at the specific location.
	 * @param location
	 * @return {@link ShieldRegion} - The highest priority ShieldRegion
	 */
	public ShieldRegion getPriorityRegion(Location location);
	
	/**
	 * Gets all regions from all plugins
	 * 
	 * @return ArrayList<{@link ShieldRegion}>
	 */
	public ArrayList<ShieldRegion> getRegions();
	
	/**
	 * Gets the regions that the Entity is in
	 * 
	 * @param entity
	 * @return {@link ShieldRegion}
	 */
	public ArrayList<ShieldRegion> getRegions(Entity entity);
	
	/**
	 * Gets the regions that the Location is in
	 * 
	 * @param location
	 * @return {@link ShieldRegion}
	 */
	public ArrayList<ShieldRegion> getRegions(Location location);
	
	/**
	 * Checks whether or not the entity is in any regions
	 * @param entity
	 * @return Boolean
	 */
	public boolean isInRegion(Entity entity);
	
	/**
	 * Checks if the location is in any region
	 * @param location
	 * @return Boolean
	 */
	public boolean isInRegion(Location location);
	
	/**
	 * Checks whether or not the player owns the region they are in
	 * @param player
	 * @return true or false
	 */
	//public boolean isOwner(Player player);
	
	/**
	 * Checks if the player can build where they are standing
	 * @param player
	 * @return Boolean
	 */
	public boolean canBuild(Player player);
	
	/**
	 * Checks if the player can build at the specified location
	 * @param player
	 * @param location
	 * @return Boolean
	 */
	public boolean canBuild(Player player, Location location);
	
	/**
	 * Checks if the player can use doors, buttons, levers, etc at their current location
	 * @param player
	 * @return Boolean
	 */
	public boolean canUse(Player player);
	
	/**
	 * Checks if the player can use doors, buttons, levers, etc at the specified location
	 * @param player
	 * @param location
	 * @return Boolean
	 */
	public boolean canUse(Player player, Location location);
	
	/**
	 * Checks if the player can open chests, furnaces, and dispensers at their current location
	 * @param player
	 * @return Boolean
	 */
	public boolean canOpen(Player player);
	
	/**
	 * Checks if the player can open chests, furnaces, and dispensers at the specified location
	 * @param player
	 * @param location
	 * @return Boolean
	 */
	public boolean canOpen(Player player, Location location);
	
	/*
	 * Custom Flag Methods
	 */
	
	/**
	 * Adds a flag to the valid flag list
	 * 
	 * @param flag - flag name, String
	 */
	public void addValidFlag(String flag);
	
	/**
	 * Checks if a flag is in the valid flag list
	 * 
	 * @param flag - flag name, String
	 * @return Boolean
	 */
	public boolean isValidFlag(String flag);
	
	/**
	 * Sets a Custom flag
	 * 
	 * @param flag - flag name, String
	 * @param region - {@link ShieldRegion}
	 * @param players  - ArrayList<{@linkplain Player}> that the value is applied to (can be null)
	 * @param value - Boolean value that is applied
	 */
	public void setFlag(String flag, ShieldRegion region, ArrayList<Player> players, boolean value);
	
	/**
	 * Checks the value of a flag for a specific Player.
	 * <p>
	 * If the flag's Player list contains the player passed in, then this will return the flag's value. If not, it will return the opposite of the flags value.
	 * @param player - player
	 * @param flag - flag name, String
	 * @param region - {@link ShieldRegion}
	 * @return Boolean
	 * @throws FlagNotFoundException 
	 * @throws InvalidRegionException 
	 * @throws InvalidFlagException 
	 */
	public boolean getFlagValue(Player player, String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException, InvalidRegionException;
	
	/**
	 * Gets the flag object for the name and region
	 * @param flag - flag name, String
	 * @param region - {@link ShieldRegion}
	 * @return Flag
	 * @throws FlagNotFoundException 
	 * @throws InvalidRegionException 
	 * @throws InvalidFlagException 
	 */
	public Flag getFlag(String flag, ShieldRegion region) throws FlagNotFoundException, InvalidFlagException, InvalidRegionException;
}
