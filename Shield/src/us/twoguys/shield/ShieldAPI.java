package us.twoguys.shield;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
//poop
/**
 * The main API
 * 
 */
public interface Protection {

	/**
	 * Checks if the protection plugin is enabled
	 * @return true or false
	 */
	public boolean isEnabled();

	/**
	 * Gets the name of the protection plugin loaded
	 * @return name as a string
	 */
	public String getPluginName();
	
	/**
	 * Checks whether or not the player is in any regions
	 * @param player
	 * @return true or false
	 */
	public boolean isInRegion(Entity entity);
	
	/**
	 * Returns the highest priority region that the entity is inside of
	 * @param entity
	 * @return region name as string, or null
	 */
	public String getRegionOccupiedBy(Entity entity);
		
	/**
	 * Checks whether or not the player owns the region they are in
	 * @param player
	 * @return true or false
	 */
	//public boolean isOwner(Player player);
	
	/**
	 * Gets all regions owned by the player
	 * @param player
	 * @return regions as String[]
	 */
	//public String[] getOwnedRegions(Player player);
	
	/**
	 * Checks if the location is in any region
	 * @param loc
	 * @return true or false
	 */
	public boolean isInRegion(Location loc);
	
	/**
	 * Checks if the location is in the region specified
	 * @param loc
	 * @param regionName
	 * @return true or false
	 */
	public boolean isInRegion(Location loc, String regionName);
	
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
	
}
