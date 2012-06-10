package us.twoguys.shield.regions;

public class ShieldRegion {
	
	protected String name, protect;
	
	public ShieldRegion (String name, String protect){
		this.name = name;
		this.protect = protect;
	}
	
	public String getName(){
		return name;
	}
	
	public String getProtectionPluginName(){
		return protect;
	}
}
