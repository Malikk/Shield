package us.twoguys.shield.plugins;

import us.twoguys.shield.ShieldAPI;

public interface Protect extends ShieldAPI{
	
	//Add methods that we need to make sure every Protect class has, but don't want in the actual API, here.
	
	public boolean isEnabled();
	
	public String getPluginName();
}
