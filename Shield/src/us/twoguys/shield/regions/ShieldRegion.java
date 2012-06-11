package us.twoguys.shield.regions;

import org.bukkit.World;

public class ShieldRegion {
	
	private String name, protect;
	private World world;
	
	public ShieldRegion (String name, String protect, World world){
		this.name = name;
		this.protect = protect;
		this.world = world;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPluginName(){
		return protect;
	}
	
	public World getWorld(){
		return world;
	}
}
