package us.twoguys.shield.regions;

import org.bukkit.World;

/**
 * A ShieldRegion is simply an object which contains the association between the name of a region and the plugin is comes from. The purpose is to avoid conflicts with region names across protection plugins.
 * @author Malikk
 *
 */
public class ShieldRegion {
	
	private String name, protect;
	private World world;
	
	public ShieldRegion (String name, String protect, World world){
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
}
